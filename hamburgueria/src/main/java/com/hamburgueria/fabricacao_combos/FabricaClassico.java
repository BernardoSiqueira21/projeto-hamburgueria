package com.hamburgueria.fabricacao_combos;

public class FabricaClassico implements FabricaAbstrata {
    @Override
    public Lanche criarLanche() { return new LancheClassico(); }

    @Override
    public Acompanhamento criarAcompanhamento() { return new AcompanhamentoClassico(); }
}