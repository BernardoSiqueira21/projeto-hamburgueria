package com.hamburgueria;

import com.hamburgueria.navegacao_cardapio.*;
import com.hamburgueria.templates_pedido.*;
import com.hamburgueria.ingredientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Padrão: Iterator
class NavegacaoPeloCardapioTest {

    private CardapioHamburgueria cardapio;

    @BeforeEach
    void montarCardapio() {
        cardapio = new CardapioHamburgueria();
        cardapio.adicionarItem(new ItemPedido("Smash Burguer", "Lanches", 45.90, 850, true));
        cardapio.adicionarItem(new ItemPedido("X-Burguer",     "Lanches", 28.90, 620, true));
        cardapio.adicionarItem(new ItemPedido("Batata Frita P","Acomps",   9.90, 320, true));
        cardapio.adicionarItem(new ItemPedido("Onion Rings",   "Acomps",  16.90, 410, false));
        cardapio.adicionarItem(new ItemPedido("Refri 350ml",   "Bebidas",  6.90, 140, true));
    }

    @Test
    void iteradorPercorreTodosOsItens() {
        Iterador it = cardapio.criarIterador();
        int count = 0;
        while (it.temProximo()) { it.proximo(); count++; }
        assertEquals(5, count);
    }

    @Test
    void reiniciarPermiteIterarNovamente() {
        Iterador it = cardapio.criarIterador();
        while (it.temProximo()) it.proximo();
        it.reiniciar();
        assertTrue(it.temProximo());
    }

    @Test
    void iteradorPorCategoriaFiltroLanches() {
        Iterador itL = cardapio.criarIteradorPorCategoria("Lanches");
        int count = 0;
        while (itL.temProximo()) { itL.proximo(); count++; }
        assertEquals(2, count);
    }

    @Test
    void iteradorPorCategoriaFiltroAcomps() {
        Iterador itA = cardapio.criarIteradorPorCategoria("Acomps");
        int count = 0;
        while (itA.temProximo()) { itA.proximo(); count++; }
        assertEquals(2, count);
    }

    @Test
    void iteradorDisponiveisRetornaCorretamente() {
        IteradorDisponiveis itD = (IteradorDisponiveis) cardapio.criarIteradorDisponiveis();
        assertEquals(4, itD.totalDisponiveis());
    }

    @Test
    void iteradorFaixaPrecoFiltraCorretamente() {
        Iterador itP = cardapio.criarIteradorPorFaixaPreco(5.00, 15.00);
        int count = 0;
        while (itP.temProximo()) { itP.proximo(); count++; }
        assertEquals(2, count);
    }

    @Test
    void categoriaInexistenteRetornaVazio() {
        assertFalse(cardapio.criarIteradorPorCategoria("Cafeteria").temProximo());
    }
}

// Padrão: Prototype
class ClonagemdePedidosTest {

    @Test
    void cloneEnderecoEhInstanciasDiferente() {
        EnderecoEntrega end   = new EnderecoEntrega("Rua X", "10", "Centro", "JF", "");
        EnderecoEntrega clone = end.clonar();
        assertNotSame(end, clone);
    }

    @Test
    void cloneEnderecoTemMesmosValores() {
        EnderecoEntrega end   = new EnderecoEntrega("Rua X", "10", "Centro", "JF", "");
        EnderecoEntrega clone = end.clonar();
        assertEquals(end.toString(), clone.toString());
    }

    @Test
    void alterarCloneNaoAfetaOriginal() {
        EnderecoEntrega end   = new EnderecoEntrega("Rua X", "10", "Centro", "JF", "");
        EnderecoEntrega clone = end.clonar();
        clone.setRua("Av. Brasil");
        assertNotEquals(end.getRua(), clone.getRua());
    }

    @Test
    void clonePedidoEhInstanciasDiferente() {
        PedidoTemplate orig = new PedidoTemplate("João", "Pix", "", true,
                new EnderecoEntrega("Rua Y", "1", "Bairro", "JF", ""));
        PedidoTemplate copia = orig.clonar();
        assertNotSame(orig, copia);
    }

