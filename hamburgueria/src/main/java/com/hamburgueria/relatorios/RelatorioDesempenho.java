package com.hamburgueria.relatorios;

public class RelatorioDesempenho implements TipoRelatorio {
    @Override
    public String gerar() {
        return "[RELATÓRIO DE DESEMPENHO] Tempo médio de preparo: 18min | Avaliação média: 4.7/5.0";
    }

    @Override
    public String getTipo() { return "Desempenho"; }
}