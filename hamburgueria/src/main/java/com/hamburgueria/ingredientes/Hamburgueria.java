package com.hamburgueria.ingredientes;

import com.hamburgueria.cardapio.Combo;
import com.hamburgueria.cardapio.Conteudo;
import com.hamburgueria.cardapio.Item;
import java.util.ArrayList;
import java.util.List;

public class Hamburgueria {

    private final String       nome;
    private final List<Pedido> pedidos = new ArrayList<>();

    public Hamburgueria(String nome) { this.nome = nome; }

    public void adicionarPedido(Pedido pedido) { pedidos.add(pedido); }

    public Combo montarCardapioComposite() {
        Combo cardapio = new Combo("Cardapio " + nome);
        IngredienteFactory.obterTodos().forEach(ing -> {
            Item item = new Item(
                    ing.getNome(),
                    ing.getTipo(),
                    ing.getCustoUnitario()
            );
            cardapio.adicionar(item);
        });
        return cardapio;
    }

    public void exibirTodosPedidos() {}

    public int    totalPedidos()  { return pedidos.size(); }
    public String getNome()       { return nome; }
    public List<Pedido> getPedidos() { return new ArrayList<>(pedidos); }
}
//flyweight