package com.hamburgueria;

import com.hamburgueria.calculo_preco.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculoDePrecosTest {

    @Test
    void semDescontoRetornaValorOriginal() {
        Calculadora calc = new Calculadora(new OperacaoSemDesconto());
        assertEquals(100.00, calc.calcular(100.00), 0.01);
    }

    @Test
    void descontoDezPorCentoCalculadoCorretamente() {
        Calculadora calc = new Calculadora(new OperacaoDesconto(10));
        assertEquals(90.00, calc.calcular(100.00), 0.01);
    }

    @Test
    void acrescimoCincoPorCentoCalculadoCorretamente() {
        Calculadora calc = new Calculadora(new OperacaoAcrescimo(5));
        assertEquals(105.00, calc.calcular(100.00), 0.01);
    }

    @Test
    void taxaEntregaAdicionadaAoTotal() {
        Calculadora calc = new Calculadora(new OperacaoTaxaEntrega(8.50));
        assertEquals(108.50, calc.calcular(100.00), 0.01);
    }

    @Test
    void cupomDescontaValorFixo() {
        Calculadora calc = new Calculadora(new OperacaoCupom("BG15", 15.00));
        assertEquals(85.00, calc.calcular(100.00), 0.01);
    }

    @Test
    void programaFidelidadeDescontaPontos() {
        Calculadora calc = new Calculadora(new OperacaoFidelidade(200));
        assertEquals(80.00, calc.calcular(100.00), 0.01);
    }

    @Test
    void cupomMaiorQueValorResultaEmZero() {
        Calculadora calc = new Calculadora(new OperacaoCupom("SUPER", 200.00));
        assertEquals(0.00, calc.calcular(100.00), 0.01);
    }

    @Test
    void trocaDeEstrategiaEmTempoDeExecucao() {
        Calculadora calc = new Calculadora(new OperacaoSemDesconto());
        assertEquals(100.00, calc.calcular(100.00), 0.01);
        calc.setOperacao(new OperacaoDesconto(20));
        assertEquals(80.00, calc.calcular(100.00), 0.01);
        calc.setOperacao(new OperacaoTaxaEntrega(5.00));
        assertEquals(105.00, calc.calcular(100.00), 0.01);
    }
}