package com.hamburgueria.carrinho_compras;

import java.util.List;

public class CarrinhoEstadoAberto implements CarrinhoEstado {

    private final CarrinhoSnapshot snap;

    public CarrinhoEstadoAberto(CarrinhoSnapshot snap) {
        this.snap = snap;
    }

    @Override public String       getStatus()      { return "ABERTO"; }
    @Override public List<String> getItens()       { return snap.getItens(); }
    @Override public double       getTotal()        { return snap.getTotal(); }
    @Override public String       getObservacoes() { return snap.getObservacoes(); }
    @Override public String       getMomento()     { return snap.getMomento(); }
}