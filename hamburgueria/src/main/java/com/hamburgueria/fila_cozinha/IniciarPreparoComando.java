package com.hamburgueria.fila_cozinha;

public class IniciarPreparoComando implements Comando {

    private final PedidoCozinha pedido;

    public IniciarPreparoComando(PedidoCozinha pedido) { this.pedido = pedido; }

    @Override public void executar()       { pedido.iniciarPreparo(); }
    @Override public void desfazer()       { pedido.abrir(); }
    @Override public String getDescricao() { return "Iniciar preparo do pedido #" + pedido.getNumero(); }
}