package com.hamburgueria.fabricacao_combos;

import com.hamburgueria.templates_pedido.PedidoTemplate;
import com.hamburgueria.templates_pedido.EnderecoEntrega;

public class FabricaClassico implements FabricaAbstrata {

    @Override
    public Lanche criarLanche() { return new LancheClassico(); }

    @Override
    public Acompanhamento criarAcompanhamento() { return new AcompanhamentoClassico(); }

    @Override
    public PedidoTemplate criarTemplateCombo() {
        PedidoTemplate template = new PedidoTemplate(
                "Cliente", "Pix", "Combo Classico", false, null);
        template.adicionarItem(new LancheClassico().descrever());
        template.adicionarItem(new AcompanhamentoClassico().descrever());
        return template;
    }
}