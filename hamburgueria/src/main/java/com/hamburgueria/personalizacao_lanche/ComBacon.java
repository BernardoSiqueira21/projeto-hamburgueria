package com.hamburgueria.personalizacao_lanche;

public class ComBacon extends IngredienteDecoradorBase {

    private static final double PRECO    = 5.00;
    private static final double CALORIAS = 130.0;

    public ComBacon(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Bacon Crocante"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
