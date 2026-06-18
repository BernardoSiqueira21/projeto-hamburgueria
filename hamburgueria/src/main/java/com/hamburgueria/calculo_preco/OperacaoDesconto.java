package com.hamburgueria.calculo_preco;

public class OperacaoDesconto implements Operacao {

    private final double percentual;

    public OperacaoDesconto(double percentual) {
        this.percentual = percentual;
    }

    @Override
    public double executar(double valor) {
        return valor - (valor * percentual / 100);
    }

    @Override
    public String getDescricao() {
        return "Desconto de " + percentual + "%";
    }
}