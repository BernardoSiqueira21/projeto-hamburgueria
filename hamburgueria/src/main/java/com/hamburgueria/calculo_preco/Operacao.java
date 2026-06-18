package com.hamburgueria.calculo_preco;

public interface Operacao {
    double executar(double valor);
    String getDescricao();
}