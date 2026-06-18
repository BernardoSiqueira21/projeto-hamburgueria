package com.hamburgueria;

import com.hamburgueria.ciclo_pedido.*;
import com.hamburgueria.notificacoes.*;
import com.hamburgueria.configuracoes.Configuracoes;
import com.hamburgueria.servicos.*;
import com.hamburgueria.fabricacao_combos.*;
import com.hamburgueria.relatorios.*;
import com.hamburgueria.calculo_preco.*;
import com.hamburgueria.montagem_lanche.*;
import com.hamburgueria.aprovacao_desconto.*;
import com.hamburgueria.preparo_cozinha.*;
import com.hamburgueria.comunicacao_interna.*;
import com.hamburgueria.operacoes_balcao.*;
import com.hamburgueria.cardapio.*;
import com.hamburgueria.carrinho_compras.*;
import com.hamburgueria.auditoria_cardapio.*;
import com.hamburgueria.personalizacao_lanche.IngredienteDecorator;
import com.hamburgueria.personalizacao_lanche.LancheBase;
import com.hamburgueria.personalizacao_lanche.ComQueijo;
import com.hamburgueria.personalizacao_lanche.ComBacon;
import com.hamburgueria.personalizacao_lanche.ComAlface;
import com.hamburgueria.personalizacao_lanche.ComTomate;
import com.hamburgueria.personalizacao_lanche.ComPickles;
import com.hamburgueria.personalizacao_lanche.ComOvo;
import com.hamburgueria.personalizacao_lanche.ComCatupiry;
import com.hamburgueria.personalizacao_lanche.ComMolhoEspecial;
import com.hamburgueria.navegacao_cardapio.*;
import com.hamburgueria.templates_pedido.*;
import com.hamburgueria.ingredientes.*;
import com.hamburgueria.fila_cozinha.*;
import com.hamburgueria.pagamento_entrega.*;
import com.hamburgueria.acesso_cardapio.*;
import com.hamburgueria.calculadora_precos.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class HamburgueriaTest {
    
    @Test
    void cicloDeVidaDoPedido() {
        PedidoContext pedido = new PedidoContext("P-001");
        assertEquals("PENDENTE", pedido.getEstado().getNome());

        pedido.confirmar();
        assertEquals("CONFIRMADO", pedido.getEstado().getNome());

        pedido.preparar();
        assertEquals("EM_PREPARO", pedido.getEstado().getNome());

        pedido.entregar();
        assertEquals("ENTREGUE", pedido.getEstado().getNome());

        pedido.devolver();
        assertEquals("DEVOLVIDO", pedido.getEstado().getNome());

        PedidoContext p2 = new PedidoContext("P-002");
        p2.confirmar();
        p2.cancelar();
        assertEquals("CANCELADO", p2.getEstado().getNome());

        p2.confirmar();
        assertEquals("CANCELADO", p2.getEstado().getNome());

        PedidoContext p3 = new PedidoContext("P-003");
        p3.preparar();
        assertEquals("PENDENTE", p3.getEstado().getNome());
    }

    @Test
    void notificacaoDeStatusDaMesa() {
        Mesa mesa = new Mesa(1);
        Cliente joao  = new Cliente("João");
        Cliente maria = new Cliente("Maria");

        mesa.adicionarObserver(joao);
        mesa.adicionarObserver(maria);
        assertEquals("LIVRE", mesa.getStatusPedido());

        PedidoContext pedido = new PedidoContext("P-OBS-01");
        mesa.setPedidoAtual(pedido);
        assertEquals("PENDENTE", mesa.getStatusPedido());

        mesa.avancarEstadoPedido(); // PENDENTE → CONFIRMADO → notifica observers
        assertEquals("CONFIRMADO", mesa.getStatusPedido());
        assertEquals("CONFIRMADO", mesa.getPedidoAtual().getEstado().getNome());

        mesa.avancarEstadoPedido(); // CONFIRMADO → EM_PREPARO → notifica observers
        assertEquals("EM_PREPARO", mesa.getStatusPedido());

        mesa.removerObserver(joao);
        mesa.setStatusPedido("ENTREGUE");
        assertEquals("ENTREGUE", mesa.getStatusPedido());
    }

    @Test
    void configuracaoGlobalDaLanchonete() {
        Configuracoes cfg1 = Configuracoes.getInstance();
        Configuracoes cfg2 = Configuracoes.getInstance();

        assertSame(cfg1, cfg2);

        cfg1.setTaxaEntrega(8.50);
        assertEquals(8.50, cfg2.getTaxaEntrega());

        cfg1.setNomeEstabelecimento("Burger House Premium");
        assertEquals("Burger House Premium", cfg2.getNomeEstabelecimento());
    }

    @Test
    void criacaoDeServicos() {
        IServico realizar = ServicoFactory.obterServico("REALIZAR_PEDIDO");
        assertEquals("RealizarPedido", realizar.getNome());

        IServico emitir = ServicoFactory.obterServico("EMITIR_NOTA");
        assertEquals("EmitirNota", emitir.getNome());

        IServico cancelar = ServicoFactory.obterServico("CANCELAR_PEDIDO");
        assertEquals("CancelarPedido", cancelar.getNome());

        IServico entregar = ServicoFactory.obterServico("ENTREGAR_PEDIDO");
        assertEquals("EntregarPedido", entregar.getNome());

        IServico pagamento = ServicoFactory.obterServico("PROCESSAR_PAGAMENTO");
        assertEquals("ProcessarPagamento", pagamento.getNome());

        assertThrows(IllegalArgumentException.class,
                () -> ServicoFactory.obterServico("INVALIDO"));
    }


    @Test
    void fabricacaoDeCombos() {
        FabricaAbstrata classico = FabricaProvider.getFabrica("CLASSICO");
        assertNotNull(classico.criarLanche());
        assertNotNull(classico.criarAcompanhamento());

        FabricaAbstrata gourmet = FabricaProvider.getFabrica("GOURMET");
        assertTrue(gourmet.criarLanche().getPreco() > classico.criarLanche().getPreco());

        FabricaAbstrata vegano = FabricaProvider.getFabrica("VEGANO");
        assertNotNull(vegano.criarLanche());

        assertThrows(IllegalArgumentException.class,
                () -> FabricaProvider.getFabrica("INVALIDA"));
    }

    @Test
    void emissaoDeRelatorios() {
        TipoRelatorio financeiro  = new RelatorioFinanceiro();
        TipoRelatorio vendas      = new RelatorioVendas();
        TipoRelatorio desempenho  = new RelatorioDesempenho();

        Funcionario gerente = new FuncionarioGerente(financeiro);
        assertEquals("Gerente", gerente.getCargo());

        Funcionario caixa = new FuncionarioCaixa(vendas);
        assertEquals("Caixa", caixa.getCargo());

        gerente.setRelatorio(desempenho);
        assertDoesNotThrow(() -> gerente.emitirRelatorio());
    }

    @Test
    void calculoDePrecos() {
        double valor = 100.00;
        Calculadora calc = new Calculadora(new OperacaoSemDesconto());
        assertEquals(100.00, calc.calcular(valor), 0.01);

        calc.setOperacao(new OperacaoDesconto(10));
        assertEquals(90.00, calc.calcular(valor), 0.01);

        calc.setOperacao(new OperacaoAcrescimo(5));
        assertEquals(105.00, calc.calcular(valor), 0.01);

        calc.setOperacao(new OperacaoTaxaEntrega(8.50));
        assertEquals(108.50, calc.calcular(valor), 0.01);

        calc.setOperacao(new OperacaoCupom("BG15", 15.00));
        assertEquals(85.00, calc.calcular(valor), 0.01);

        calc.setOperacao(new OperacaoFidelidade(200));
        assertEquals(80.00, calc.calcular(valor), 0.01);

        calc.setOperacao(new OperacaoCupom("SUPER", 200.00));
        assertEquals(0.00, calc.calcular(valor), 0.01);
    }

    @Test
    void montagemDeLanchePersonalizado() {
        LancheBuilder b = new LancheBuilder();

        LanchePersonalizado simples = b.iniciar("X-Simples","Pão","Carne 120g")
                .definirPrecoBase(18.00).adicionarQueijo().adicionarTomate().build();

        assertEquals("X-Simples", simples.getNome());
        assertTrue(simples.temQueijo());
        assertTrue(simples.temTomate());
        assertFalse(simples.temBacon());
        assertEquals(22.00, simples.getPreco(), 0.01); // 18 + 3 queijo + 1 tomate

        LanchePersonalizado completo = b.iniciar("X-Tudo","Brioche","Blend 180g")
                .definirPrecoBase(28.00).adicionarQueijo().adicionarBacon()
                .adicionarAlface().adicionarTomate().adicionarPickles()
                .adicionarMolho("Molho Especial").build();

        assertTrue(completo.temBacon());
        assertTrue(completo.temPickles());
        assertNotNull(completo.getMolho());
    }


    @Test
    void aprovacaoDeDescontos() {
        Atendente at  = new Atendente();
        Supervisor su = new Supervisor();
        Gerente    ge = new Gerente();
        Dono       do_ = new Dono();
        at.setProximo(su); su.setProximo(ge); ge.setProximo(do_);

        assertDoesNotThrow(() -> at.processar(new SolicitacaoDesconto(1,"João",89.90,3,"Frequente")));
        assertDoesNotThrow(() -> at.processar(new SolicitacaoDesconto(2,"Maria",150.00,10,"Aniversário")));
        assertDoesNotThrow(() -> at.processar(new SolicitacaoDesconto(3,"Carlos",320.00,25,"Corporativo")));
        assertDoesNotThrow(() -> at.processar(new SolicitacaoDesconto(4,"Inf",500.00,50,"Parceria")));

        SolicitacaoDesconto s = new SolicitacaoDesconto(5,"Ana",100.00,5,"Teste");
        assertEquals("Ana",   s.getCliente());
        assertEquals(5.0,     s.getPercentualDesconto(), 0.01);
        assertEquals(100.00,  s.getValorPedido(), 0.01);
    }

    @Test
    void preparoDosLanches() {
        assertDoesNotThrow(() -> new PreparoSmashBurguer().preparar());
        assertDoesNotThrow(() -> new PreparoXBurguer().preparar());
        assertDoesNotThrow(() -> new PreparoVegano().preparar());
        assertDoesNotThrow(() -> new PreparoFrango().preparar());
    }

    @Test
    void comunicacaoEntreSetores() {
        Central central = new Central();
        Cozinha cozinha = new Cozinha(central);
        central.registrarSetor(cozinha);
        assertEquals("Cozinha", cozinha.getNome());

        ClienteHamburgueria joao = new ClienteHamburgueria("João", central);
        central.registrarPessoa(joao);
        assertEquals("João", joao.getNome());

        assertDoesNotThrow(() -> joao.enviarMensagem("Cozinha","1 X-Burguer"));
        assertDoesNotThrow(() -> central.broadcast("Central","Fechando!"));
    }


    @Test
    void operacoesDoBalcao() {
        PedidoFacade facade = new PedidoFacade();
        assertDoesNotThrow(() -> facade.realizarPedidoBalcao("João","Smash",45.90,"Pix"));
        assertDoesNotThrow(() -> facade.realizarPedidoDelivery("Maria","X-Tudo",38.90,
                "Cartão","Rua das Flores","Centro"));
        assertDoesNotThrow(() -> facade.cancelarPedido("Carlos",38.90));
        assertDoesNotThrow(() -> facade.fecharCaixaDoDia());
    }

    @Test
    void estruturaDoCardapio() {
        Item smash = new Item("Smash Burguer","blend 180g",45.90);
        Item batat = new Item("Batata Frita P","pequena",9.90);

        Combo lanches = new Combo("Lanches");
        lanches.adicionar(smash);
        lanches.adicionar(new Item("X-Burguer","carne 120g",28.90));

        assertEquals(74.80, lanches.calcularTotal(), 0.01);

        Combo card = new Combo("Cardápio");
        card.adicionar(lanches);
        card.adicionar(batat);
        assertEquals(84.70, card.calcularTotal(), 0.01);

        assertThrows(UnsupportedOperationException.class, () -> smash.adicionar(batat));
    }


    @Test
    void historicoDoCarrinho() {
        Carrinho carrinho = new Carrinho("João");
        HistoricoCarrinho h = new HistoricoCarrinho();

        carrinho.adicionarItem("X-Burguer", 28.90); h.push(carrinho.salvar());
        carrinho.adicionarItem("Batata G",  14.90); h.push(carrinho.salvar());
        carrinho.adicionarItem("Refri",      6.90); h.push(carrinho.salvar());

        assertEquals(50.70, carrinho.getTotal(), 0.01);
        assertEquals(3, h.tamanho());

        CarrinhoSnapshot s1 = h.pop();
        assertNotNull(s1);
        carrinho.restaurar(s1);
        assertEquals(50.70, carrinho.getTotal(), 0.01);

        CarrinhoSnapshot s2 = h.pop();
        assertNotNull(s2);
        carrinho.restaurar(s2);
        assertEquals(43.80, carrinho.getTotal(), 0.01);
        h.pop(); h.pop();
        assertNull(h.pop());
    }


    @Test
    void auditoriaDoCardapio() {
        ItemCardapio smash = new ItemCardapio("Smash Burguer","Lanches",45.90,850,true);
        ItemCardapio xBurg = new ItemCardapio("X-Burguer","Lanches",28.90,620,true);
        ItemCardapio calor = new ItemCardapio("Mega XXL","Lanches",59.90,1250,true);

        ComboCardapio combo = new ComboCardapio("Combo Clássico",
                List.of(xBurg, new ItemCardapio("Batata P","Acomps",9.90,320,true),
                        new ItemCardapio("Refri","Bebidas",6.90,140,true)), 10.0);
        assertEquals(41.13, combo.getPrecoFinal(), 0.01);
        assertEquals(1080.0, combo.getTotalCalorias(), 0.01);

        Promocao promo = new Promocao("Happy Hour","30% off",30.0,
                LocalDate.now().plusDays(10), true);
        assertTrue(promo.isAtiva());
        assertFalse(promo.getValidade().isBefore(LocalDate.now()));

        VisitanteRelatorio rel = new VisitanteRelatorio();
        assertDoesNotThrow(() -> {
            smash.aceitar(rel); xBurg.aceitar(rel);
            calor.aceitar(rel); combo.aceitar(rel); promo.aceitar(rel);
        });

        VisitanteAuditoria aud = new VisitanteAuditoria();
        assertDoesNotThrow(() -> calor.aceitar(aud)); // deve detectar calorias altas

        VisitanteDesconto desc = new VisitanteDesconto(20.0);
        assertDoesNotThrow(() -> combo.aceitar(desc));
    }


    @Test
    void personalizacaoDeLanche() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        assertEquals(20.00, base.getPreco(), 0.01);
        assertEquals(500.0, base.getCalorias(), 0.01);

        IngredienteDecorator comQueijo = new ComQueijo(base);
        assertEquals(23.00, comQueijo.getPreco(), 0.01);
        assertEquals(590.0, comQueijo.getCalorias(), 0.01);
        assertTrue(comQueijo.getDescricao().contains("Queijo"));

        IngredienteDecorator comBacon = new ComBacon(comQueijo);
        assertEquals(28.00, comBacon.getPreco(), 0.01);

        IngredienteDecorator duplo = new ComQueijo(new ComQueijo(base));
        assertEquals(26.00, duplo.getPreco(), 0.01);

        IngredienteDecorator completo = new ComQueijo(new ComBacon(new ComAlface(
                new ComTomate(new ComPickles(new ComOvo(new ComCatupiry(
                        new ComMolhoEspecial(base))))))));
        assertTrue(completo.getPreco() > base.getPreco());
        assertTrue(completo.getCalorias() > base.getCalorias());
        assertTrue(completo.getDescricao().length() > base.getDescricao().length());
    }


