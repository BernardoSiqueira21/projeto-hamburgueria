package com.hamburgueria.auditoria_cardapio;

import java.util.List;

public class ComboCardapio implements Elemento {

    private final String            nome;
    private final List<ItemCardapio> itens;
    private final double            desconto;

    public ComboCardapio(String nome, List<ItemCardapio> itens, double desconto) {
        this.nome     = nome;
        this.itens    = itens;
        this.desconto = desconto;
    }

    @Override
    public void aceitar(Visitante visitante) {
        visitante.visitar(this);
    }

    public double getPrecoOriginal() {
        return itens.stream().mapToDouble(ItemCardapio::getPreco).sum();
    }

    public double getPrecoFinal() {
        return getPrecoOriginal() * (1 - desconto / 100);
    }

    public double getTotalCalorias() {
        return itens.stream().mapToDouble(ItemCardapio::getCaloriasKcal).sum();
    }

    public String            getNome()     { return nome; }
    public List<ItemCardapio> getItens()   { return itens; }
    public double            getDesconto() { return desconto; }
}
//visitor