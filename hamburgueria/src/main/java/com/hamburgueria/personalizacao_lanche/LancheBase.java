package com.hamburgueria.personalizacao_lanche;

public class LancheBase implements IngredienteDecorator {

    private final String nome;
    private final double preco;
    private final double calorias;

    public LancheBase(String nome, double preco, double calorias) {
        this.nome     = nome;
        this.preco    = preco;
        this.calorias = calorias;
    }

    @Override
    public String getDescricao() { return nome; }

    @Override
    public double getPreco() { return preco; }

    @Override
    public double getCalorias() { return calorias; }
}
