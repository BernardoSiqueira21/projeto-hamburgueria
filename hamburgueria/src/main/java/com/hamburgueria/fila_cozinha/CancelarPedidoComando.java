package com.hamburgueria.fila_cozinha;

public class CancelarPedidoComando implements Comando {

    private final PedidoCozinha pedido;

    public CancelarPedidoComando(PedidoCozinha pedido) { this.pedido = pedido; }

    @Override public void executar()       { pedido.cancelar(); }
    @Override public void desfazer()       { pedido.abrir(); }
    @Override public String getDescricao() { return "Cancelar pedido #" + pedido.getNumero(); }
}