package com.hamburgueria.calculadora_precos;

import com.hamburgueria.calculo_preco.Calculadora;
import com.hamburgueria.calculo_preco.OperacaoDesconto;
import com.hamburgueria.calculo_preco.OperacaoTaxaEntrega;

public class CalculadoraCardapio {

    private final Calculadora calculadora = new Calculadora(new OperacaoDesconto(0));

    public double calcular(ExpressaoCardapio expressao) {
        return expressao.interpretar();
    }

    public ExpressaoCardapio totalCombo(double preco1, double preco2) {
        return new Adicao(
                new ValorNumerico(preco1, "item1"),
                new ValorNumerico(preco2, "item2")
        );
    }

    public ExpressaoCardapio aplicarDesconto(double total, double percentualDesconto) {
        calculadora.setOperacao(new OperacaoDesconto(percentualDesconto));
        double valorFinal = calculadora.calcular(total);
        return new ValorNumerico(valorFinal, "total_com_desconto");
    }

    public ExpressaoCardapio aplicarTaxaEntrega(double total, double taxa) {
        calculadora.setOperacao(new OperacaoTaxaEntrega(taxa));
        double valorFinal = calculadora.calcular(total);
        return new ValorNumerico(valorFinal, "total_com_taxa");
    }

    public ExpressaoCardapio dividirConta(double total, int pessoas) {
        return new Divisao(
                new ValorNumerico(total, "total"),
                new ValorNumerico(pessoas, pessoas + " pessoas")
        );
    }

    public ExpressaoCardapio totalComQuantidade(double precoUnitario, int quantidade) {
        return new Multiplicacao(
                new ValorNumerico(precoUnitario, "preco"),
                new ValorNumerico(quantidade, quantidade + "x")
        );
    }
}