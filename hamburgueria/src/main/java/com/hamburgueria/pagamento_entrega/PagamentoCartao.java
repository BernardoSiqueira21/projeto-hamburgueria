package com.hamburgueria.pagamento_entrega;

import java.util.UUID;

public class PagamentoCartao implements iPagamento {

    private final String tipoCartao;
    private String ultimoComprovante = "";

    public PagamentoCartao(String tipoCartao) { this.tipoCartao = tipoCartao; }

    @Override
    public boolean processar(String cliente, double valor) {
        ultimoComprovante = "CARTAO-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return true;
    }

    @Override
    public String getFormaPagamento() { return "Cartão " + tipoCartao; }

    @Override
    public String getComprovante() { return ultimoComprovante; }
}