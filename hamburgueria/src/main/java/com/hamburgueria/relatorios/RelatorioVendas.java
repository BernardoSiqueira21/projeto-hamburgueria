package com.hamburgueria.relatorios;

public class RelatorioVendas implements TipoRelatorio {

    @Override
    public String gerar() { return getTipo(); }

    @Override
    public String getTipo() { return "Relatorio de Vendas"; }
}