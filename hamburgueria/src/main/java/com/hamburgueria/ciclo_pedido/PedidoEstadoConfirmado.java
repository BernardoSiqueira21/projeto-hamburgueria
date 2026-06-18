package com.hamburgueria.ciclo_pedido;

public class PedidoEstadoConfirmado extends PedidoEstado {

    @Override
    public void confirmar(PedidoContext pedido) {
        System.out.println("Pedido já está confirmado.");
    }

    @Override
    public void preparar(PedidoContext pedido) {
        pedido.setEstado(new PedidoEstadoEmPreparo());
    }

    @Override
    public void entregar(PedidoContext pedido) {
        System.out.println("Pedido ainda está sendo preparado.");
    }

    @Override
    public void cancelar(PedidoContext pedido) {
        pedido.setEstado(new PedidoEstadoCancelado());
    }

    @Override
    public void devolver(PedidoContext pedido) {
        System.out.println("Pedido confirmado não pode ser devolvido ainda.");
    }

    @Override
    public String getNome() { return "CONFIRMADO"; }
}