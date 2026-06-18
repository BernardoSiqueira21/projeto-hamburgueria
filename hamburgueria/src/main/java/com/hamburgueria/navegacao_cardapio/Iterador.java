package com.hamburgueria.navegacao_cardapio;

public interface Iterador {
    boolean temProximo();
    ItemPedido proximo();
    void reiniciar();
}