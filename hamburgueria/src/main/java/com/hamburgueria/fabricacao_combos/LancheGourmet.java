package com.hamburgueria.fabricacao_combos;

public class LancheGourmet implements Lanche {
    @Override
    public String descrever() {
        return "Smash Burguer Gourmet (brioche, blend 180g, cheddar artesanal, bacon, aioli trufado)";
    }

    @Override
    public double getPreco() { return 45.90; }
}