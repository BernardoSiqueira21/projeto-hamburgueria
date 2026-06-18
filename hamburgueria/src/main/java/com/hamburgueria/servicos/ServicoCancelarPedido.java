package com.hamburgueria.servicos;

public class ServicoCancelarPedido implements IServico {
    @Override
    public void executar() {
        System.out.println("[ServicoCancelarPedido] Cancelando pedido e reembolsando cliente...");
    }

    @Override
    public String getNome() { return "CancelarPedido"; }
}