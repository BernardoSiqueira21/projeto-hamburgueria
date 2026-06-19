package com.hamburgueria;

import com.hamburgueria.fabricacao_combos.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Padrão: Abstract Factory — fabricação de famílias de lanches e acompanhamentos
class FabricacaoDeCombosTest {

    @Test
    void fabricaClassicoCriaLanche() {
        FabricaAbstrata fabrica = FabricaProvider.getFabrica("CLASSICO");
        assertNotNull(fabrica.criarLanche());
    }

    @Test
    void fabricaClassicoCriaAcompanhamento() {
        FabricaAbstrata fabrica = FabricaProvider.getFabrica("CLASSICO");
        assertNotNull(fabrica.criarAcompanhamento());
    }

    @Test
    void fabricaGourmetCriaLanche() {
        FabricaAbstrata fabrica = FabricaProvider.getFabrica("GOURMET");
        assertNotNull(fabrica.criarLanche());
    }

    @Test
    void fabricaGourmetTemPrecoMaiorQueClassico() {
        FabricaAbstrata classico = FabricaProvider.getFabrica("CLASSICO");
        FabricaAbstrata gourmet  = FabricaProvider.getFabrica("GOURMET");
        assertTrue(gourmet.criarLanche().getPreco() > classico.criarLanche().getPreco());
    }

    @Test
    void fabricaVeganoCriaLanche() {
        FabricaAbstrata fabrica = FabricaProvider.getFabrica("VEGANO");
        assertNotNull(fabrica.criarLanche());
    }

    @Test
    void fabricaVeganoCriaAcompanhamento() {
        FabricaAbstrata fabrica = FabricaProvider.getFabrica("VEGANO");
        assertNotNull(fabrica.criarAcompanhamento());
    }

    @Test
    void fabricaInvalidaLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> FabricaProvider.getFabrica("INVALIDA"));
    }

    @Test
    void fabricaCriaTemplateCombo() {
        FabricaAbstrata fabrica = FabricaProvider.getFabrica("CLASSICO");
        assertNotNull(fabrica.criarTemplateCombo());
        assertFalse(fabrica.criarTemplateCombo().getItens().isEmpty());
    }
}