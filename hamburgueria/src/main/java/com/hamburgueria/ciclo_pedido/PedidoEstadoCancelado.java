package com.hamburgueria.ciclo_pedido;

public class PedidoEstadoCancelado extends PedidoEstado {

    @Override public void confirmar(PedidoContext p) {}
    @Override public void preparar(PedidoContext p)  {}
    @Override public void entregar(PedidoContext p)  {}
    @Override public void cancelar(PedidoContext p)  {}
    @Override public void devolver(PedidoContext p)  {}
    @Override public String getNome() { return "CANCELADO"; }
}