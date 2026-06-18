package com.hamburgueria.notificacoes;

public interface Observer {
    void atualizar(String evento, Object dados);
}