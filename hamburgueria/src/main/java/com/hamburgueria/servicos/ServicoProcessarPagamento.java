package com.hamburgueria.servicos;

public class ServicoProcessarPagamento implements IServico {
    @Override
    public void executar() {
        System.out.println("[ServicoProcessarPagamento] Processando pagamento do cliente...");
    }

    @Override
    public String getNome() { return "ProcessarPagamento"; }
}