package com.hamburgueria.preparo_cozinha;

public class PreparoVegano extends PreparoLanche {

    @Override protected void selecionarIngredientes() {}
    @Override protected void grelharCarne()           {}
    @Override protected void montarLanche()           {}

    @Override
    protected boolean adicionarMolho() { return false; }

    @Override protected String getNome() { return "Green Burguer Vegano"; }
}