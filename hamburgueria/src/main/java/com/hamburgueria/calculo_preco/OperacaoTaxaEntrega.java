package com.hamburgueria.calculo_preco;

public class OperacaoTaxaEntrega implements Operacao {

    private final double taxaFixa;

    public OperacaoTaxaEntrega(double taxaFixa) {
        this.taxaFixa = taxaFixa;
    }

    @Override
    public double executar(double valor) {
        return valor + taxaFixa;
    }

    @Override
    public String getDescricao() {
        return "Taxa de entrega fixa de R$ " + String.format("%.2f", taxaFixa);
    }
}