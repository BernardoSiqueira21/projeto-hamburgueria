package com.hamburgueria.notificacoes;

public class Cliente implements Observer {

    private final String nome;

    public Cliente(String nome) { this.nome = nome; }

    @Override public void atualizar(String evento, Object dados) {}

    public String getNome() { return nome; }
}
