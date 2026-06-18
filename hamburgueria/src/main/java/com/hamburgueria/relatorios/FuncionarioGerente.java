package com.hamburgueria.relatorios;

public class FuncionarioGerente extends Funcionario {

    public FuncionarioGerente(TipoRelatorio relatorio) { super(relatorio); }

    @Override
    public void emitirRelatorio() {
        relatorio.gerar();
    }

    @Override
    public String getCargo() { return "Gerente"; }
}