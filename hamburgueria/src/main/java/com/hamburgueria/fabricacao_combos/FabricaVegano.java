package com.hamburgueria.fabricacao_combos;

public class FabricaVegano implements FabricaAbstrata {
    @Override
    public Lanche criarLanche() { return new LancheVegano(); }

    @Override
    public Acompanhamento criarAcompanhamento() { return new AcompanhamentoVegano(); }
}