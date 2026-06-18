package com.hamburgueria.relatorios;

public class FuncionarioCaixa extends Funcionario {

    public FuncionarioCaixa(TipoRelatorio relatorio) { super(relatorio); }

    @Override
    public void emitirRelatorio() {
        System.out.println("[CAIXA] Emitindo relatório de " + relatorio.getTipo() + ":");
        System.out.println("  " + relatorio.gerar());
    }

    @Override
    public String getCargo() { return "Caixa"; }
}