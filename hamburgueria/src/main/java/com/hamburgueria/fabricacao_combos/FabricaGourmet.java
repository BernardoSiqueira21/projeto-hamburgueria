package com.hamburgueria.fabricacao_combos;

import com.hamburgueria.templates_pedido.PedidoTemplate;

public class FabricaGourmet implements FabricaAbstrata {

    @Override
    public Lanche criarLanche() { return new LancheGourmet(); }

    @Override
    public Acompanhamento criarAcompanhamento() { return new AcompanhamentoGourmet(); }

    @Override
    public PedidoTemplate criarTemplateCombo() {
        PedidoTemplate template = new PedidoTemplate(
                "Cliente", "Cartao", "Combo Gourmet", false, null);
        template.adicionarItem(new LancheGourmet().descrever());
        template.adicionarItem(new AcompanhamentoGourmet().descrever());
        return template;
    }
}