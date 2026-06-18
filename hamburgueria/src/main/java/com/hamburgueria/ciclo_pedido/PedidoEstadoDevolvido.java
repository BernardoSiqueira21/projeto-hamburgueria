package com.hamburgueria.ciclo_pedido;

public class PedidoEstadoDevolvido extends PedidoEstado {

    @Override
    public void confirmar(PedidoContext pedido) {
        System.out.println("Pedido devolvido. Faça novo pedido se desejar.");
    }

    @Override
    public void preparar(PedidoContext pedido) {
        System.out.println("Pedido devolvido.");
    }

    @Override
    public void entregar(PedidoContext pedido) {
        System.out.println("Pedido devolvido.");
    }

    @Override
    public void cancelar(PedidoContext pedido) {
        System.out.println("Pedido já foi devolvido.");
    }

    @Override
    public void devolver(PedidoContext pedido) {
        System.out.println("Pedido já foi devolvido.");
    }

    @Override
    public String getNome() { return "DEVOLVIDO"; }
}