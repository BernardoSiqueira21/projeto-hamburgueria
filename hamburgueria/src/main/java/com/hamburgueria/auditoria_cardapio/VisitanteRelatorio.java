package com.hamburgueria.auditoria_cardapio;

import com.hamburgueria.navegacao_cardapio.CardapioHamburgueria;
import com.hamburgueria.navegacao_cardapio.Iterador;
import com.hamburgueria.navegacao_cardapio.ItemPedido;

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

    public void gerarRelatorioCompleto(CardapioHamburgueria cardapio) {
        Iterador iterador = cardapio.criarIteradorDisponiveis();
        while (iterador.temProximo()) {
            ItemPedido item = iterador.proximo();
            totalItens += item.getPreco();
            qtdItens++;
        }
    }

    public double getTotalItens()   { return totalItens; }
    public double getTotalCombos()  { return totalCombos; }
    public int    getQtdItens()     { return qtdItens; }
    public int    getQtdCombos()    { return qtdCombos; }
    public int    getQtdPromocoes() { return qtdPromocoes; }

    public void exibirResumo() {}
}