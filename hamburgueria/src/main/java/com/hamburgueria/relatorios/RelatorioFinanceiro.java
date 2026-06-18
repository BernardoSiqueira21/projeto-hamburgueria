package com.hamburgueria.relatorios;

public class RelatorioFinanceiro implements TipoRelatorio {
    @Override
    public String gerar() {
        return "[RELATÓRIO FINANCEIRO] Receita: R$ 3.450,00 | Despesas: R$ 1.200,00 | Lucro: R$ 2.250,00";
    }

    @Override
    public String getTipo() { return "Financeiro"; }
}