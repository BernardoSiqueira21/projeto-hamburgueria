package com.hamburgueria.preparo_cozinha;

import com.hamburgueria.servicos.IServico;
import com.hamburgueria.servicos.ServicoFactory;

public abstract class PreparoLanche {

    public final void preparar() {
        selecionarIngredientes();
        grelharCarne();
        montarLanche();
        if (adicionarMolho()) aplicarMolho();
        embalar();
    }

    public IServico obterServicoEntrega() {
        return ServicoFactory.obterServico("ENTREGAR_PEDIDO");
    }

    protected abstract void selecionarIngredientes();
    protected abstract void grelharCarne();
    protected abstract void montarLanche();
    protected abstract String getNome();

    protected boolean adicionarMolho() { return true; }
    protected void    aplicarMolho()   {}
    private   void    embalar()        {}
}