package com.hamburgueria.auditoria_cardapio;

public interface Visitante {
    void visitar(ItemCardapio item);
    void visitar(ComboCardapio combo);
    void visitar(Promocao promocao);
}