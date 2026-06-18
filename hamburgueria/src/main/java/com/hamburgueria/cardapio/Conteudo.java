package com.hamburgueria.cardapio;

public abstract class Conteudo {

    protected String nome;
    protected double preco;

    public Conteudo(String nome, double preco) {
        this.nome  = nome;
        this.preco = preco;
    }

    public String getNome()   { return nome; }
    public double getPreco()  { return preco; }

    public abstract void exibir(String indentacao);
    public abstract double calcularTotal();

    public void adicionar(Conteudo conteudo) {
        throw new UnsupportedOperationException(getClass().getSimpleName()
                + " não suporta filhos.");
    }

    public void remover(Conteudo conteudo) {
        throw new UnsupportedOperationException(getClass().getSimpleName()
                + " não suporta remoção de filhos.");
    }
}