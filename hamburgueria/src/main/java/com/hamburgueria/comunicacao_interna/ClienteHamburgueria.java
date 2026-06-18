package com.hamburgueria.comunicacao_interna;

public class ClienteHamburgueria extends Pessoa {

    public ClienteHamburgueria(String nome, Central central) { super(nome, central); }

    @Override
    public void enviarMensagem(String destinatario, String mensagem) {
        central.enviarParaSetor(nome, destinatario, mensagem);
    }

    @Override public void receberMensagem(String remetente, String mensagem) {}
}