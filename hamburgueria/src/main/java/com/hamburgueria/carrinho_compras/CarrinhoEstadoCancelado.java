package com.hamburgueria.carrinho_compras;

import java.util.List;

public class CarrinhoEstadoCancelado implements CarrinhoEstado {

    private final CarrinhoSnapshot snap;

    public CarrinhoEstadoCancelado(CarrinhoSnapshot snap) {
        this.snap = snap;
    }

    @Override public String       getStatus()      { return "CANCELADO"; }
    @Override public List<String> getItens()       { return snap.getItens(); }
    @Override public double       getTotal()        { return snap.getTotal(); }
    @Override public String       getObservacoes() { return snap.getObservacoes(); }
    @Override public String       getMomento()     { return snap.getMomento(); }
}