package com.hamburgueria;

import com.hamburgueria.carrinho_compras.*;
import com.hamburgueria.auditoria_cardapio.*;
import com.hamburgueria.personalizacao_lanche.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Padrão: Memento
class HistoricoDoCarrinhoTest {

    @Test
    void carrinhoIniciaVazio() {
        Carrinho carrinho = new Carrinho("João");
        assertEquals(0.0, carrinho.getTotal(), 0.01);
    }

    @Test
    void adicionarItemAtualizaTotal() {
        Carrinho carrinho = new Carrinho("João");
        carrinho.adicionarItem("X-Burguer", 28.90);
        assertEquals(28.90, carrinho.getTotal(), 0.01);
    }

    @Test
    void totalComTresItens() {
        Carrinho carrinho = new Carrinho("João");
        carrinho.adicionarItem("X-Burguer", 28.90);
        carrinho.adicionarItem("Batata G", 14.90);
        carrinho.adicionarItem("Refri", 6.90);
        assertEquals(50.70, carrinho.getTotal(), 0.01);
    }

    @Test
    void historicoArmazenaSnapshots() {
        Carrinho carrinho = new Carrinho("João");
        HistoricoCarrinho h = new HistoricoCarrinho();
        carrinho.adicionarItem("X-Burguer", 28.90); h.push(carrinho.salvar());
        carrinho.adicionarItem("Batata G", 14.90);  h.push(carrinho.salvar());
        carrinho.adicionarItem("Refri", 6.90);      h.push(carrinho.salvar());
        assertEquals(3, h.tamanho());
    }

    @Test
    void restaurarSnapshotMaisRecente() {
        Carrinho carrinho = new Carrinho("João");
        HistoricoCarrinho h = new HistoricoCarrinho();
        carrinho.adicionarItem("X-Burguer", 28.90); h.push(carrinho.salvar());
        carrinho.adicionarItem("Batata G", 14.90);  h.push(carrinho.salvar());
        carrinho.adicionarItem("Refri", 6.90);      h.push(carrinho.salvar());
        CarrinhoSnapshot s = h.pop();
        assertNotNull(s);
        carrinho.restaurar(s);
        assertEquals(50.70, carrinho.getTotal(), 0.01);
    }

    @Test
    void restaurarSnapshotDoMeio() {
        Carrinho carrinho = new Carrinho("João");
        HistoricoCarrinho h = new HistoricoCarrinho();
        carrinho.adicionarItem("X-Burguer", 28.90); h.push(carrinho.salvar());
        carrinho.adicionarItem("Batata G", 14.90);  h.push(carrinho.salvar());
        carrinho.adicionarItem("Refri", 6.90);      h.push(carrinho.salvar());
        h.pop();
        CarrinhoSnapshot s = h.pop();
        carrinho.restaurar(s);
        assertEquals(43.80, carrinho.getTotal(), 0.01);
    }

    @Test
    void historicoVazioRetornaNull() {
        HistoricoCarrinho h = new HistoricoCarrinho();
        assertNull(h.pop());
    }
}

// Padrão: Visitor
class AuditoriaDoCardapioTest {

    @Test
    void precoFinalComboComDesconto() {
        ItemCardapio xBurg  = new ItemCardapio("X-Burguer", "Lanches", 28.90, 620, true);
        ItemCardapio batata = new ItemCardapio("Batata P",  "Acomps",   9.90, 320, true);
        ItemCardapio refri  = new ItemCardapio("Refri",     "Bebidas",  6.90, 140, true);
        ComboCardapio combo = new ComboCardapio("Combo Classico",
                List.of(xBurg, batata, refri), 10.0);
        assertEquals(41.13, combo.getPrecoFinal(), 0.01);
    }

    @Test
    void totalCaloriasDoCombo() {
        ItemCardapio xBurg  = new ItemCardapio("X-Burguer", "Lanches", 28.90, 620, true);
        ItemCardapio batata = new ItemCardapio("Batata P",  "Acomps",   9.90, 320, true);
        ItemCardapio refri  = new ItemCardapio("Refri",     "Bebidas",  6.90, 140, true);
        ComboCardapio combo = new ComboCardapio("Combo Classico",
                List.of(xBurg, batata, refri), 10.0);
        assertEquals(1080.0, combo.getTotalCalorias(), 0.01);
    }

