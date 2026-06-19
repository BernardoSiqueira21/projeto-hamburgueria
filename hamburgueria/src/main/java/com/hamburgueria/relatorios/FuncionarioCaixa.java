package com.hamburgueria.relatorios;

public class FuncionarioCaixa extends Funcionario {

    public FuncionarioCaixa(TipoRelatorio relatorio) { super(relatorio); }

    @Override
    public void emitirRelatorio() { relatorio.gerar(); }

    @Override
    public String getCargo() { return "Caixa"; }
}