package com.hamburgueria;

import com.hamburgueria.ciclo_pedido.*;
import com.hamburgueria.notificacoes.*;
import com.hamburgueria.configuracoes.Configuracoes;
import com.hamburgueria.servicos.*;
import com.hamburgueria.fabricacao_combos.*;
import com.hamburgueria.relatorios.*;
import com.hamburgueria.calculo_preco.*;
import com.hamburgueria.montagem_lanche.*;
import com.hamburgueria.aprovacao_desconto.*;
import com.hamburgueria.preparo_cozinha.*;
import com.hamburgueria.comunicacao_interna.*;
import com.hamburgueria.operacoes_balcao.*;
import com.hamburgueria.cardapio.*;
import com.hamburgueria.carrinho_compras.*;
import com.hamburgueria.auditoria_cardapio.*;
import com.hamburgueria.personalizacao_lanche.IngredienteDecorator;
import com.hamburgueria.personalizacao_lanche.LancheBase;
import com.hamburgueria.personalizacao_lanche.ComQueijo;
import com.hamburgueria.personalizacao_lanche.ComBacon;
import com.hamburgueria.personalizacao_lanche.ComAlface;
import com.hamburgueria.personalizacao_lanche.ComTomate;
import com.hamburgueria.personalizacao_lanche.ComPickles;
import com.hamburgueria.personalizacao_lanche.ComOvo;
import com.hamburgueria.personalizacao_lanche.ComCatupiry;
import com.hamburgueria.personalizacao_lanche.ComMolhoEspecial;
import com.hamburgueria.navegacao_cardapio.*;
import com.hamburgueria.templates_pedido.*;
import com.hamburgueria.ingredientes.*;
import com.hamburgueria.fila_cozinha.*;
import com.hamburgueria.pagamento_entrega.*;
import com.hamburgueria.acesso_cardapio.*;
import com.hamburgueria.calculadora_precos.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class HamburgueriaTest {
    
    @Test
    void cicloDeVidaDoPedido() {
        PedidoContext pedido = new PedidoContext("P-001");
        assertEquals("PENDENTE", pedido.getEstado().getNome());

        pedido.confirmar();
        assertEquals("CONFIRMADO", pedido.getEstado().getNome());

        pedido.preparar();
        assertEquals("EM_PREPARO", pedido.getEstado().getNome());

        pedido.entregar();
        assertEquals("ENTREGUE", pedido.getEstado().getNome());

        pedido.devolver();
        assertEquals("DEVOLVIDO", pedido.getEstado().getNome());

        PedidoContext p2 = new PedidoContext("P-002");
        p2.confirmar();
        p2.cancelar();
        assertEquals("CANCELADO", p2.getEstado().getNome());

        p2.confirmar();
        assertEquals("CANCELADO", p2.getEstado().getNome());

        PedidoContext p3 = new PedidoContext("P-003");
        p3.preparar();
        assertEquals("PENDENTE", p3.getEstado().getNome());
    }

    @Test
    void notificacaoDeStatusDaMesa() {
        Mesa mesa = new Mesa(1);
        Cliente joao  = new Cliente("João");
        Cliente maria = new Cliente("Maria");

        mesa.adicionarObserver(joao);
        mesa.adicionarObserver(maria);
        assertEquals("LIVRE", mesa.getStatusPedido());

        PedidoContext pedido = new PedidoContext("P-OBS-01");
        mesa.setPedidoAtual(pedido);
        assertEquals("PENDENTE", mesa.getStatusPedido());

        mesa.avancarEstadoPedido(); // PENDENTE → CONFIRMADO → notifica observers
        assertEquals("CONFIRMADO", mesa.getStatusPedido());
        assertEquals("CONFIRMADO", mesa.getPedidoAtual().getEstado().getNome());

        mesa.avancarEstadoPedido(); // CONFIRMADO → EM_PREPARO → notifica observers
        assertEquals("EM_PREPARO", mesa.getStatusPedido());

        mesa.removerObserver(joao);
        mesa.setStatusPedido("ENTREGUE");
        assertEquals("ENTREGUE", mesa.getStatusPedido());
    }

