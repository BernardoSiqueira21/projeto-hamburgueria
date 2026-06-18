package com.hamburgueria.carrinho_compras;

import java.util.List;

public interface CarrinhoEstado {
    String getStatus();
    List<String> getItens();
    double getTotal();
    String getObservacoes();
    String getMomento();
}