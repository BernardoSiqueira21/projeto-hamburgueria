package com.hamburgueria.relatorios;

public abstract class Funcionario {

    protected TipoRelatorio relatorio;

    public Funcionario(TipoRelatorio relatorio) {
        this.relatorio = relatorio;
    }

    public abstract void emitirRelatorio();
    public abstract String getCargo();

    public void setRelatorio(TipoRelatorio relatorio) {
        this.relatorio = relatorio;
    }
}
