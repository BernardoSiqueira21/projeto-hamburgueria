package com.hamburgueria.acesso_cardapio;

import java.util.HashMap;
import java.util.Map;

public class BancoDados {

    private final Map<String, Double> itens = new HashMap<>();

    public BancoDados() {
        itens.put("Smash Burguer",    45.90);
        itens.put("X-Burguer",        28.90);
        itens.put("Chicken Crispy",   32.90);
        itens.put("Green Burguer",    35.90);
        itens.put("Batata Frita P",    9.90);
        itens.put("Batata Frita G",   14.90);
        itens.put("Onion Rings",      16.90);
        itens.put("Refri 350ml",       6.90);
        itens.put("Suco Natural",      8.90);
        itens.put("Sorvete 2 bolas",  10.90);
    }

    public Map<String, Double> carregarItens() {
        return new HashMap<>(itens);
    }

    public void salvarItem(String nome, double preco) {
        itens.put(nome, preco);
    }

    public void deletarItem(String nome) {
        itens.remove(nome);
    }

    public void atualizarItem(String nome, double preco) {
        if (itens.containsKey(nome)) {
            itens.put(nome, preco);
        }
    }
}
//proxy