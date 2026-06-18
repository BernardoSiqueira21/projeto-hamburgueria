package com.hamburgueria.relatorios;

public class RelatorioPedidos implements TipoRelatorio {
    @Override
    public String gerar() {
        return "[RELATÓRIO DE PEDIDOS] Abertos: 5 | Em preparo: 3 | Entregues: 79 | Cancelados: 2";
    }

    @Override
    public String getTipo() { return "Pedidos"; }
}