    @Test
    void promocaoAtiva() {
        Promocao promo = new Promocao("Happy Hour", "30% off", 30.0,
                LocalDate.now().plusDays(10), true);
        assertTrue(promo.isAtiva());
    }

    @Test
    void promocaoValidadeNaoVencida() {
        Promocao promo = new Promocao("Happy Hour", "30% off", 30.0,
                LocalDate.now().plusDays(10), true);
        assertFalse(promo.getValidade().isBefore(LocalDate.now()));
    }

    @Test
    void visitanteRelatorioPercorreItens() {
        ItemCardapio smash = new ItemCardapio("Smash Burguer", "Lanches", 45.90, 850, true);
        VisitanteRelatorio rel = new VisitanteRelatorio();
        assertDoesNotThrow(() -> smash.aceitar(rel));
        assertEquals(1, rel.getQtdItens());
    }

    @Test
    void visitanteRelatorioIgnoraItemIndisponivel() {
        ItemCardapio indisponivel = new ItemCardapio("Mega XXL", "Lanches", 59.90, 1250, false);
        VisitanteRelatorio rel = new VisitanteRelatorio();
        indisponivel.aceitar(rel);
        assertEquals(0, rel.getQtdItens());
    }

    @Test
    void visitanteAuditoriaPercorreCombo() {
        ItemCardapio xBurg = new ItemCardapio("X-Burguer", "Lanches", 28.90, 620, true);
        ComboCardapio combo = new ComboCardapio("Combo", List.of(xBurg), 10.0);
        VisitanteAuditoria aud = new VisitanteAuditoria();
        assertDoesNotThrow(() -> combo.aceitar(aud));
    }

    @Test
    void visitanteDescontoPercorrePromocao() {
        Promocao promo = new Promocao("Promo", "20% off", 20.0,
                LocalDate.now().plusDays(5), true);
        VisitanteDesconto desc = new VisitanteDesconto(5.0);
        assertDoesNotThrow(() -> promo.aceitar(desc));
    }
}

// Padrão: Decorator
class PersonalizacaoDeLancheTest {

    @Test
    void precoBaseCorreto() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        assertEquals(20.00, base.getPreco(), 0.01);
    }

    @Test
    void caloriasBaseCorretas() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        assertEquals(500.0, base.getCalorias(), 0.01);
    }

    @Test
    void queijoAumentaPreco() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        IngredienteDecorator comQueijo = new ComQueijo(base);
        assertEquals(23.00, comQueijo.getPreco(), 0.01);
    }

    @Test
    void queijoAumentaCalorias() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        IngredienteDecorator comQueijo = new ComQueijo(base);
        assertEquals(590.0, comQueijo.getCalorias(), 0.01);
    }

    @Test
    void queijoApareceNaDescricao() {
        IngredienteDecorator comQueijo = new ComQueijo(new LancheBase("X-Burguer", 20.00, 500));
        assertTrue(comQueijo.getDescricao().contains("Queijo"));
    }

    @Test
    void baconAumentaPrecoAposQueijo() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        IngredienteDecorator comBacon = new ComBacon(new ComQueijo(base));
        assertEquals(28.00, comBacon.getPreco(), 0.01);
    }

    @Test
    void duploQueijoDobraOAcrescimo() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        IngredienteDecorator duplo = new ComQueijo(new ComQueijo(base));
        assertEquals(26.00, duplo.getPreco(), 0.01);
    }

    @Test
    void todosOsDecoradoresAcumulam() {
        IngredienteDecorator base = new LancheBase("X-Burguer", 20.00, 500);
        IngredienteDecorator completo = new ComQueijo(new ComBacon(new ComAlface(
                new ComTomate(new ComPickles(new ComOvo(new ComCatupiry(
                        new ComMolhoEspecial(base))))))));
        assertTrue(completo.getPreco() > base.getPreco());
        assertTrue(completo.getCalorias() > base.getCalorias());
        assertTrue(completo.getDescricao().length() > base.getDescricao().length());
    }
}