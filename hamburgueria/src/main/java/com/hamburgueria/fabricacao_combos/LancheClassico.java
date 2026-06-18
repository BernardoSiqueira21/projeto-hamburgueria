package com.hamburgueria.fabricacao_combos;

public class LancheClassico implements Lanche {
    @Override
    public String descrever() {
        return "X-Burguer Clássico (pão, carne 120g, queijo, alface, tomate)";
    }

    @Override
    public double getPreco() { return 25.90; }
}