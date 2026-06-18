package com.hamburgueria.personalizacao_lanche;

public class ComQueijo extends IngredienteDecoradorBase {

    private static final double PRECO    = 3.00;
    private static final double CALORIAS = 90.0;

    public ComQueijo(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Queijo Cheddar"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
