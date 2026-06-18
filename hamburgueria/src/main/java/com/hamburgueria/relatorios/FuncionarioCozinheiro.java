package com.hamburgueria.relatorios;

public class FuncionarioCozinheiro extends Funcionario {

    public FuncionarioCozinheiro(TipoRelatorio relatorio) { super(relatorio); }

    @Override
    public void emitirRelatorio() {
        System.out.println("[COZINHEIRO] Emitindo relatório de " + relatorio.getTipo() + ":");
        System.out.println("  " + relatorio.gerar());
    }

    @Override
    public String getCargo() { return "Cozinheiro"; }
}