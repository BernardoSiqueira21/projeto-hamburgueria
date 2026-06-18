package com.hamburgueria.auditoria_cardapio;

public class ItemCardapio implements Elemento {

    private final String nome;
    private final String categoria;
    private final double preco;
    private final double caloriasKcal;
    private final boolean disponivel;

    public ItemCardapio(String nome, String categoria,
                        double preco, double caloriasKcal, boolean disponivel) {
        this.nome         = nome;
        this.categoria    = categoria;
        this.preco        = preco;
        this.caloriasKcal = caloriasKcal;
        this.disponivel   = disponivel;
    }

    @Override
    public void aceitar(Visitante visitante) {
        visitante.visitar(this);
    }

    public String  getNome()         { return nome; }
    public String  getCategoria()    { return categoria; }
    public double  getPreco()        { return preco; }
    public double  getCaloriasKcal() { return caloriasKcal; }
    public boolean isDisponivel()    { return disponivel; }
}