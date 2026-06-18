package com.hamburgueria.fila_cozinha;

public class AbrirPedidoComando implements Comando {

    private final PedidoCozinha pedido;

    public AbrirPedidoComando(PedidoCozinha pedido) { this.pedido = pedido; }

    @Override public void executar()         { pedido.abrir(); }
    @Override public void desfazer()         { pedido.cancelar(); }
    @Override public String getDescricao()   { return "Abrir pedido #" + pedido.getNumero(); }
}
