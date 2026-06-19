package com.hamburgueria;

import com.hamburgueria.fila_cozinha.*;
import com.hamburgueria.pagamento_entrega.*;
import com.hamburgueria.acesso_cardapio.*;
import com.hamburgueria.calculadora_precos.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Padrão: Command
class FilaDeComandasDaCozinhaTest {

    @Test
    void abrirPedidoMudaStatusParaAberto() {
        FilaCozinha fila = new FilaCozinha();
        PedidoCozinha pedido = new PedidoCozinha("001", "João");
        fila.executar(new AbrirPedidoComando(pedido));
        assertEquals("ABERTO", pedido.getStatus());
    }

    @Test
    void adicionarItemIncluiNaLista() {
        FilaCozinha fila = new FilaCozinha();
        PedidoCozinha pedido = new PedidoCozinha("001", "João");
        fila.executar(new AbrirPedidoComando(pedido));
        fila.executar(new AdicionarItemComando(pedido, "Smash Burguer"));
        assertTrue(pedido.getItens().contains("Smash Burguer"));
    }

    @Test
    void iniciarPreparoMudaStatus() {
        FilaCozinha fila = new FilaCozinha();
        PedidoCozinha pedido = new PedidoCozinha("001", "João");
        fila.executar(new AbrirPedidoComando(pedido));
        fila.executar(new IniciarPreparoComando(pedido));
        assertEquals("EM_PREPARO", pedido.getStatus());
    }

    @Test
    void finalizarPreparoMudaStatusParaPronto() {
        FilaCozinha fila = new FilaCozinha();
        PedidoCozinha pedido = new PedidoCozinha("001", "João");
        fila.executar(new AbrirPedidoComando(pedido));
        fila.executar(new IniciarPreparoComando(pedido));
        fila.executar(new FinalizarPreparoComando(pedido));
        assertEquals("PRONTO", pedido.getStatus());
    }

    @Test
    void undoDesfazUltimoComando() {
        FilaCozinha fila = new FilaCozinha();
        PedidoCozinha pedido = new PedidoCozinha("001", "João");
        fila.executar(new AbrirPedidoComando(pedido));
        fila.executar(new IniciarPreparoComando(pedido));
        fila.executar(new FinalizarPreparoComando(pedido));
        fila.desfazerUltimo();
        assertEquals("EM_PREPARO", pedido.getStatus());
    }

    @Test
    void filaBatchExecutaOrdem() {
        PedidoCozinha p2 = new PedidoCozinha("002", "Maria");
        FilaCozinha fb = new FilaCozinha();
        fb.enfileirar(new AbrirPedidoComando(p2));
        fb.enfileirar(new AdicionarItemComando(p2, "X-Burguer"));
        fb.enfileirar(new FecharPedidoComando(p2));
        assertEquals(3, fb.totalNaFila());
        fb.executarFila();
        assertEquals("FECHADO", p2.getStatus());
    }

    @Test
    void filaEsvaziaAposExecucao() {
        PedidoCozinha p = new PedidoCozinha("003", "Carlos");
        FilaCozinha fb = new FilaCozinha();
        fb.enfileirar(new AbrirPedidoComando(p));
        fb.executarFila();
        assertEquals(0, fb.totalNaFila());
    }

    @Test
    void undoCascataReverteTudo() {
        PedidoCozinha p2 = new PedidoCozinha("002", "Maria");
        FilaCozinha fb = new FilaCozinha();
        fb.enfileirar(new AbrirPedidoComando(p2));
        fb.enfileirar(new AdicionarItemComando(p2, "X-Burguer"));
        fb.enfileirar(new FecharPedidoComando(p2));
        fb.executarFila();
        fb.desfazerTodos();
        assertEquals("CANCELADO", p2.getStatus());
    }

    @Test
    void cancelarPedidoEDesfazerVoltaParaAberto() {
        PedidoCozinha p3 = new PedidoCozinha("003", "Carlos");
        FilaCozinha fc = new FilaCozinha();
        fc.executar(new AbrirPedidoComando(p3));
        fc.executar(new CancelarPedidoComando(p3));
        assertEquals("CANCELADO", p3.getStatus());
        fc.desfazerUltimo();
        assertEquals("ABERTO", p3.getStatus());
    }

