package com.hamburgueria.auditoria_cardapio;

import java.time.LocalDate;

public class VisitanteAuditoria implements Visitante {

    private int alertas = 0;

    @Override
    public void visitar(ItemCardapio item) {
        if (item.getPreco() <= 0) {
            alertas++;
        } else if (item.getCaloriasKcal() > 1000) {
            alertas++;
        } else if (!item.isDisponivel()) {
        } else {
        }
    }

    @Override
    public void visitar(ComboCardapio combo) {
        if (combo.getDesconto() > 50) {
            alertas++;
        } else if (combo.getItens().isEmpty()) {
            alertas++;
        } else {
        }
    }

    @Override
    public void visitar(Promocao promocao) {
        if (promocao.getValidade().isBefore(LocalDate.now())) {
            alertas++;
        } else if (!promocao.isAtiva()) {
        } else {
        }
    }

    public void exibirResumo() {
        if (alertas == 0) {
        } else {
        }
    }
}