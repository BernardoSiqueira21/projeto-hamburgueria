package com.hamburgueria.relatorios;

public class RelatorioVendas implements TipoRelatorio {
    @Override
    public String gerar() {
        return "[RELATÓRIO DE VENDAS] Total do dia: R$ 3.450,00 | 87 pedidos realizados";
    }

    @Override
    public String getTipo() { return "Vendas"; }
}