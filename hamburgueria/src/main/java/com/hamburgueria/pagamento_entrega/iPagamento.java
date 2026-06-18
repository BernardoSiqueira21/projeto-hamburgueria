package com.hamburgueria.pagamento_entrega;

public interface iPagamento {
    boolean processar(String cliente, double valor);
    String getFormaPagamento();
    String getComprovante();
}