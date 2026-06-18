package com.hamburgueria.comunicacao_interna;

public class Caixa implements Setor {

    private final Central central;

    public Caixa(Central central) { this.central = central; }

    @Override public void receberMensagem(String remetente, String mensagem) {}
    @Override public String getNome() { return "Caixa"; }
}
