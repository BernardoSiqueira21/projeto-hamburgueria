package com.hamburgueria.calculo_preco;

public class Calculadora {

    private Operacao operacao;

    public Calculadora(Operacao operacao) { this.operacao = operacao; }

    public void setOperacao(Operacao operacao) { this.operacao = operacao; }

    public double calcular(double valor) {
        return operacao.executar(valor);
    }
}
