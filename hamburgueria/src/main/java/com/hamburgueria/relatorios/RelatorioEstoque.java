package com.hamburgueria.relatorios;

public class RelatorioEstoque implements TipoRelatorio {
    @Override
    public String gerar() {
        return "[RELATÓRIO DE ESTOQUE] Pão: 120un | Carne: 8kg | Queijo: 3kg | Alerta: Bacon abaixo do mínimo!";
    }

    @Override
    public String getTipo() { return "Estoque"; }
}