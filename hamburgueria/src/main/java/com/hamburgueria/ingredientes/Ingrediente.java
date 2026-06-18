package com.hamburgueria.ingredientes;

public class Ingrediente {

    private final String nome;
    private final String tipo;
    private final double custoUnitario;
    private final double caloriasUnitarias;
    private final String descricao;

    public Ingrediente(String nome, String tipo, double custoUnitario,
                       double caloriasUnitarias, String descricao) {
        this.nome              = nome;
        this.tipo              = tipo;
        this.custoUnitario     = custoUnitario;
        this.caloriasUnitarias = caloriasUnitarias;
        this.descricao         = descricao;
    }

    public void aplicarNoPedido(String nomePedido, int quantidade) {}

    public String getNome()              { return nome; }
    public String getTipo()              { return tipo; }
    public double getCustoUnitario()     { return custoUnitario; }
    public double getCaloriasUnitarias() { return caloriasUnitarias; }
    public String getDescricao()         { return descricao; }
}