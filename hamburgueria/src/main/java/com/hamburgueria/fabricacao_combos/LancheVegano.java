package com.hamburgueria.fabricacao_combos;

public class LancheVegano implements Lanche {
    @Override
    public String descrever() {
        return "Green Burguer (pão integral, burger de grão-de-bico, queijo vegano, rúcula, tomate)";
    }

    @Override
    public double getPreco() { return 35.90; }
}