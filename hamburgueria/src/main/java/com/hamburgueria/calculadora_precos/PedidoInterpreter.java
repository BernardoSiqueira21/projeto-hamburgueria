package com.hamburgueria.calculadora_precos;

import java.util.ArrayList;
import java.util.List;

public class PedidoInterpreter {

    private final String                  numero;
    private final String                  cliente;
    private final List<ExpressaoCardapio> expressoes = new ArrayList<>();
    private final CalculadoraCardapio     calc       = new CalculadoraCardapio();

    public PedidoInterpreter(String numero, String cliente) {
        this.numero  = numero;
        this.cliente = cliente;
    }

    public void adicionarExpressao(ExpressaoCardapio expressao) {
        expressoes.add(expressao);
    }

    public double calcularTotal() {
        double total = 0;
        for (ExpressaoCardapio exp : expressoes) {
            total += exp.interpretar();
        }
        return total;
    }

    public String getNumero() { return numero; }
    public String getCliente(){ return cliente; }
}