    @Test
    void undoSemHistoricoNaoLancaExcecao() {
        assertDoesNotThrow(() -> new FilaCozinha().desfazerUltimo());
    }
}

// Padrão: Adapter
class IntegracaoDePagamentoEEntregaTest {

    @Test
    void pagamentoPorPixAprovado() {
        iPagamento pix = new PagamentoPix();
        assertTrue(pix.processar("João", 45.90));
    }

    @Test
    void formaPagamentoPix() {
        assertEquals("Pix", new PagamentoPix().getFormaPagamento());
    }

    @Test
    void comprovantePixNaoNulo() {
        iPagamento pix = new PagamentoPix();
        pix.processar("João", 45.90);
        assertNotNull(pix.getComprovante());
    }

    @Test
    void pagamentoCartaoCreditoAprovado() {
        iPagamento cartao = new PagamentoCartao("Crédito");
        assertTrue(cartao.processar("Maria", 89.80));
    }

    @Test
    void formaPagamentoCartao() {
        assertEquals("Cartão Crédito", new PagamentoCartao("Crédito").getFormaPagamento());
    }

    @Test
    void pagamentoLegadoViaAdapterAprovado() {
        iPagamento legado = new PagamentoLegadoAdapter(
                new SistemaPagamentoLegado(), "123.456.789-00");
        assertTrue(legado.processar("Ana", 67.40));
    }

    @Test
    void formaPagamentoLegadoViaAdapter() {
        iPagamento legado = new PagamentoLegadoAdapter(
                new SistemaPagamentoLegado(), "123.456.789-00");
        assertEquals("Sistema Legado (via Adapter)", legado.getFormaPagamento());
    }

    @Test
    void comprovanteAdapterNaoNulo() {
        iPagamento legado = new PagamentoLegadoAdapter(
                new SistemaPagamentoLegado(), "123.456.789-00");
        legado.processar("Ana", 67.40);
        assertNotNull(legado.getComprovante());
    }

    @Test
    void entregaExternaAdapterDespacha() {
        iEntrega entrega = new EntregaExternaAdapter(new SistemaEntregaExterna());
        assertTrue(entrega.despachar("Rua das Flores, 42", "36000-000"));
    }

    @Test
    void rastreamentoEntregaNaoNulo() {
        iEntrega entrega = new EntregaExternaAdapter(new SistemaEntregaExterna());
        entrega.despachar("Rua das Flores, 42", "36000-000");
        assertNotNull(entrega.getRastreamento());
    }

    @Test
    void taxaEntregaPositiva() {
        iEntrega entrega = new EntregaExternaAdapter(new SistemaEntregaExterna());
        assertTrue(entrega.getTaxaEntrega("36000-000") > 0);
    }

    @Test
    void polimorfismoPagamentoFuncionaParaTodosAdapters() {
        List<iPagamento> formas = List.of(
                new PagamentoPix(),
                new PagamentoCartao("Débito"),
                new PagamentoLegadoAdapter(new SistemaPagamentoLegado(), "000.000.000-00")
        );
        for (iPagamento f : formas) {
            assertTrue(f.processar("Teste", 10.00));
        }
    }
}

// Padrão: Proxy
class ControleDeAcessoAoCardapioTest {

    @Test
    void semUsuarioListagemVazia() {
        CardapioProxy proxy = new CardapioProxy();
        assertTrue(proxy.listarItens().isEmpty());
    }

