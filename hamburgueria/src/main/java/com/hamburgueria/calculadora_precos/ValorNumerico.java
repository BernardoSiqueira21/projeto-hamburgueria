package com.hamburgueria.calculadora_precos;

public class ValorNumerico implements ExpressaoCardapio {

    private final double valor;
    private final String rotulo;

    public ValorNumerico(double valor) {
        this.valor  = valor;
        this.rotulo = String.format("%.2f", valor);
    }

    public ValorNumerico(double valor, String rotulo) {
        this.valor  = valor;
        this.rotulo = rotulo;
    }

    @Override
    public double interpretar() { return valor; }

    @Override
    public String representar() { return rotulo; }
}