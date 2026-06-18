package com.hamburgueria.ingredientes;

import java.util.ArrayList;
import java.util.List;

public class Hamburgueria {

    private final String       nome;
    private final List<Pedido> pedidos = new ArrayList<>();

    public Hamburgueria(String nome) { this.nome = nome; }

    public void adicionarPedido(Pedido pedido) { pedidos.add(pedido); }

    public void exibirTodosPedidos() {}

    public int    totalPedidos()  { return pedidos.size(); }
    public String getNome()       { return nome; }

    public List<Pedido> getPedidos() { return new ArrayList<>(pedidos); }
}
//flyweight