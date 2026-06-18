package com.hamburgueria.operacoes_balcao;

import com.hamburgueria.fila_cozinha.*;
import com.hamburgueria.configuracoes.Configuracoes;
import com.hamburgueria.calculo_preco.*;

public class PedidoFacade {

    private final SetorEstoque   estoque   = new SetorEstoque();
    private final SetorCozinha   cozinha   = new SetorCozinha();
    private final SetorPagamento pagamento = new SetorPagamento();
    private final SetorEntrega   entrega   = new SetorEntrega();

    private final FilaCozinha    filaCozinha = new FilaCozinha();
    private final Calculadora    calculadora = new Calculadora(new OperacaoSemDesconto());

    public void realizarPedidoBalcao(String cliente, String lanche,
                                     double valor, String formaPagamento) {
        int tempoPreparo = Configuracoes.getInstance().getTempoMedioPreparo();

        estoque.verificarDisponibilidade("Blend 180g");
        estoque.verificarDisponibilidade("Pão Brioche");
        estoque.reservarIngrediente("Blend 180g", 1);
        estoque.reservarIngrediente("Pão Brioche", 1);

        PedidoCozinha pedido = new PedidoCozinha("BAL-" + System.currentTimeMillis(), cliente);
        filaCozinha.executar(new AbrirPedidoComando(pedido));
        filaCozinha.executar(new AdicionarItemComando(pedido, lanche));
        filaCozinha.executar(new IniciarPreparoComando(pedido));

        boolean pago = pagamento.processarPagamento(cliente, valor, formaPagamento);
        if (pago) {
            pagamento.emitirComprovante(cliente, valor);
            cozinha.registrarTempoMedio(tempoPreparo);
            filaCozinha.executar(new FinalizarPreparoComando(pedido));
            filaCozinha.executar(new FecharPedidoComando(pedido));
            cozinha.notificarPronto(lanche);
        }
    }

    public void realizarPedidoDelivery(String cliente, String lanche, double valor,
                                       String formaPagamento, String endereco, String bairro) {
        double taxaBase = Configuracoes.getInstance().getTaxaEntrega();

        estoque.verificarDisponibilidade("Blend 180g");
        estoque.reservarIngrediente("Blend 180g", 1);

        calculadora.setOperacao(new OperacaoTaxaEntrega(taxaBase));
        double totalComTaxa = calculadora.calcular(valor);

        PedidoCozinha pedido = new PedidoCozinha("DEL-" + System.currentTimeMillis(), cliente);
        filaCozinha.executar(new AbrirPedidoComando(pedido));
        filaCozinha.executar(new AdicionarItemComando(pedido, lanche));

        boolean pago = pagamento.processarPagamento(cliente, totalComTaxa, formaPagamento);
        if (pago) {
            pagamento.emitirComprovante(cliente, totalComTaxa);
            filaCozinha.executar(new IniciarPreparoComando(pedido));
            filaCozinha.executar(new FinalizarPreparoComando(pedido));
            entrega.registrarEndereço(cliente, endereco);
            entrega.despacharEntregador(cliente);
            filaCozinha.executar(new FecharPedidoComando(pedido));
        }
    }

    public void cancelarPedido(String cliente, double valorReembolso) {
        calculadora.setOperacao(new OperacaoDesconto(100));
        calculadora.calcular(valorReembolso);
        pagamento.emitirComprovante(cliente + " [REEMBOLSO]", valorReembolso);
        estoque.emitirAlertaEstoqueBaixo();
    }

    public void fecharCaixaDoDia() {
        estoque.emitirAlertaEstoqueBaixo();
    }

    public FilaCozinha getFilaCozinha() { return filaCozinha; }
}
