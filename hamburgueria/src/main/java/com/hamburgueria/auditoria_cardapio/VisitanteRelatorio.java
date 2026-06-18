package com.hamburgueria.auditoria_cardapio;

public class VisitanteRelatorio implements Visitante {

    private double totalItens   = 0;
    private double totalCombos  = 0;
    private int    qtdItens     = 0;
    private int    qtdCombos    = 0;
    private int    qtdPromocoes = 0;

    @Override
    public void visitar(ItemCardapio item) {
        if (item.isDisponivel()) {
            totalItens += item.getPreco();
            qtdItens++;
        }
    }

    @Override
    public void visitar(ComboCardapio combo) {
        totalCombos += combo.getPrecoFinal();
        qtdCombos++;
    }

    @Override
    public void visitar(Promocao promocao) {
        qtdPromocoes++;
    }

    public double getTotalItens()   { return totalItens; }
    public double getTotalCombos()  { return totalCombos; }
    public int    getQtdItens()     { return qtdItens; }
    public int    getQtdCombos()    { return qtdCombos; }
    public int    getQtdPromocoes() { return qtdPromocoes; }

    public void exibirResumo() {}
}