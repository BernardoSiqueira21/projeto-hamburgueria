package com.hamburgueria;

import com.hamburgueria.montagem_lanche.*;
import com.hamburgueria.personalizacao_lanche.IngredienteDecorator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Padrão: Builder — montagem passo a passo de lanches personalizados
class MontagemDeLancheTest {

    @Test
    void lancheTemNomeCorreto() {
        LanchePersonalizado lanche = new LancheBuilder()
                .iniciar("X-Simples", "Pão", "Carne 120g")
                .definirPrecoBase(18.00).build();
        assertEquals("X-Simples", lanche.getNome());
    }

    @Test
    void precoBaseDefinidoCorretamente() {
        LanchePersonalizado lanche = new LancheBuilder()
                .iniciar("X-Simples", "Pão", "Carne 120g")
                .definirPrecoBase(18.00).build();
        assertEquals(18.00, lanche.getPreco(), 0.01);
    }

    @Test
    void adicionarQueijoAumentaPreco() {
        LanchePersonalizado lanche = new LancheBuilder()
                .iniciar("X-Queijo", "Pão", "Carne 120g")
                .definirPrecoBase(18.00).adicionarQueijo().build();
        assertTrue(lanche.temQueijo());
        assertEquals(21.00, lanche.getPreco(), 0.01);
    }

    @Test
    void adicionarBaconAumentaPreco() {
        LanchePersonalizado lanche = new LancheBuilder()
                .iniciar("X-Bacon", "Pão", "Carne 120g")
                .definirPrecoBase(18.00).adicionarBacon().build();
        assertTrue(lanche.temBacon());
    }

    @Test
    void semBaconRetornaFalse() {
        LanchePersonalizado lanche = new LancheBuilder()
                .iniciar("X-Simples", "Pão", "Carne 120g")
                .definirPrecoBase(18.00).adicionarQueijo().build();
        assertFalse(lanche.temBacon());
    }

    @Test
    void lancheTudoComTodosIngredientes() {
        LanchePersonalizado lanche = new LancheBuilder()
                .iniciar("X-Tudo", "Brioche", "Blend 180g")
                .definirPrecoBase(28.00).adicionarQueijo().adicionarBacon()
                .adicionarAlface().adicionarTomate().adicionarPickles()
                .adicionarMolho("Molho Especial").build();
        assertTrue(lanche.temBacon());
        assertTrue(lanche.temPickles());
        assertNotNull(lanche.getMolho());
    }

    @Test
    void buildDecoradoRetornaIngredienteDecorator() {
        LancheBuilder builder = new LancheBuilder();
        builder.iniciar("X-Decorado", "Pão", "Carne 120g")
                .definirPrecoBase(18.00).adicionarQueijo().adicionarBacon();
        IngredienteDecorator decorado = builder.buildDecorado();
        assertNotNull(decorado);
        assertTrue(decorado.getPreco() > 18.00);
    }

    @Test
    void buildDecoradoIncluiDescricaoDosExtras() {
        LancheBuilder builder = new LancheBuilder();
        builder.iniciar("X-Decorado", "Pão", "Carne 120g")
                .definirPrecoBase(18.00).adicionarQueijo();
        IngredienteDecorator decorado = builder.buildDecorado();
        assertTrue(decorado.getDescricao().contains("Queijo"));
    }
}