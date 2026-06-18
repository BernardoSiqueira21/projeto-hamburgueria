package com.hamburgueria.comunicacao_interna;

public class Cozinha implements Setor {

    private final Central central;

    public Cozinha(Central central) { this.central = central; }

    @Override public void receberMensagem(String remetente, String mensagem) {}
    @Override public String getNome() { return "Cozinha"; }
}