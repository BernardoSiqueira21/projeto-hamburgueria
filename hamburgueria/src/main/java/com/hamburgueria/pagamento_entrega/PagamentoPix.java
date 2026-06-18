package com.hamburgueria.pagamento_entrega;

import java.util.UUID;

public class PagamentoPix implements iPagamento {

    private String ultimoComprovante = "";

    @Override
    public boolean processar(String cliente, double valor) {
        ultimoComprovante = "PIX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return true;
    }

    @Override public String getFormaPagamento() { return "Pix"; }
    @Override public String getComprovante()    { return ultimoComprovante; }
}