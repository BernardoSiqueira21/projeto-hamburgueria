package com.hamburgueria.comunicacao_interna;

public class Entrega implements Setor {

    private final Central central;

    public Entrega(Central central) { this.central = central; }

    @Override public void receberMensagem(String remetente, String mensagem) {}
    @Override public String getNome() { return "Entrega"; }
}