    @Test
    void clienteLiberaLeitura() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("João", FuncionarioAcesso.Perfil.CLIENTE, "1234"));
        assertFalse(proxy.listarItens().isEmpty());
    }

    @Test
    void segundaListagemUsaCache() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("João", FuncionarioAcesso.Perfil.CLIENTE, "1234"));
        List<String> itens1 = proxy.listarItens();
        List<String> itens2 = proxy.listarItens();
        assertEquals(itens1.size(), itens2.size());
    }

    @Test
    void buscaItemExistente() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("João", FuncionarioAcesso.Perfil.CLIENTE, "1234"));
        assertFalse(proxy.buscarItem("Smash Burguer").contains("não encontrado"));
    }

    @Test
    void precoItemExistente() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("João", FuncionarioAcesso.Perfil.CLIENTE, "1234"));
        assertTrue(proxy.consultarPreco("X-Burguer") > 0);
    }

    @Test
    void clienteNaoPodeAdicionarItem() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("João", FuncionarioAcesso.Perfil.CLIENTE, "1234"));
        proxy.adicionarItem("Lanche VIP", 99.90);
        assertEquals(-1.0, proxy.consultarPreco("Lanche VIP"), 0.01);
    }

    @Test
    void gerentePodeAdicionarItem() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("Carlos", FuncionarioAcesso.Perfil.GERENTE, "9999"));
        proxy.adicionarItem("Truffle Burguer", 75.90);
        assertTrue(proxy.consultarPreco("Truffle Burguer") > 0);
    }

    @Test
    void gerenteAtualizaPreco() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("Carlos", FuncionarioAcesso.Perfil.GERENTE, "9999"));
        proxy.atualizarPreco("X-Burguer", 31.90);
        assertEquals(31.90, proxy.consultarPreco("X-Burguer"), 0.01);
    }

    @Test
    void gerenteRemoveItem() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("Carlos", FuncionarioAcesso.Perfil.GERENTE, "9999"));
        proxy.removerItem("Onion Rings");
        assertTrue(proxy.buscarItem("Onion Rings").contains("não encontrado"));
    }

    @Test
    void transparenciaViaInterface() {
        CardapioProxy proxy = new CardapioProxy();
        proxy.setUsuarioAtual(new FuncionarioAcesso("Carlos", FuncionarioAcesso.Perfil.GERENTE, "9999"));
        ICardapio cardapio = proxy;
        assertTrue(cardapio.consultarPreco("Smash Burguer") > 0);
    }
}

// Padrão: Interpreter
class CalculadoraDePrecosTest {

    @Test
    void valorNumericoInterpretaCorretamente() {
        ExpressaoCardapio val = new ValorNumerico(45.90, "Smash Burguer");
        assertEquals(45.90, val.interpretar(), 0.01);
    }

    @Test
    void valorNumericoRepresentaRotulo() {
        ExpressaoCardapio val = new ValorNumerico(45.90, "Smash Burguer");
        assertEquals("Smash Burguer", val.representar());
    }

    @Test
    void adicaoSomaDoisValores() {
        ExpressaoCardapio soma = new Adicao(
                new ValorNumerico(45.90, "Smash"),
                new ValorNumerico(9.90, "Batata"));
        assertEquals(55.80, soma.interpretar(), 0.01);
    }

    @Test
    void adicaoRepresentacaoContemSinal() {
        ExpressaoCardapio soma = new Adicao(
                new ValorNumerico(45.90, "Smash"),
                new ValorNumerico(9.90, "Batata"));
        assertTrue(soma.representar().contains("+"));
    }

    @Test
    void subtracaoCalculaCorretamente() {
        ExpressaoCardapio sub = new Subtracao(
                new ValorNumerico(100.00, "total"),
                new ValorNumerico(15.00, "desconto"));
        assertEquals(85.00, sub.interpretar(), 0.01);
    }

    @Test
    void subtracaoNegativaRetornaZero() {
        ExpressaoCardapio sub = new Subtracao(
                new ValorNumerico(10.00, "base"),
                new ValorNumerico(50.00, "desconto"));
        assertEquals(0.00, sub.interpretar(), 0.01);
    }

    @Test
    void multiplicacaoCalculaCorretamente() {
        ExpressaoCardapio mult = new Multiplicacao(
                new ValorNumerico(3, "3x"),
                new ValorNumerico(45.90, "Smash"));
        assertEquals(137.70, mult.interpretar(), 0.01);
    }

    @Test
    void divisaoCalculaCorretamente() {
        ExpressaoCardapio div = new Divisao(
                new ValorNumerico(100.00, "total"),
                new ValorNumerico(4, "pessoas"));
        assertEquals(25.00, div.interpretar(), 0.01);
    }

    @Test
    void divisaoPorZeroRetornaZero() {
        ExpressaoCardapio div = new Divisao(
                new ValorNumerico(100.00, "v"),
                new ValorNumerico(0, "zero"));
        assertEquals(0.00, div.interpretar(), 0.01);
    }