    @Test
    void clonePedidoFazDeepCopyDoEndereco() {
        PedidoTemplate orig = new PedidoTemplate("João", "Pix", "", true,
                new EnderecoEntrega("Rua Y", "1", "Bairro", "JF", ""));
        PedidoTemplate copia = orig.clonar();
        assertNotSame(orig.getEndereco(), copia.getEndereco());
    }

    @Test
    void clonePedidoItensSaoIndependentes() {
        PedidoTemplate orig = new PedidoTemplate("João", "Pix", "", true, null);
        orig.adicionarItem("X-Burguer");
        PedidoTemplate copia = orig.clonar();
        copia.adicionarItem("Batata");
        assertNotEquals(orig.getItens().size(), copia.getItens().size());
    }

    @Test
    void registroClonaTemplateCorretamente() {
        RegistroPedidos reg = new RegistroPedidos();
        PedidoTemplate tVeg = new PedidoTemplate("Cliente", "Pix", "", false, null);
        tVeg.adicionarItem("Green Burguer");
        reg.registrar("vegano", tVeg);
        PedidoTemplate c1 = reg.clonarTemplate("vegano");
        PedidoTemplate c2 = reg.clonarTemplate("vegano");
        assertNotSame(c1, c2);
    }

    @Test
    void clonarTemplateNaoAfetaOriginal() {
        RegistroPedidos reg = new RegistroPedidos();
        PedidoTemplate tVeg = new PedidoTemplate("Cliente", "Pix", "", false, null);
        tVeg.adicionarItem("Green Burguer");
        reg.registrar("vegano", tVeg);
        PedidoTemplate c1 = reg.clonarTemplate("vegano");
        c1.adicionarItem("Suco");
        assertNotEquals(c1.getItens().size(), tVeg.getItens().size());
    }

    @Test
    void templateInexistenteLancaExcecao() {
        RegistroPedidos reg = new RegistroPedidos();
        assertThrows(IllegalArgumentException.class,
                () -> reg.clonarTemplate("inexistente"));
    }
}

// Padrão: Flyweight
class CompartilhamentoDeIngredientesTest {

    @BeforeEach
    void limparCache() {
        IngredienteFactory.limparCache();
    }

    @Test
    void reutilizaMesmaInstanciaDeIngrediente() {
        Ingrediente pao  = IngredienteFactory.obter("Pao Brioche","Pao",1.50,200,"Macio");
        Ingrediente pao2 = IngredienteFactory.obter("Pao Brioche");
        assertSame(pao, pao2);
    }

    @Test
    void totalInstanciasCorreto() {
        IngredienteFactory.obter("Pao Brioche","Pao",  1.50,200,"Macio");
        IngredienteFactory.obter("Blend 180g", "Carne",8.00,400,"Bovino");
        IngredienteFactory.obter("Queijo",     "Latic",2.00, 90,"Cremoso");
        assertEquals(3, IngredienteFactory.totalInstancias());
    }

    @Test
    void doisPedidosNaoCriaNovasInstancias() {
        Ingrediente pao   = IngredienteFactory.obter("Pao","Pao",  1.50,200,"Macio");
        Ingrediente carne = IngredienteFactory.obter("Carne","Carne",8.00,400,"Bovino");
        Pedido p1 = new Pedido("001", "Joao");
        Pedido p2 = new Pedido("002", "Maria");
        p1.adicionarIngrediente(pao, 1); p1.adicionarIngrediente(carne, 1);
        p2.adicionarIngrediente(pao, 1); p2.adicionarIngrediente(carne, 1);
        assertEquals(2, IngredienteFactory.totalInstancias());
    }

    @Test
    void ingredienteInexistenteLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> IngredienteFactory.obter("Trufas Negras"));
    }

    @Test
    void limparCacheZeraInstancias() {
        IngredienteFactory.obter("Pao","Pao",1.50,200,"Macio");
        IngredienteFactory.limparCache();
        assertEquals(0, IngredienteFactory.totalInstancias());
    }
}