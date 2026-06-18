package com.hamburgueria.fabricacao_combos;

public class FabricaGourmet implements FabricaAbstrata {
    @Override
    public Lanche criarLanche() { return new LancheGourmet(); }

    @Override
    public Acompanhamento criarAcompanhamento() { return new AcompanhamentoGourmet(); }
}