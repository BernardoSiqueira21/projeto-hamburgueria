package com.hamburgueria.personalizacao_lanche;

public abstract class IngredienteDecoradorBase implements IngredienteDecorator {

    protected final IngredienteDecorator ingrediente;

    public IngredienteDecoradorBase(IngredienteDecorator ingrediente) {
        this.ingrediente = ingrediente;
    }

    @Override
    public String getDescricao() { return ingrediente.getDescricao(); }

    @Override
    public double getPreco() { return ingrediente.getPreco(); }

    @Override
    public double getCalorias() { return ingrediente.getCalorias(); }
}
