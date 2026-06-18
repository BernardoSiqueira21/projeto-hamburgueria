package com.hamburgueria.personalizacao_lanche;

public class ComAlface extends IngredienteDecoradorBase {

    private static final double PRECO    = 1.00;
    private static final double CALORIAS = 10.0;

    public ComAlface(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Alface Americana"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
