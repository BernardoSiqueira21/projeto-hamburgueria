package com.hamburgueria.personalizacao_lanche;

public class ComCatupiry extends IngredienteDecoradorBase {

    private static final double PRECO    = 3.50;
    private static final double CALORIAS = 110.0;

    public ComCatupiry(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Catupiry"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
