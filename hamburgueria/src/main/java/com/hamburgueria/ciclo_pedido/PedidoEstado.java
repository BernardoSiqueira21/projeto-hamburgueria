package com.hamburgueria.ciclo_pedido;

public abstract class PedidoEstado {
    public abstract void confirmar(PedidoContext pedido);
    public abstract void preparar(PedidoContext pedido);
    public abstract void entregar(PedidoContext pedido);
    public abstract void cancelar(PedidoContext pedido);
    public abstract void devolver(PedidoContext pedido);
    public abstract String getNome();
}