package com.hamburgueria.operacoes_balcao;

public abstract class Setor {
    protected String nome;

    public Setor(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
}