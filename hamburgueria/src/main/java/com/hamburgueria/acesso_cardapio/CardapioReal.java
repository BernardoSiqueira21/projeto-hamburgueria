package com.hamburgueria.acesso_cardapio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardapioReal implements ICardapio {

    private final Map<String, Double> itens;
    private final BancoDados          bancoDados;

    public CardapioReal() {
        this.bancoDados = new BancoDados();
        this.itens      = bancoDados.carregarItens();
    }

    @Override
    public List<String> listarItens() {
        List<String> lista = new ArrayList<>();
        itens.forEach((nome, preco) ->
                lista.add(String.format("%-25s R$%.2f", nome, preco)));
        lista.sort(String::compareTo);
        return lista;
    }

    @Override
    public String buscarItem(String nome) {
        Double preco = itens.get(nome);
        if (preco != null) {
            return String.format("%s → R$%.2f", nome, preco);
        }
        return "Item não encontrado: " + nome;
    }

    @Override
    public void adicionarItem(String nome, double preco) {
        itens.put(nome, preco);
        bancoDados.salvarItem(nome, preco);
    }

    @Override
    public void removerItem(String nome) {
        if (itens.remove(nome) != null) {
            bancoDados.deletarItem(nome);
        } else {
        }
    }

    @Override
    public void atualizarPreco(String nome, double novoPreco) {
        if (itens.containsKey(nome)) {
            itens.put(nome, novoPreco);
            bancoDados.atualizarItem(nome, novoPreco);
        } else {
        }
    }

    @Override
    public double consultarPreco(String nome) {
        return itens.getOrDefault(nome, -1.0);
    }
}