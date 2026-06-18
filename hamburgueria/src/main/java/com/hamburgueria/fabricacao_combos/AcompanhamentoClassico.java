package com.hamburgueria.fabricacao_combos;

public class AcompanhamentoClassico implements Acompanhamento {
    @Override
    public String descrever() {
        return "Batata Frita Clássica (porção média)";
    }

    @Override
    public double getPreco() { return 12.90; }
}