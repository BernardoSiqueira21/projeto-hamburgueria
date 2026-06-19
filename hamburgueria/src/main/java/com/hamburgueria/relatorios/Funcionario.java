package com.hamburgueria.relatorios;

import com.hamburgueria.configuracoes.Configuracoes;

public abstract class Funcionario {

    protected TipoRelatorio relatorio;

    public Funcionario(TipoRelatorio relatorio) { this.relatorio = relatorio; }

    public void emitirRelatorio() {
        String nomeEstab = Configuracoes.getInstance().getNomeEstabelecimento();
        relatorio.gerar();
    }

    public abstract String getCargo();

    public void setRelatorio(TipoRelatorio relatorio) { this.relatorio = relatorio; }
}
