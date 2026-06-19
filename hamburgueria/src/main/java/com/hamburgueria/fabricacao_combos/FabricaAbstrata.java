package com.hamburgueria.fabricacao_combos;

import com.hamburgueria.templates_pedido.PedidoTemplate;

public interface FabricaAbstrata {
    Lanche criarLanche();
    Acompanhamento criarAcompanhamento();
    PedidoTemplate criarTemplateCombo();
}