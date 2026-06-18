package com.hamburgueria.navegacao_cardapio;

import com.hamburgueria.cardapio.Conteudo;
import com.hamburgueria.cardapio.Combo;
import java.util.ArrayList;
import java.util.List;


public class CardapioHamburgueria implements Iteravel {

    private final List<ItemPedido> itens = new ArrayList<>();

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
    }


    public void carregarDoComposite(Conteudo conteudo) {
        if (conteudo instanceof Combo combo) {
            for (Conteudo filho : combo.getItens()) {
                carregarDoComposite(filho);
            }
        } else {
            // Folha (Item do composite) → converte para ItemPedido
            itens.add(new ItemPedido(
                    conteudo.getNome(),
                    "Cardapio",
                    conteudo.getPreco(),
                    0,
                    true
            ));
        }
    }

    @Override
    public Iterador criarIterador() {
        return new IteradorCardapio(new ArrayList<>(itens));
    }

    public Iterador criarIteradorPorCategoria(String categoria) {
        return new IteradorPorCategoria(new ArrayList<>(itens), categoria);
    }

    public Iterador criarIteradorDisponiveis() {
        return new IteradorDisponiveis(new ArrayList<>(itens));
    }

    public Iterador criarIteradorPorFaixaPreco(double min, double max) {
        return new IteradorPorFaixaPreco(new ArrayList<>(itens), min, max);
    }
}
