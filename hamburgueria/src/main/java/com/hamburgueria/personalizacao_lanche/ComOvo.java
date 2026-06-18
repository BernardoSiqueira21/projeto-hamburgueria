package com.hamburgueria.personalizacao_lanche;

public class ComOvo extends IngredienteDecoradorBase {

    private static final double PRECO    = 2.50;
    private static final double CALORIAS = 70.0;

    public ComOvo(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Ovo Estrelado"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
