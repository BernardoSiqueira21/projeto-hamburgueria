package com.hamburgueria.cardapio;

public class Item extends Conteudo {

    private final String descricao;

    public Item(String nome, String descricao, double preco) {
        super(nome, preco);
        this.descricao = descricao;
    }

    @Override
    public void exibir(String indentacao) {}

    @Override
    public double calcularTotal() { return preco; }
}