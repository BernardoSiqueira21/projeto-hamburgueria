package com.hamburgueria.navegacao_cardapio;

import java.util.List;

public class IteradorCardapio implements Iterador {

    private final List<ItemPedido> itens;
    private int posicao = 0;

    public IteradorCardapio(List<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public boolean temProximo() {
        return posicao < itens.size();
    }

    @Override
    public ItemPedido proximo() {
        return itens.get(posicao++);
    }

    @Override
    public void reiniciar() {
        posicao = 0;
    }
}