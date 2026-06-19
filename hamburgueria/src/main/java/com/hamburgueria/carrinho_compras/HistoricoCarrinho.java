package com.hamburgueria.carrinho_compras;

import com.hamburgueria.fila_cozinha.Comando;
import java.util.ArrayDeque;
import java.util.Deque;

public class HistoricoCarrinho {

    private final Deque<CarrinhoSnapshot> pilha         = new ArrayDeque<>();
    private final Deque<Comando>          pilhaComandos = new ArrayDeque<>();

    public void push(CarrinhoSnapshot snap) { pilha.push(snap); }

    public CarrinhoSnapshot pop() {
        if (pilha.isEmpty()) return null;
        return pilha.pop();
    }

    public void pushComComando(CarrinhoSnapshot snap, Comando comando) {
        pilha.push(snap);
        pilhaComandos.push(comando);
    }

    public Comando desfazerUltimoComando() {
        if (pilhaComandos.isEmpty()) return null;
        Comando c = pilhaComandos.pop();
        c.desfazer();
        return c;
    }

    public boolean isEmpty()  { return pilha.isEmpty(); }
    public int     tamanho()  { return pilha.size(); }
    public void exibirHistorico() {}
}