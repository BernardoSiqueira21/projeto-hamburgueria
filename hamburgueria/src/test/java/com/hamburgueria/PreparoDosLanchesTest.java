package com.hamburgueria;

import com.hamburgueria.preparo_cozinha.*;
import com.hamburgueria.comunicacao_interna.*;
import com.hamburgueria.operacoes_balcao.*;
import com.hamburgueria.cardapio.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Padrão: Template Method
class PreparoDosLanchesTest {

    @Test
    void preparaSmashBurguerSemExcecao() {
        assertDoesNotThrow(() -> new PreparoSmashBurguer().preparar());
    }

    @Test
    void preparaXBurguerSemExcecao() {
        assertDoesNotThrow(() -> new PreparoXBurguer().preparar());
    }

    @Test
    void preparaVeganoSemExcecao() {
        assertDoesNotThrow(() -> new PreparoVegano().preparar());
    }

    @Test
    void preparaFrangoSemExcecao() {
        assertDoesNotThrow(() -> new PreparoFrango().preparar());
    }

    @Test
    void obtemServicoEntregaViaFactoryMethod() {
        PreparoLanche preparo = new PreparoSmashBurguer();
        assertNotNull(preparo.obterServicoEntrega());
        assertEquals("EntregarPedido", preparo.obterServicoEntrega().getNome());
    }
}

// Padrão: Mediator
class ComunicacaoEntreSetoresTest {

    @Test
    void cozinhaPossuiNomeCorreto() {
        Central central = new Central();
        Cozinha cozinha = new Cozinha(central);
        assertEquals("Cozinha", cozinha.getNome());
    }

    @Test
    void clientePossuiNomeCorreto() {
        Central central = new Central();
        ClienteHamburgueria joao = new ClienteHamburgueria("João", central);
        assertEquals("João", joao.getNome());
    }

    @Test
    void clienteEnviaMensagemSemExcecao() {
        Central central = new Central();
        central.registrarSetor(new Cozinha(central));
        ClienteHamburgueria joao = new ClienteHamburgueria("João", central);
        central.registrarPessoa(joao);
        assertDoesNotThrow(() -> joao.enviarMensagem("Cozinha", "1 X-Burguer"));
    }

    @Test
    void broadcastSemExcecao() {
        Central central = new Central();
        central.registrarSetor(new Cozinha(central));
        central.registrarPessoa(new ClienteHamburgueria("João", central));
        assertDoesNotThrow(() -> central.broadcast("Central", "Fechando!"));
    }
}

// Padrão: Facade
class OperacoesDoBalcaoTest {

    @Test
    void pedidoBalcaoSemExcecao() {
        assertDoesNotThrow(() ->
                new PedidoFacade().realizarPedidoBalcao("João", "Smash", 45.90, "Pix"));
    }

    @Test
    void pedidoDeliverySemExcecao() {
        assertDoesNotThrow(() ->
                new PedidoFacade().realizarPedidoDelivery(
                        "Maria", "X-Tudo", 38.90, "Cartão", "Rua das Flores", "Centro"));
    }

    @Test
    void cancelamentoPedidoSemExcecao() {
        assertDoesNotThrow(() -> new PedidoFacade().cancelarPedido("Carlos", 38.90));
    }

    @Test
    void fechamentoCaixaSemExcecao() {
        assertDoesNotThrow(() -> new PedidoFacade().fecharCaixaDoDia());
    }

    @Test
    void filaCozinhaDisponivel() {
        assertNotNull(new PedidoFacade().getFilaCozinha());
    }
}

// Padrão: Composite
class EstruturadoCardapioTest {

    @Test
    void totalDeLanchesCalculadoCorretamente() {
        Combo lanches = new Combo("Lanches");
        lanches.adicionar(new Item("Smash Burguer", "blend 180g", 45.90));
        lanches.adicionar(new Item("X-Burguer", "carne 120g", 28.90));
        assertEquals(74.80, lanches.calcularTotal(), 0.01);
    }

    @Test
    void comboComSubCombosCalculadoCorretamente() {
        Combo lanches = new Combo("Lanches");
        lanches.adicionar(new Item("Smash Burguer", "blend 180g", 45.90));
        lanches.adicionar(new Item("X-Burguer", "carne 120g", 28.90));
        Combo cardapio = new Combo("Cardapio");
        cardapio.adicionar(lanches);
        cardapio.adicionar(new Item("Batata", "pequena", 9.90));
        assertEquals(84.70, cardapio.calcularTotal(), 0.01);
    }

    @Test
    void itemNaoAceitaFilhos() {
        Item smash = new Item("Smash", "blend", 45.90);
        Item batata = new Item("Batata", "p", 9.90);
        assertThrows(UnsupportedOperationException.class, () -> smash.adicionar(batata));
    }

    @Test
    void comboAceitaNovoItem() {
        Combo combo = new Combo("Combo");
        assertDoesNotThrow(() -> combo.adicionar(new Item("Smash", "blend", 45.90)));
    }

    @Test
    void comboRemoveItem() {
        Item smash = new Item("Smash", "blend", 45.90);
        Combo combo = new Combo("Combo");
        combo.adicionar(smash);
        combo.remover(smash);
        assertEquals(0.0, combo.calcularTotal(), 0.01);
    }

    @Test
    void cardapioRetornaTotalDaRaiz() {
        Combo raiz = new Combo("Raiz");
        raiz.adicionar(new Item("Smash", "blend", 45.90));
        raiz.adicionar(new Item("Batata", "p", 9.90));
        Cardapio cardapio = new Cardapio("Burger House", raiz);
        assertEquals(55.80, cardapio.getTotal(), 0.01);
    }
}