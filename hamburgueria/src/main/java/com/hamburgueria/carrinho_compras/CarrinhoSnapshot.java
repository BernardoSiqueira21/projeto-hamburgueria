package com.hamburgueria.carrinho_compras;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoSnapshot implements CarrinhoEstado {

    private final String        status;
    private final List<String>  itens;
    private final double        total;
    private final String        observacoes;
    private final LocalDateTime momento;

    CarrinhoSnapshot(String status, List<String> itens,
                     double total, String observacoes) {
        this.status      = status;
        this.itens       = new ArrayList<>(itens);
        this.total       = total;
        this.observacoes = observacoes;
        this.momento     = LocalDateTime.now();
    }

    @Override public String       getStatus()      { return status; }
    @Override public List<String> getItens()       { return new ArrayList<>(itens); }
    @Override public double       getTotal()       { return total; }
    @Override public String       getObservacoes() { return observacoes; }

    @Override
    public String getMomento() {
        return momento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }
}