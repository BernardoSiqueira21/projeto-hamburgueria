package com.hamburgueria.carrinho_compras;

import java.util.ArrayDeque;
import java.util.Deque;

public class HistoricoCarrinho {

    private final Deque<CarrinhoSnapshot> pilha = new ArrayDeque<>();

    public void push(CarrinhoSnapshot snap) { pilha.push(snap); }

    public CarrinhoSnapshot pop() {
        if (pilha.isEmpty()) return null;
        return pilha.pop();
    }

    public boolean isEmpty() { return pilha.isEmpty(); }
    public int     tamanho() { return pilha.size(); }

    public void exibirHistorico() {}
}