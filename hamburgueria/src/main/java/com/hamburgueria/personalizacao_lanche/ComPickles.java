package com.hamburgueria.personalizacao_lanche;

public class ComPickles extends IngredienteDecoradorBase {

    private static final double PRECO    = 1.50;
    private static final double CALORIAS = 20.0;

    public ComPickles(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Pickles"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
