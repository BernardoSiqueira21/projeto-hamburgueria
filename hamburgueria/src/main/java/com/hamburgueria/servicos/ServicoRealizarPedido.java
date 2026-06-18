package com.hamburgueria.servicos;

public class ServicoRealizarPedido implements IServico {
    @Override
    public void executar() {
        System.out.println("[ServicoRealizarPedido] Registrando novo pedido no sistema...");
    }

    @Override
    public String getNome() { return "RealizarPedido"; }
}