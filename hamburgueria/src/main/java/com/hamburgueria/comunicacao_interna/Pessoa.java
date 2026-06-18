package com.hamburgueria.comunicacao_interna;

public abstract class Pessoa {

    protected Central central;
    protected String nome;

    public Pessoa(String nome, Central central) {
        this.nome    = nome;
        this.central = central;
    }

    public abstract void enviarMensagem(String destinatario, String mensagem);
    public abstract void receberMensagem(String remetente, String mensagem);
    public String getNome() { return nome; }
}