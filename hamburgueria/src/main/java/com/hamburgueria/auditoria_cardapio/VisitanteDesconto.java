package com.hamburgueria.auditoria_cardapio;

public class VisitanteDesconto implements Visitante {

    private final double percentualExtra;

    public VisitanteDesconto(double percentualExtra) {
        this.percentualExtra = percentualExtra;
    }

    @Override
    public void visitar(ItemCardapio item) {
        double precoComDesconto = item.getPreco() * (1 - percentualExtra / 100);
    }

    @Override
    public void visitar(ComboCardapio combo) {
        double novoPreco = combo.getPrecoFinal() * (1 - percentualExtra / 100);
    }

    @Override
    public void visitar(Promocao promocao) {
        double novoPercentual = promocao.getPercentualDesconto() + percentualExtra;
    }
}