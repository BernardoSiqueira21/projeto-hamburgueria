package com.hamburgueria.navegacao_cardapio;

public class ItemPedido {

    private final String nome;
    private final String categoria;
    private final double preco;
    private final double calorias;
    private final boolean disponivel;

    public ItemPedido(String nome, String categoria,
                      double preco, double calorias, boolean disponivel) {
        this.nome       = nome;
        this.categoria  = categoria;
        this.preco      = preco;
        this.calorias   = calorias;
        this.disponivel = disponivel;
    }

    public String  getNome()       { return nome; }
    public String  getCategoria()  { return categoria; }
    public double  getPreco()      { return preco; }
    public double  getCalorias()   { return calorias; }
    public boolean isDisponivel()  { return disponivel; }

    @Override
    public String toString() {
        return String.format("%-28s | %-15s | R$%6.2f | %5.0f kcal | %s",
                nome, categoria, preco, calorias,
                disponivel ? "✔ Disponível" : "✗ Indisponível");
    }
}