    @Test
    void percentualCalculaCorretamente() {
        ExpressaoCardapio perc = new Percentual(
                new ValorNumerico(200.00, "base"),
                new ValorNumerico(10, "10"));
        assertEquals(20.00, perc.interpretar(), 0.01);
    }

    @Test
    void expressaoCompostaCalculaCorretamente() {
        ExpressaoCardapio smash  = new ValorNumerico(45.90, "Smash");
        ExpressaoCardapio batata = new ValorNumerico(9.90, "Batata");
        ExpressaoCardapio subTotal = new Adicao(
                new Multiplicacao(new ValorNumerico(3, "3x"), smash),
                new Multiplicacao(new ValorNumerico(2, "2x"), batata));
        ExpressaoCardapio desc15   = new Percentual(subTotal, new ValorNumerico(15, "15"));
        ExpressaoCardapio comTaxa  = new Adicao(
                new Subtracao(subTotal, desc15),
                new ValorNumerico(8.50, "taxa"));
        assertEquals(142.375, comTaxa.interpretar(), 0.01);
    }

    @Test
    void calculadoraTotalCombo() {
        CalculadoraCardapio calc = new CalculadoraCardapio();
        assertEquals(55.80, calc.calcular(calc.totalCombo(45.90, 9.90)), 0.01);
    }

    @Test
    void calculadoraAplicaDesconto() {
        CalculadoraCardapio calc = new CalculadoraCardapio();
        assertEquals(85.00, calc.calcular(calc.aplicarDesconto(100.00, 15)), 0.01);
    }

    @Test
    void calculadoraDivideConta() {
        CalculadoraCardapio calc = new CalculadoraCardapio();
        assertEquals(25.00, calc.calcular(calc.dividirConta(100.00, 4)), 0.01);
    }

    @Test
    void calculadoraTotalComQuantidade() {
        CalculadoraCardapio calc = new CalculadoraCardapio();
        assertEquals(115.60, calc.calcular(calc.totalComQuantidade(28.90, 4)), 0.01);
    }

    @Test
    void pedidoInterpreterSomaTodosItens() {
        PedidoInterpreter pedido = new PedidoInterpreter("P-001", "João");
        pedido.adicionarExpressao(new ValorNumerico(45.90, "Smash"));
        pedido.adicionarExpressao(new ValorNumerico(9.90, "Batata"));
        pedido.adicionarExpressao(new ValorNumerico(6.90, "Refri"));
        assertEquals(62.70, pedido.calcularTotal(), 0.01);
    }

    @Test
    void hamburgueriaInterpreterCalculaFaturamento() {
        HamburgueriaInterpreter burger = new HamburgueriaInterpreter("Burger House");
        PedidoInterpreter p1 = new PedidoInterpreter("P-001", "Ana");
        p1.adicionarExpressao(new ValorNumerico(45.90, "Smash"));
        p1.adicionarExpressao(new ValorNumerico(6.90, "Refri"));
        PedidoInterpreter p2 = new PedidoInterpreter("P-002", "Pedro");
        p2.adicionarExpressao(new ValorNumerico(28.90, "X-Burguer"));
        p2.adicionarExpressao(new ValorNumerico(9.90, "Batata"));
        burger.adicionarPedido(p1);
        burger.adicionarPedido(p2);
        assertEquals(91.60, burger.calcularFaturamentoDoDia(), 0.01);
    }

    @Test
    void hamburgueriaInterpreterCalculaTicketMedio() {
        HamburgueriaInterpreter burger = new HamburgueriaInterpreter("Burger House");
        PedidoInterpreter p1 = new PedidoInterpreter("P-001", "Ana");
        p1.adicionarExpressao(new ValorNumerico(45.90, "Smash"));
        p1.adicionarExpressao(new ValorNumerico(6.90, "Refri"));
        PedidoInterpreter p2 = new PedidoInterpreter("P-002", "Pedro");
        p2.adicionarExpressao(new ValorNumerico(28.90, "X-Burguer"));
        p2.adicionarExpressao(new ValorNumerico(9.90, "Batata"));
        burger.adicionarPedido(p1);
        burger.adicionarPedido(p2);
        assertEquals(45.80, burger.calcularTicketMedio(), 0.01);
    }
}