package com.hamburgueria.navegacao_cardapio;

import java.util.List;
import java.util.stream.Collectors;

public class IteradorPorCategoria implements Iterador {

    private final List<ItemPedido> itensFiltrados;
    private int posicao = 0;

    public IteradorPorCategoria(List<ItemPedido> itens, String categoria) {
        this.itensFiltrados = itens.stream()
                .filter(i -> i.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    @Override
    public boolean temProximo() {
        return posicao < itensFiltrados.size();
    }

    @Override
    public ItemPedido proximo() {
        return itensFiltrados.get(posicao++);
    }

    @Override
    public void reiniciar() {
        posicao = 0;
    }

    public int totalFiltrado() {
        return itensFiltrados.size();
    }
}