package com.hamburgueria.operacoes_balcao;

public class SetorPagamento extends Setor {

    public SetorPagamento() { super("Pagamento"); }

    public boolean processarPagamento(String cliente, double valor, String formaPagamento) {
        return valor > 0;
    }

    public void emitirComprovante(String cliente, double valor) {}

    public void aplicarDesconto(double percentual) {}
}