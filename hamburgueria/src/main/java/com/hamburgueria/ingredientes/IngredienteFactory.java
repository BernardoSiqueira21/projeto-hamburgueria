package com.hamburgueria.ingredientes;

import java.util.HashMap;
import java.util.Map;

public class IngredienteFactory {

    private static final Map<String, Ingrediente> cache = new HashMap<>();

    public static Ingrediente obter(String nome, String tipo, double custo,
                                    double calorias, String descricao) {
        if (!cache.containsKey(nome)) {
            cache.put(nome, new Ingrediente(nome, tipo, custo, calorias, descricao));
        }
        return cache.get(nome);
    }

    public static Ingrediente obter(String nome) {
        Ingrediente ing = cache.get(nome);
        if (ing == null) {
            throw new IllegalArgumentException("Ingrediente não cadastrado: " + nome);
        }
        return ing;
    }

    public static int  totalInstancias() { return cache.size(); }
    public static void listarCache()     {}
    public static void limparCache()     { cache.clear(); }
}