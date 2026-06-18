package com.hamburgueria.navegacao_cardapio;

import java.util.List;
import java.util.stream.Collectors;

public class IteradorDisponiveis implements Iterador {

    private final List<ItemPedido> disponiveis;
    private int posicao = 0;

    public IteradorDisponiveis(List<ItemPedido> itens) {
        this.disponiveis = itens.stream()
                .filter(ItemPedido::isDisponivel)
                .collect(Collectors.toList());
    }

    @Override
    public boolean temProximo() {
        return posicao < disponiveis.size();
    }

    @Override
    public ItemPedido proximo() {
        return disponiveis.get(posicao++);
    }

    @Override
    public void reiniciar() {
        posicao = 0;
    }

    public int totalDisponiveis() {
        return disponiveis.size();
    }
}