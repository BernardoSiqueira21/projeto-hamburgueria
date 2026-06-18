package com.hamburgueria.navegacao_cardapio;

import java.util.List;
import java.util.stream.Collectors;

public class IteradorPorFaixaPreco implements Iterador {

    private final List<ItemPedido> itensFiltrados;
    private int posicao = 0;

    public IteradorPorFaixaPreco(List<ItemPedido> itens,
                                 double precoMin, double precoMax) {
        this.itensFiltrados = itens.stream()
                .filter(i -> i.getPreco() >= precoMin && i.getPreco() <= precoMax)
                .sorted((a, b) -> Double.compare(a.getPreco(), b.getPreco()))
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
}