package com.hamburgueria.calculadora_precos;

public class CalculadoraCardapio {


    public double calcular(ExpressaoCardapio expressao) {
        double resultado = expressao.interpretar();
        return resultado;
    }

    public ExpressaoCardapio totalCombo(double preco1, double preco2) {
        return new Adicao(
                new ValorNumerico(preco1, "item1"),
                new ValorNumerico(preco2, "item2")
        );
    }

    public ExpressaoCardapio aplicarDesconto(double total, double percentualDesconto) {
        ExpressaoCardapio base     = new ValorNumerico(total, "total");
        ExpressaoCardapio desconto = new Percentual(base,
                new ValorNumerico(percentualDesconto, percentualDesconto + "%"));
        return new Subtracao(base, desconto);
    }

    public ExpressaoCardapio dividirConta(double total, int pessoas) {
        return new Divisao(
                new ValorNumerico(total, "total"),
                new ValorNumerico(pessoas, pessoas + " pessoas")
        );
    }

    public ExpressaoCardapio totalComQuantidade(double precoUnitario, int quantidade) {
        return new Multiplicacao(
                new ValorNumerico(precoUnitario, "preço"),
                new ValorNumerico(quantidade, quantidade + "x")
        );
    }
}