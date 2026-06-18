package com.hamburgueria.personalizacao_lanche;

public class ComTomate extends IngredienteDecoradorBase {

    private static final double PRECO    = 1.00;
    private static final double CALORIAS = 15.0;

    public ComTomate(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Tomate Fresco"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
