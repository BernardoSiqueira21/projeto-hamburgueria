package com.hamburgueria.operacoes_balcao;

import com.hamburgueria.fila_cozinha.*;
import com.hamburgueria.configuracoes.Configuracoes;
import com.hamburgueria.calculo_preco.*;
import com.hamburgueria.pagamento_entrega.*;

public class PedidoFacade {

    private final SetorEstoque   estoque     = new SetorEstoque();
    private final SetorCozinha   cozinha     = new SetorCozinha();
    private final SetorPagamento pagamento   = new SetorPagamento();
    private final SetorEntrega   entrega     = new SetorEntrega();
    private final FilaCozinha    filaCozinha = new FilaCozinha();
    private final Calculadora    calculadora = new Calculadora(new OperacaoSemDesconto());

    public void realizarPedidoBalcao(String cliente, String lanche,
                                     double valor, String formaPagamento) {
        int tempoPreparo = Configuracoes.getInstance().getTempoMedioPreparo();
        estoque.verificarDisponibilidade("Blend 180g");
        estoque.reservarIngrediente("Blend 180g", 1);

        PedidoCozinha pedido = new PedidoCozinha("BAL-" + System.currentTimeMillis(), cliente);
        filaCozinha.executar(new AbrirPedidoComando(pedido));
        filaCozinha.executar(new AdicionarItemComando(pedido, lanche));
        filaCozinha.executar(new IniciarPreparoComando(pedido));

        // Adapter: processa pagamento via interface iPagamento
        iPagamento formaPag = resolverFormaPagamento(formaPagamento);
        boolean pago = formaPag.processar(cliente, valor);
        if (pago) {
            cozinha.registrarTempoMedio(tempoPreparo);
            filaCozinha.executar(new FinalizarPreparoComando(pedido));
            filaCozinha.executar(new FecharPedidoComando(pedido));
        }
    }

    public void realizarPedidoDelivery(String cliente, String lanche, double valor,
                                       String formaPagamento, String endereco, String cep) {
        double taxaBase = Configuracoes.getInstance().getTaxaEntrega();
        estoque.reservarIngrediente("Blend 180g", 1);

        calculadora.setOperacao(new OperacaoTaxaEntrega(taxaBase));
        double totalComTaxa = calculadora.calcular(valor);

        PedidoCozinha pedido = new PedidoCozinha("DEL-" + System.currentTimeMillis(), cliente);
        filaCozinha.executar(new AbrirPedidoComando(pedido));
        filaCozinha.executar(new AdicionarItemComando(pedido, lanche));

        iPagamento formaPag = resolverFormaPagamento(formaPagamento);
        boolean pago = formaPag.processar(cliente, totalComTaxa);
        if (pago) {
            filaCozinha.executar(new IniciarPreparoComando(pedido));
            filaCozinha.executar(new FinalizarPreparoComando(pedido));

            iEntrega entregaAdapter = new EntregaExternaAdapter(new SistemaEntregaExterna());
            entregaAdapter.despachar(endereco, cep);
            filaCozinha.executar(new FecharPedidoComando(pedido));
        }
    }

    public void cancelarPedido(String cliente, double valorReembolso) {
        calculadora.setOperacao(new OperacaoDesconto(100));
        calculadora.calcular(valorReembolso);
        estoque.emitirAlertaEstoqueBaixo();
    }

    public void fecharCaixaDoDia() { estoque.emitirAlertaEstoqueBaixo(); }

    public FilaCozinha getFilaCozinha() { return filaCozinha; }

    private iPagamento resolverFormaPagamento(String forma) {
        return switch (forma.toLowerCase()) {
            case "pix"     -> new PagamentoPix();
            case "legado"  -> new PagamentoLegadoAdapter(new SistemaPagamentoLegado(), "000.000.000-00");
            default        -> new PagamentoCartao(forma);
        };
    }
}