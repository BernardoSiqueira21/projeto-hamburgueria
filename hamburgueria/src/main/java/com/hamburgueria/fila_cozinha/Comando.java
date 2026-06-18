package com.hamburgueria.fila_cozinha;

public interface Comando {
    void executar();
    void desfazer();
    String getDescricao();
}