package com.hamburgueria.templates_pedido;

import java.util.HashMap;
import java.util.Map;

public class RegistroPedidos {

    private final Map<String, PedidoTemplate> templates = new HashMap<>();

    public void registrar(String chave, PedidoTemplate template) {
        templates.put(chave, template);
    }

    public PedidoTemplate clonarTemplate(String chave) {
        PedidoTemplate template = templates.get(chave);
        if (template == null) {
            throw new IllegalArgumentException("Template não encontrado: " + chave);
        }
        return template.clonar();
    }

    public boolean contemTemplate(String chave) { return templates.containsKey(chave); }

    public void listarTemplates() {}
}