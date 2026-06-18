package com.hamburgueria.fabricacao_combos;

public class AcompanhamentoVegano implements Acompanhamento {
    @Override
    public String descrever() {
        return "Chips de couve com hummus";
    }

    @Override
    public double getPreco() { return 14.90; }
}