package com.hamburgueria.calculadora_precos;

public class Divisao implements ExpressaoCardapio {

    private final ExpressaoCardapio esquerda;
    private final ExpressaoCardapio direita;

    public Divisao(ExpressaoCardapio esquerda, ExpressaoCardapio direita) {
        this.esquerda = esquerda;
        this.direita  = direita;
    }

    @Override
    public double interpretar() {
        double divisor = direita.interpretar();
        if (divisor == 0) {
            return 0;
        }
        return esquerda.interpretar() / divisor;
    }

    @Override
    public String representar() {
        return "(" + esquerda.representar() + " ÷ " + direita.representar() + ")";
    }
}