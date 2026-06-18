package com.hamburgueria.calculo_preco;

public class OperacaoFidelidade implements Operacao {

    private final int pontos;
    private static final double VALOR_POR_PONTO = 0.10;

    public OperacaoFidelidade(int pontos) {
        this.pontos = pontos;
    }

    @Override
    public double executar(double valor) {
        double desconto = pontos * VALOR_POR_PONTO;
        double resultado = valor - desconto;
        return resultado < 0 ? 0 : resultado;
    }

    @Override
    public String getDescricao() {
        return "Resgate de " + pontos + " pontos de fidelidade (R$ "
                + String.format("%.2f", pontos * VALOR_POR_PONTO) + " de desconto)";
    }
}