package com.hamburgueria.preparo_cozinha;

public abstract class PreparoLanche {

    public final void preparar() {
        selecionarIngredientes();
        grelharCarne();
        montarLanche();
        if (adicionarMolho()) aplicarMolho();
        embalar();
    }

    protected abstract void selecionarIngredientes();
    protected abstract void grelharCarne();
    protected abstract void montarLanche();
    protected abstract String getNome();

    protected boolean adicionarMolho() { return true; }
    protected void aplicarMolho()      {}
    private   void embalar()           {}
}