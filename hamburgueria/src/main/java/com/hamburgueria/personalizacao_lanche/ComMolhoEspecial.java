package com.hamburgueria.personalizacao_lanche;

public class ComMolhoEspecial extends IngredienteDecoradorBase {

    private static final double PRECO    = 2.00;
    private static final double CALORIAS = 60.0;

    public ComMolhoEspecial(IngredienteDecorator ingrediente) { super(ingrediente); }

    @Override
    public String getDescricao() { return ingrediente.getDescricao() + ", Molho Especial da Casa"; }

    @Override
    public double getPreco() { return ingrediente.getPreco() + PRECO; }

    @Override
    public double getCalorias() { return ingrediente.getCalorias() + CALORIAS; }
}
