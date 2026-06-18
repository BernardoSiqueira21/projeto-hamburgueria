package com.hamburgueria.cardapio;

public class Cardapio {

    private final String   nome;
    private final Conteudo raiz;

    public Cardapio(String nome, Conteudo raiz) {
        this.nome = nome;
        this.raiz = raiz;
    }

    public void exibir() {}

    public double getTotal() { return raiz.calcularTotal(); }

    public String getNome()   { return nome; }
    public Conteudo getRaiz() { return raiz; }
}
