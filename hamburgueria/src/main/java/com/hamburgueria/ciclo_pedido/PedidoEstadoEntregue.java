package com.hamburgueria.ciclo_pedido;

public class PedidoEstadoEntregue extends PedidoEstado {

    @Override
    public void confirmar(PedidoContext pedido) {
        System.out.println("Pedido já foi entregue.");
    }

    @Override
    public void preparar(PedidoContext pedido) {
        System.out.println("Pedido já foi entregue.");
    }

    @Override
    public void entregar(PedidoContext pedido) {
        System.out.println("Pedido já foi entregue.");
    }

    @Override
    public void cancelar(PedidoContext pedido) {
        System.out.println("Pedido já entregue não pode ser cancelado.");
    }

    @Override
    public void devolver(PedidoContext pedido) {
        pedido.setEstado(new PedidoEstadoDevolvido());
    }

    @Override
    public String getNome() { return "ENTREGUE"; }
}