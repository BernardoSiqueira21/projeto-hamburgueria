package com.hamburgueria.fila_cozinha;

public class FecharPedidoComando implements Comando {

    private final PedidoCozinha pedido;

    public FecharPedidoComando(PedidoCozinha pedido) { this.pedido = pedido; }

    @Override public void executar()       { pedido.fechar(); }
    @Override public void desfazer()       { pedido.abrir(); }
    @Override public String getDescricao() { return "Fechar pedido #" + pedido.getNumero(); }
}