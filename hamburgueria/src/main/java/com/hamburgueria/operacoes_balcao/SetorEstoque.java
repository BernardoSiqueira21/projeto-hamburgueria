package com.hamburgueria.operacoes_balcao;

import java.util.HashMap;
import java.util.Map;

public class SetorEstoque extends Setor {

    private final Map<String, Integer> estoque = new HashMap<>();

    public SetorEstoque() {
        super("Estoque");
        estoque.put("Pão Brioche",    50);
        estoque.put("Blend 180g",     30);
        estoque.put("Queijo Cheddar", 40);
        estoque.put("Bacon",          25);
        estoque.put("Alface",         60);
        estoque.put("Tomate",         45);
    }

    public boolean verificarDisponibilidade(String ingrediente) {
        return estoque.getOrDefault(ingrediente, 0) > 0;
    }

    public void reservarIngrediente(String ingrediente, int quantidade) {
        if (estoque.containsKey(ingrediente)) {
            estoque.put(ingrediente, estoque.get(ingrediente) - quantidade);
        }
    }

    public void emitirAlertaEstoqueBaixo() {}
}