package com.hamburgueria.comunicacao_interna;

public interface Setor {
    void receberMensagem(String remetente, String mensagem);
    String getNome();
}