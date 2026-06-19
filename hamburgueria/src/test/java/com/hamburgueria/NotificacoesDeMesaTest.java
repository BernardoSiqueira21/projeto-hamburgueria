package com.hamburgueria;

import com.hamburgueria.ciclo_pedido.*;
import com.hamburgueria.notificacoes.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Padrão: Observer — notifica clientes quando o status da mesa muda
class NotificacoesDeMesaTest {

    @Test
    void mesaIniciaComStatusLivre() {
        Mesa mesa = new Mesa(1);
        assertEquals("LIVRE", mesa.getStatusPedido());
    }

    @Test
    void mesaAtualizaStatusAoDefinirPedido() {
        Mesa mesa = new Mesa(1);
        PedidoContext pedido = new PedidoContext("P-OBS-01");
        mesa.setPedidoAtual(pedido);
        assertEquals("PENDENTE", mesa.getStatusPedido());
    }

    @Test
    void mesaAvancaEstadoParaConfirmado() {
        Mesa mesa = new Mesa(1);
        mesa.adicionarObserver(new Cliente("João"));
        PedidoContext pedido = new PedidoContext("P-OBS-01");
        mesa.setPedidoAtual(pedido);
        mesa.avancarEstadoPedido();
        assertEquals("CONFIRMADO", mesa.getStatusPedido());
    }

    @Test
    void estadoPedidoSincronizadoComMesa() {
        Mesa mesa = new Mesa(1);
        PedidoContext pedido = new PedidoContext("P-OBS-01");
        mesa.setPedidoAtual(pedido);
        mesa.avancarEstadoPedido();
        assertEquals("CONFIRMADO", mesa.getPedidoAtual().getEstado().getNome());
    }

    @Test
    void mesaAvancaEstadoParaEmPreparo() {
        Mesa mesa = new Mesa(1);
        PedidoContext pedido = new PedidoContext("P-OBS-01");
        mesa.setPedidoAtual(pedido);
        mesa.avancarEstadoPedido();
        mesa.avancarEstadoPedido();
        assertEquals("EM_PREPARO", mesa.getStatusPedido());
    }

    @Test
    void removerObserverParaDeNotificacoes() {
        Mesa mesa = new Mesa(1);
        Cliente joao = new Cliente("João");
        mesa.adicionarObserver(joao);
        mesa.removerObserver(joao);
        mesa.setStatusPedido("ENTREGUE");
        assertEquals("ENTREGUE", mesa.getStatusPedido());
    }

    @Test
    void mesaAceitaMultiplosObservers() {
        Mesa mesa = new Mesa(1);
        mesa.adicionarObserver(new Cliente("João"));
        mesa.adicionarObserver(new Cliente("Maria"));
        mesa.adicionarObserver(new Cliente("Pedro"));
        mesa.setStatusPedido("PRONTO");
        assertEquals("PRONTO", mesa.getStatusPedido());
    }
}