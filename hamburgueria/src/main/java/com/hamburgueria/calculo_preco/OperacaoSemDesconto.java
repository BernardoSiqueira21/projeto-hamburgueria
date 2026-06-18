package com.hamburgueria.calculo_preco;

public class OperacaoSemDesconto implements Operacao {

    @Override
    public double executar(double valor) {
        return valor;
    }

    @Override
    public String getDescricao() {
        return "Sem desconto aplicado";
    }
}