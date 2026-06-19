package com.hamburgueria;

import com.hamburgueria.configuracoes.Configuracoes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfiguracoesDaLanchoneteTest {

    @Test
    void retornaMesmaInstancia() {
        Configuracoes cfg1 = Configuracoes.getInstance();
        Configuracoes cfg2 = Configuracoes.getInstance();
        assertSame(cfg1, cfg2);
    }

    @Test
    void alteracaoRefletidaEmTodasReferencias() {
        Configuracoes cfg1 = Configuracoes.getInstance();
        Configuracoes cfg2 = Configuracoes.getInstance();
        cfg1.setTaxaEntrega(8.50);
        assertEquals(8.50, cfg2.getTaxaEntrega());
    }

    @Test
    void nomeEstabelecimentoCompartilhado() {
        Configuracoes cfg1 = Configuracoes.getInstance();
        Configuracoes cfg2 = Configuracoes.getInstance();
        cfg1.setNomeEstabelecimento("Burger House Premium");
        assertEquals("Burger House Premium", cfg2.getNomeEstabelecimento());
    }

    @Test
    void limiteDescontoAtendenteConfiguravel() {
        Configuracoes cfg = Configuracoes.getInstance();
        cfg.setLimiteDescontoAtendente(7.0);
        assertEquals(7.0, cfg.getLimiteDescontoAtendente());
    }

    @Test
    void limiteDescontoSupervisorConfiguravel() {
        Configuracoes cfg = Configuracoes.getInstance();
        cfg.setLimiteDescontoSupervisor(20.0);
        assertEquals(20.0, cfg.getLimiteDescontoSupervisor());
    }

    @Test
    void limiteDescontoGerenteConfiguravel() {
        Configuracoes cfg = Configuracoes.getInstance();
        cfg.setLimiteDescontoGerente(35.0);
        assertEquals(35.0, cfg.getLimiteDescontoGerente());
    }

    @Test
    void tempoMedioPreparoConfiguravel() {
        Configuracoes cfg = Configuracoes.getInstance();
        cfg.setTempoMedioPreparo(25);
        assertEquals(25, cfg.getTempoMedioPreparo());
    }
}