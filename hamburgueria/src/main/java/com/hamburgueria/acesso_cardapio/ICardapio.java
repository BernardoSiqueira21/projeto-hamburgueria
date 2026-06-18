package com.hamburgueria.acesso_cardapio;

import java.util.List;

public interface ICardapio {
    List<String> listarItens();
    String buscarItem(String nome);
    void adicionarItem(String nome, double preco);
    void removerItem(String nome);
    void atualizarPreco(String nome, double novoPreco);
    double consultarPreco(String nome);
}