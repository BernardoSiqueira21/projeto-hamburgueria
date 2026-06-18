package com.hamburgueria.calculo_preco;

public class OperacaoCupom implements Operacao {

    private final double valorCupom;
    private final String codigoCupom;

    public OperacaoCupom(String codigoCupom, double valorCupom) {
        this.codigoCupom = codigoCupom;
        this.valorCupom = valorCupom;
    }

    @Override
    public double executar(double valor) {
        double resultado = valor - valorCupom;
        return resultado < 0 ? 0 : resultado;
    }

    @Override
    public String getDescricao() {
        return "Cupom '" + codigoCupom + "' de R$ " + String.format("%.2f", valorCupom);
    }
}