package com.hamburgueria.relatorios;

public class FuncionarioAtendente extends Funcionario {

    public FuncionarioAtendente(TipoRelatorio relatorio) { super(relatorio); }

    @Override
    public void emitirRelatorio() {
        System.out.println("[ATENDENTE] Emitindo relatório de " + relatorio.getTipo() + ":");
        System.out.println("  " + relatorio.gerar());
    }

    @Override
    public String getCargo() { return "Atendente"; }
}