package com.hamburgueria.calculo_preco;

public class OperacaoAcrescimo implements Operacao {

    private final double percentual;

    public OperacaoAcrescimo(double percentual) {
        this.percentual = percentual;
    }

    @Override
    public double executar(double valor) {
        return valor + (valor * percentual / 100);
    }

    @Override
    public String getDescricao() {
        return "Acréscimo de " + percentual + "%";
    }
}