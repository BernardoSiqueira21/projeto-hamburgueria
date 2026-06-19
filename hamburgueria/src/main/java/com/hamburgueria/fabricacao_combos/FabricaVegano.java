package com.hamburgueria.fabricacao_combos;

import com.hamburgueria.templates_pedido.PedidoTemplate;

public class FabricaVegano implements FabricaAbstrata {

    @Override
    public Lanche criarLanche() { return new LancheVegano(); }

    @Override
    public Acompanhamento criarAcompanhamento() { return new AcompanhamentoVegano(); }

    @Override
    public PedidoTemplate criarTemplateCombo() {
        PedidoTemplate template = new PedidoTemplate(
                "Cliente", "Pix", "Combo Vegano", false, null);
        template.adicionarItem(new LancheVegano().descrever());
        template.adicionarItem(new AcompanhamentoVegano().descrever());
        return template;
    }
}