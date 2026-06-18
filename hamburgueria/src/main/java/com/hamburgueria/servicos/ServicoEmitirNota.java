package com.hamburgueria.servicos;

public class ServicoEmitirNota implements IServico {
    @Override
    public void executar() {
        System.out.println("[ServicoEmitirNota] Emitindo nota fiscal do pedido...");
    }

    @Override
    public String getNome() { return "EmitirNota"; }
}