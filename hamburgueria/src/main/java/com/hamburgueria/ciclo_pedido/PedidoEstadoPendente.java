package com.hamburgueria.ciclo_pedido;

public class PedidoEstadoPendente extends PedidoEstado {

    @Override
    public void confirmar(PedidoContext pedido) {
        pedido.setEstado(new PedidoEstadoConfirmado());
    }

    @Override public void preparar(PedidoContext p)  {}
    @Override public void entregar(PedidoContext p)  {}

    @Override
    public void cancelar(PedidoContext pedido) {
        pedido.setEstado(new PedidoEstadoCancelado());
    }

    @Override public void devolver(PedidoContext p) {}
    @Override public String getNome() { return "PENDENTE"; }
}