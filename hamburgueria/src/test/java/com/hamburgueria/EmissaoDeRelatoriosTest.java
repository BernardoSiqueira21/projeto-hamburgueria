package com.hamburgueria;

import com.hamburgueria.relatorios.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmissaoDeRelatoriosTest {

    @Test
    void gerenteTemCargoCorreto() {
        Funcionario gerente = new FuncionarioGerente(new RelatorioFinanceiro());
        assertEquals("Gerente", gerente.getCargo());
    }

    @Test
    void caixaTemCargoCorreto() {
        Funcionario caixa = new FuncionarioCaixa(new RelatorioVendas());
        assertEquals("Caixa", caixa.getCargo());
    }

    @Test
    void cozinheiroTemCargoCorreto() {
        Funcionario cozinheiro = new FuncionarioCozinheiro(new RelatorioEstoque());
        assertEquals("Cozinheiro", cozinheiro.getCargo());
    }

    @Test
    void atendenteTemCargoCorreto() {
        Funcionario atendente = new FuncionarioAtendente(new RelatorioPedidos());
        assertEquals("Atendente", atendente.getCargo());
    }

    @Test
    void gerenteEmiteRelatorioFinanceiro() {
        Funcionario gerente = new FuncionarioGerente(new RelatorioFinanceiro());
        assertDoesNotThrow(gerente::emitirRelatorio);
    }

    @Test
    void gerenteTrocaRelatorioEmTempoDeExecucao() {
        Funcionario gerente = new FuncionarioGerente(new RelatorioFinanceiro());
        gerente.setRelatorio(new RelatorioDesempenho());
        assertDoesNotThrow(gerente::emitirRelatorio);
    }

    @Test
    void caixaEmiteRelatorioVendas() {
        Funcionario caixa = new FuncionarioCaixa(new RelatorioVendas());
        assertDoesNotThrow(caixa::emitirRelatorio);
    }

    @Test
    void mesmoFuncionarioFuncionaComQualquerRelatorio() {
        Funcionario gerente = new FuncionarioGerente(new RelatorioFinanceiro());
        for (TipoRelatorio rel : new TipoRelatorio[]{
                new RelatorioVendas(), new RelatorioEstoque(),
                new RelatorioPedidos(), new RelatorioDesempenho()}) {
            gerente.setRelatorio(rel);
            assertDoesNotThrow(gerente::emitirRelatorio);
        }
    }
}