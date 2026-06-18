package com.hamburgueria.fila_cozinha;

public class AdicionarItemComando implements Comando {

    private final PedidoCozinha pedido;
    private final String        item;

    public AdicionarItemComando(PedidoCozinha pedido, String item) {
        this.pedido = pedido;
        this.item   = item;
    }

    @Override public void executar()       { pedido.adicionarItem(item); }
    @Override public void desfazer()       { pedido.removerItem(item); }
    @Override public String getDescricao() { return "Adicionar '" + item + "' ao pedido #" + pedido.getNumero(); }
}