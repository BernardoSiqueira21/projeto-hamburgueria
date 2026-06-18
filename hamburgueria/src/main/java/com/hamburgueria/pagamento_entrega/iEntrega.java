package com.hamburgueria.pagamento_entrega;

public interface iEntrega {
    boolean despachar(String endereco, String cep);
    String getRastreamento();
    double getTaxaEntrega(String cep);
}