package com.hamburgueria.calculadora_precos;

import java.util.ArrayList;
import java.util.List;

public class HamburgueriaInterpreter {

    private final String                    nome;
    private final List<PedidoInterpreter>   pedidos = new ArrayList<>();
    private final CalculadoraCardapio       calc    = new CalculadoraCardapio();

    public HamburgueriaInterpreter(String nome) {
        this.nome = nome;
    }

    public void adicionarPedido(PedidoInterpreter pedido) {
        pedidos.add(pedido);
    }

    public double calcularFaturamentoDoDia() {
        double total = 0;
        for (PedidoInterpreter p : pedidos) {
            total += p.calcularTotal();
        }
        return total;
    }

    public double calcularTicketMedio() {
        if (pedidos.isEmpty()) return 0;
        double fat = 0;
        for (PedidoInterpreter p : pedidos) fat += p.calcularTotal();
        ExpressaoCardapio expr = new Divisao(
                new ValorNumerico(fat, "faturamento"),
                new ValorNumerico(pedidos.size(), pedidos.size() + " pedidos")
        );
        return calc.calcular(expr);
    }
}