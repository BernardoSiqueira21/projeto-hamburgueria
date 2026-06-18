package com.hamburgueria.calculadora_precos;

public class Subtracao implements ExpressaoCardapio {

    private final ExpressaoCardapio esquerda;
    private final ExpressaoCardapio direita;

    public Subtracao(ExpressaoCardapio esquerda, ExpressaoCardapio direita) {
        this.esquerda = esquerda;
        this.direita  = direita;
    }

    @Override
    public double interpretar() {
        double resultado = esquerda.interpretar() - direita.interpretar();
        return resultado < 0 ? 0 : resultado;
    }

    @Override
    public String representar() {
        return "(" + esquerda.representar() + " - " + direita.representar() + ")";
    }
}