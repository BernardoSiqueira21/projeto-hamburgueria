package com.hamburgueria.servicos;

public class ServicoEntregarPedido implements IServico {
    @Override
    public void executar() {
        System.out.println("[ServicoEntregarPedido] Despachando pedido para entrega...");
    }

    @Override
    public String getNome() { return "EntregarPedido"; }
}