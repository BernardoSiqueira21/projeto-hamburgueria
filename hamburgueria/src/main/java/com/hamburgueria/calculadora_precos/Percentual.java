package com.hamburgueria.calculadora_precos;

public class Percentual implements ExpressaoCardapio {

    private final ExpressaoCardapio base;
    private final ExpressaoCardapio porcentagem;

    public Percentual(ExpressaoCardapio base, ExpressaoCardapio porcentagem) {
        this.base        = base;
        this.porcentagem = porcentagem;
    }

    @Override
    public double interpretar() {
        return base.interpretar() * (porcentagem.interpretar() / 100.0);
    }

    @Override
    public String representar() {
        return "(" + porcentagem.representar() + "% de " + base.representar() + ")";
    }
}