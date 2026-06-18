package com.hamburgueria.pagamento_entrega;

public class CaixaRegistradora {

    private final String numeroCaixa;

    public CaixaRegistradora(String numeroCaixa) {
        this.numeroCaixa = numeroCaixa;
    }

    public void cobrar(String cliente, double valor, iPagamento pagamento) {
        boolean aprovado = pagamento.processar(cliente, valor);
        if (!aprovado) {
            throw new RuntimeException("Pagamento recusado para: " + cliente);
        }
    }

    public void despacharEntrega(String cliente, String endereco,
                                 String cep, iEntrega entrega) {
        entrega.getTaxaEntrega(cep);
        entrega.despachar(endereco, cep);
    }
}
