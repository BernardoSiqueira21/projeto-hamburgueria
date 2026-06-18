package com.hamburgueria.ingredientes;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private final String           numeroPedido;
    private final String           nomeCliente;
    private final List<ItemPedido> itensPedido = new ArrayList<>();

    public Pedido(String numeroPedido, String nomeCliente) {
        this.numeroPedido = numeroPedido;
        this.nomeCliente  = nomeCliente;
    }

    public void adicionarIngrediente(Ingrediente ingrediente, int quantidade) {
        itensPedido.add(new ItemPedido(ingrediente, quantidade));
    }

    public void exibir() {}

    public String getNumeroPedido() { return numeroPedido; }
    public String getNomeCliente()  { return nomeCliente; }

    public double getCustoTotal() {
        return itensPedido.stream()
                .mapToDouble(i -> i.getIngrediente().getCustoUnitario() * i.getQuantidade())
                .sum();
    }

    public double getCaloriasTotal() {
        return itensPedido.stream()
                .mapToDouble(i -> i.getIngrediente().getCaloriasUnitarias() * i.getQuantidade())
                .sum();
    }

    private static class ItemPedido {
        private final Ingrediente ingrediente;
        private final int         quantidade;

        ItemPedido(Ingrediente i, int q) {
            this.ingrediente = i;
            this.quantidade  = q;
        }

        Ingrediente getIngrediente() { return ingrediente; }
        int         getQuantidade()  { return quantidade; }
    }
}