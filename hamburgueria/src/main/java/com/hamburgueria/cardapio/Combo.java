package com.hamburgueria.cardapio;

import java.util.ArrayList;
import java.util.List;

public class Combo extends Conteudo {

    private final List<Conteudo> itens = new ArrayList<>();

    public Combo(String nome) { super(nome, 0); }

    @Override
    public void adicionar(Conteudo conteudo) { itens.add(conteudo); }

    @Override
    public void remover(Conteudo conteudo) { itens.remove(conteudo); }

    @Override
    public void exibir(String indentacao) {
        for (Conteudo c : itens) c.exibir(indentacao + "   ");
    }

    @Override
    public double calcularTotal() {
        return itens.stream().mapToDouble(Conteudo::calcularTotal).sum();
    }

    public List<Conteudo> getItens() { return itens; }
}