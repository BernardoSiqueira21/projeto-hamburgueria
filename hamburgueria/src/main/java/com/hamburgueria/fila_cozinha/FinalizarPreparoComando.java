package com.hamburgueria.fila_cozinha;

public class FinalizarPreparoComando implements Comando {

    private final PedidoCozinha pedido;

    public FinalizarPreparoComando(PedidoCozinha pedido) { this.pedido = pedido; }

    @Override public void executar()       { pedido.finalizarPreparo(); }
    @Override public void desfazer()       { pedido.iniciarPreparo(); }
    @Override public String getDescricao() { return "Finalizar preparo do pedido #" + pedido.getNumero(); }
}