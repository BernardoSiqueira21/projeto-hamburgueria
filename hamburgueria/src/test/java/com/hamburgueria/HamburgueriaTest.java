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


    @Test
    void navegacaoPeloCardapio() {
        CardapioHamburgueria card = new CardapioHamburgueria();
        card.adicionarItem(new ItemPedido("Smash Burguer","Lanches",45.90,850,true));
        card.adicionarItem(new ItemPedido("X-Burguer",    "Lanches",28.90,620,true));
        card.adicionarItem(new ItemPedido("Batata Frita P","Acomps", 9.90,320,true));
        card.adicionarItem(new ItemPedido("Onion Rings",  "Acomps", 16.90,410,false));
        card.adicionarItem(new ItemPedido("Refri 350ml",  "Bebidas", 6.90,140,true));

        Iterador it = card.criarIterador();
        int count = 0;
        while (it.temProximo()) { it.proximo(); count++; }
        assertEquals(5, count);

        it.reiniciar();
        assertTrue(it.temProximo());

        Iterador itL = card.criarIteradorPorCategoria("Lanches");
        int qtdLanches = 0;
        while (itL.temProximo()) { itL.proximo(); qtdLanches++; }
        assertEquals(2, qtdLanches);

        IteradorDisponiveis itD = (IteradorDisponiveis) card.criarIteradorDisponiveis();
        assertEquals(4, itD.totalDisponiveis());

        Iterador itP = card.criarIteradorPorFaixaPreco(5.00, 15.00);
        int qtdFaixa = 0;
        while (itP.temProximo()) { itP.proximo(); qtdFaixa++; }
        assertEquals(2, qtdFaixa); // Batata e Refri

        assertFalse(card.criarIteradorPorCategoria("Cafeteria").temProximo());
    }


    @Test
    void clonagemdePedidos() {
        EnderecoEntrega end   = new EnderecoEntrega("Rua X","10","Centro","JF","");
        EnderecoEntrega clone = end.clonar();

        assertNotSame(end, clone);
        assertEquals(end.toString(), clone.toString());

        clone.setRua("Av. Brasil");
        assertNotEquals(end.getRua(), clone.getRua()); // clone independente

        PedidoTemplate orig = new PedidoTemplate("João","Pix","",true,
                new EnderecoEntrega("Rua Y","1","Bairro","JF",""));
        orig.adicionarItem("X-Burguer");
        PedidoTemplate copia = orig.clonar();

        assertNotSame(orig, copia);
        assertNotSame(orig.getEndereco(), copia.getEndereco()); // deep copy
        copia.adicionarItem("Batata");
        assertNotEquals(orig.getItens().size(), copia.getItens().size());

        RegistroPedidos reg = new RegistroPedidos();
        PedidoTemplate tVeg = new PedidoTemplate("Cliente","Pix","",false,null);
        tVeg.adicionarItem("Green Burguer");
        reg.registrar("vegano", tVeg);

        PedidoTemplate c1 = reg.clonarTemplate("vegano");
        PedidoTemplate c2 = reg.clonarTemplate("vegano");
        assertNotSame(c1, c2);
        c1.adicionarItem("Suco");
        assertNotEquals(c1.getItens().size(), tVeg.getItens().size());

        assertThrows(IllegalArgumentException.class,
                () -> reg.clonarTemplate("inexistente"));
    }


    @BeforeEach
    void limparCacheFlyweight() {
        IngredienteFactory.limparCache();
    }

    @Test
    void compartilhamentoDeIngredientes() {
        Ingrediente pao   = IngredienteFactory.obter("Pão Brioche",    "Pão",      1.50,200,"Macio");
        Ingrediente carne = IngredienteFactory.obter("Blend 180g",     "Carne",    8.00,400,"Bovino");
        Ingrediente queij = IngredienteFactory.obter("Queijo Cheddar", "Laticínio",2.00, 90,"Cremoso");

        assertSame(pao,   IngredienteFactory.obter("Pão Brioche"));
        assertSame(carne, IngredienteFactory.obter("Blend 180g"));
        assertSame(queij, IngredienteFactory.obter("Queijo Cheddar"));
        assertEquals(3, IngredienteFactory.totalInstancias());

        Pedido p1 = new Pedido("001","João");
        Pedido p2 = new Pedido("002","Maria");
        p1.adicionarIngrediente(pao,1); p1.adicionarIngrediente(carne,1);
        p2.adicionarIngrediente(pao,1); p2.adicionarIngrediente(queij,1);

        assertEquals(3, IngredienteFactory.totalInstancias());

        assertThrows(IllegalArgumentException.class,
                () -> IngredienteFactory.obter("Trufas Negras"));
    }


    @Test
    void filaDeComandasDaCozinha() {
        FilaCozinha fila = new FilaCozinha();
        PedidoCozinha pedido = new PedidoCozinha("001","João");

        fila.executar(new AbrirPedidoComando(pedido));
        assertEquals("ABERTO", pedido.getStatus());

        fila.executar(new AdicionarItemComando(pedido,"Smash Burguer"));
        assertTrue(pedido.getItens().contains("Smash Burguer"));

        fila.executar(new IniciarPreparoComando(pedido));
        assertEquals("EM_PREPARO", pedido.getStatus());

        fila.executar(new FinalizarPreparoComando(pedido));
        assertEquals("PRONTO", pedido.getStatus());

        fila.desfazerUltimo();
        assertEquals("EM_PREPARO", pedido.getStatus());

        PedidoCozinha p2 = new PedidoCozinha("002","Maria");
        FilaCozinha fb = new FilaCozinha();
        fb.enfileirar(new AbrirPedidoComando(p2));
        fb.enfileirar(new AdicionarItemComando(p2,"X-Burguer"));
        fb.enfileirar(new FecharPedidoComando(p2));
        assertEquals(3, fb.totalNaFila());

        fb.executarFila();
        assertEquals("FECHADO", p2.getStatus());
        assertEquals(0, fb.totalNaFila());

        fb.desfazerTodos();
        assertEquals("CANCELADO", p2.getStatus());

        PedidoCozinha p3 = new PedidoCozinha("003","Carlos");
        FilaCozinha fc = new FilaCozinha();
        fc.executar(new AbrirPedidoComando(p3));
        fc.executar(new CancelarPedidoComando(p3));
        assertEquals("CANCELADO", p3.getStatus());
        fc.desfazerUltimo(); // desfaz o cancelar → chama abrir()
        assertEquals("ABERTO", p3.getStatus());

        assertDoesNotThrow(() -> new FilaCozinha().desfazerUltimo());
    }


    @Test
    void integracaoDePagamentoEEntrega() {
        iPagamento pix = new PagamentoPix();
        assertTrue(pix.processar("João", 45.90));
        assertEquals("Pix", pix.getFormaPagamento());
        assertNotNull(pix.getComprovante());

        iPagamento cartao = new PagamentoCartao("Crédito");
        assertTrue(cartao.processar("Maria", 89.80));
        assertEquals("Cartão Crédito", cartao.getFormaPagamento());

        iPagamento legado = new PagamentoLegadoAdapter(
                new SistemaPagamentoLegado(), "123.456.789-00");
        assertTrue(legado.processar("Ana", 67.40));
        assertEquals("Sistema Legado (via Adapter)", legado.getFormaPagamento());
        assertNotNull(legado.getComprovante());

        iEntrega entrega = new EntregaExternaAdapter(new SistemaEntregaExterna());
        assertTrue(entrega.despachar("Rua das Flores, 42","36000-000"));
        assertNotNull(entrega.getRastreamento());
        assertTrue(entrega.getTaxaEntrega("36000-000") > 0);

        List<iPagamento> formas = List.of(
                new PagamentoPix(),
                new PagamentoCartao("Débito"),
                new PagamentoLegadoAdapter(new SistemaPagamentoLegado(),"000.000.000-00")
        );
        for (iPagamento f : formas) {
            assertTrue(f.processar("Teste", 10.00));
        }
    }


    @Test
    void controleDeAcessoAoCardapio() {
        CardapioProxy proxy = new CardapioProxy();

        assertTrue(proxy.listarItens().isEmpty());

        proxy.setUsuarioAtual(new FuncionarioAcesso(
                "João", FuncionarioAcesso.Perfil.CLIENTE, "1234"));

        List<String> itens = proxy.listarItens();
        assertFalse(itens.isEmpty()); // lazy init funcionou
        List<String> itensCached = proxy.listarItens();
        assertEquals(itens.size(), itensCached.size());
        assertFalse(proxy.buscarItem("Smash Burguer").contains("não encontrado"));
        assertTrue(proxy.consultarPreco("X-Burguer") > 0);
        proxy.adicionarItem("Lanche VIP", 99.90);
        assertEquals(-1.0, proxy.consultarPreco("Lanche VIP"), 0.01);

        proxy.setUsuarioAtual(new FuncionarioAcesso(
                "Carlos", FuncionarioAcesso.Perfil.GERENTE,"9999"));
        proxy.adicionarItem("Truffle Burguer", 75.90);
        assertTrue(proxy.consultarPreco("Truffle Burguer") > 0);

        proxy.atualizarPreco("X-Burguer", 31.90);
        assertEquals(31.90, proxy.consultarPreco("X-Burguer"), 0.01);

        proxy.removerItem("Onion Rings");
        assertTrue(proxy.buscarItem("Onion Rings").contains("não encontrado"));

        ICardapio cardapio = proxy;
        assertTrue(cardapio.consultarPreco("Smash Burguer") > 0);
    }


    @Test
    void calculadoraDeExpressoesDePreco() {
        ExpressaoCardapio smash  = new ValorNumerico(45.90, "Smash Burguer");
        ExpressaoCardapio xBurg  = new ValorNumerico(28.90, "X-Burguer");
        ExpressaoCardapio batata = new ValorNumerico(9.90,  "Batata Frita P");
        ExpressaoCardapio refri  = new ValorNumerico(6.90,  "Refri 350ml");

        assertEquals(45.90, smash.interpretar(), 0.01);
        assertEquals("Smash Burguer", smash.representar());

        ExpressaoCardapio soma = new Adicao(smash, batata);
        assertEquals(55.80, soma.interpretar(), 0.01);
        assertTrue(soma.representar().contains("+"));

        ExpressaoCardapio sub = new Subtracao(
                new ValorNumerico(100.00,"total"),
                new ValorNumerico(15.00, "desconto"));
        assertEquals(85.00, sub.interpretar(), 0.01);

        ExpressaoCardapio subNeg = new Subtracao(
                new ValorNumerico(10.00,"base"),
                new ValorNumerico(50.00,"desconto"));
        assertEquals(0.00, subNeg.interpretar(), 0.01);

        ExpressaoCardapio mult = new Multiplicacao(
                new ValorNumerico(3,"3x"), smash);
        assertEquals(137.70, mult.interpretar(), 0.01);

        ExpressaoCardapio div = new Divisao(
                new ValorNumerico(100.00,"total"),
                new ValorNumerico(4,"4 pessoas"));
        assertEquals(25.00, div.interpretar(), 0.01);

        ExpressaoCardapio divZero = new Divisao(
                new ValorNumerico(100.00,"v"),
                new ValorNumerico(0,"zero"));
        assertEquals(0.00, divZero.interpretar(), 0.01);

        ExpressaoCardapio perc = new Percentual(
                new ValorNumerico(200.00,"base"),
                new ValorNumerico(10,"10"));
        assertEquals(20.00, perc.interpretar(), 0.01);

        ExpressaoCardapio subTotal = new Adicao(
                new Multiplicacao(new ValorNumerico(3,"3x"), smash),
                new Multiplicacao(new ValorNumerico(2,"2x"), batata));
        assertEquals(157.50, subTotal.interpretar(), 0.01);

        ExpressaoCardapio desc15   = new Percentual(subTotal, new ValorNumerico(15,"15"));
        ExpressaoCardapio aposDesc = new Subtracao(subTotal, desc15);
        ExpressaoCardapio comTaxa  = new Adicao(aposDesc, new ValorNumerico(8.50,"taxa"));
        assertEquals(142.375, comTaxa.interpretar(), 0.01);

        CalculadoraCardapio calc = new CalculadoraCardapio();
        assertEquals(55.80, calc.calcular(calc.totalCombo(45.90, 9.90)), 0.01);
        assertEquals(85.00, calc.calcular(calc.aplicarDesconto(100.00, 15)), 0.01);
        assertEquals(25.00, calc.calcular(calc.dividirConta(100.00, 4)), 0.01);
        assertEquals(115.60,calc.calcular(calc.totalComQuantidade(28.90, 4)), 0.01);

        PedidoInterpreter pedido = new PedidoInterpreter("P-001","João");
        pedido.adicionarExpressao(smash);
        pedido.adicionarExpressao(batata);
        pedido.adicionarExpressao(refri);
        assertEquals(62.70, pedido.calcularTotal(), 0.01);

        HamburgueriaInterpreter burger = new HamburgueriaInterpreter("Burger House");
        PedidoInterpreter p1 = new PedidoInterpreter("P-001","Ana");
        p1.adicionarExpressao(smash); p1.adicionarExpressao(refri);
        PedidoInterpreter p2 = new PedidoInterpreter("P-002","Pedro");
        p2.adicionarExpressao(xBurg); p2.adicionarExpressao(batata);
        burger.adicionarPedido(p1); burger.adicionarPedido(p2);
        assertEquals(91.60, burger.calcularFaturamentoDoDia(), 0.01);
        assertEquals(45.80, burger.calcularTicketMedio(), 0.01);
    }
}