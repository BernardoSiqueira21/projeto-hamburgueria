package com.hamburgueria.ciclo_pedido;

public class PedidoEstadoEmPreparo extends PedidoEstado {

    @Override
    public void confirmar(PedidoContext pedido) {
        System.out.println("Pedido já foi confirmado e está sendo preparado.");
    }

    @Override
    public void preparar(PedidoContext pedido) {
        System.out.println("Pedido já está em preparo.");
    }

    @Override
    public void entregar(PedidoContext pedido) {
        pedido.setEstado(new PedidoEstadoEntregue());
    }

    @Override
    public void cancelar(PedidoContext pedido) {
        System.out.println("Não é possível cancelar pedido em preparo.");
    }

    @Override
    public void devolver(PedidoContext pedido) {
        System.out.println("Pedido em preparo não pode ser devolvido ainda.");
    }

    @Override
    public String getNome() { return "EM_PREPARO"; }
}