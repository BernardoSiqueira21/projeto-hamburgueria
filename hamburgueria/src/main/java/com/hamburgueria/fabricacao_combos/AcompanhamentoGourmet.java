package com.hamburgueria.fabricacao_combos;

public class AcompanhamentoGourmet implements Acompanhamento {
    @Override
    public String descrever() {
        return "Onion Rings com molho chipotle";
    }

    @Override
    public double getPreco() { return 19.90; }
}