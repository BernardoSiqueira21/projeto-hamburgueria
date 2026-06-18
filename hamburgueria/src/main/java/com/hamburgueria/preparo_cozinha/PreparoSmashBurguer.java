package com.hamburgueria.preparo_cozinha;

public class PreparoSmashBurguer extends PreparoLanche {

    @Override protected void selecionarIngredientes() {}
    @Override protected void grelharCarne()           {}
    @Override protected void montarLanche()           {}
    @Override protected void aplicarMolho()           {}
    @Override protected String getNome()               { return "Smash Burguer"; }
}