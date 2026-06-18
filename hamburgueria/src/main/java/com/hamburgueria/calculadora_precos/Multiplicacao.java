package com.hamburgueria.calculadora_precos;

public class Multiplicacao implements ExpressaoCardapio {

    private final ExpressaoCardapio esquerda;
    private final ExpressaoCardapio direita;

    public Multiplicacao(ExpressaoCardapio esquerda, ExpressaoCardapio direita) {
        this.esquerda = esquerda;
        this.direita  = direita;
    }

    @Override
    public double interpretar() {
        return esquerda.interpretar() * direita.interpretar();
    }

    @Override
    public String representar() {
        return "(" + esquerda.representar() + " × " + direita.representar() + ")";
    }
}