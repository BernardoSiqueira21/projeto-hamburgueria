package com.hamburgueria;

import com.hamburgueria.ciclo_pedido.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CicloDeVidaDoPedidoTest {

    @Test
    void pedidoIniciaNoPendente() {
        PedidoContext pedido = new PedidoContext("P-001");
        assertEquals("PENDENTE", pedido.getEstado().getNome());
    }

    @Test
    void pedidoConfirmadoAposConfirmar() {
        PedidoContext pedido = new PedidoContext("P-001");
        pedido.confirmar();
        assertEquals("CONFIRMADO", pedido.getEstado().getNome());
    }

    @Test
    void pedidoEmPreparoAposPreparar() {
        PedidoContext pedido = new PedidoContext("P-001");
        pedido.confirmar();
        pedido.preparar();
        assertEquals("EM_PREPARO", pedido.getEstado().getNome());
    }

    @Test
    void pedidoEntregueAposEntregar() {
        PedidoContext pedido = new PedidoContext("P-001");
        pedido.confirmar();
        pedido.preparar();
        pedido.entregar();
        assertEquals("ENTREGUE", pedido.getEstado().getNome());
    }

    @Test
    void pedidoDevolvidoAposDevolver() {
        PedidoContext pedido = new PedidoContext("P-001");
        pedido.confirmar();
        pedido.preparar();
        pedido.entregar();
        pedido.devolver();
        assertEquals("DEVOLVIDO", pedido.getEstado().getNome());
    }

    @Test
    void pedidoCanceladoAposCancelar() {
        PedidoContext pedido = new PedidoContext("P-002");
        pedido.confirmar();
        pedido.cancelar();
        assertEquals("CANCELADO", pedido.getEstado().getNome());
    }

    @Test
    void pedidoCanceladoNaoPodeSerReconfirmado() {
        PedidoContext pedido = new PedidoContext("P-002");
        pedido.confirmar();
        pedido.cancelar();
        pedido.confirmar();
        assertEquals("CANCELADO", pedido.getEstado().getNome());
    }

    @Test
    void pedidoPendenteNaoPodePreparar() {
        PedidoContext pedido = new PedidoContext("P-003");
        pedido.preparar();
        assertEquals("PENDENTE", pedido.getEstado().getNome());
    }

    @Test
    void pedidoPendenteNaoPodeEntregar() {
        PedidoContext pedido = new PedidoContext("P-003");
        pedido.entregar();
        assertEquals("PENDENTE", pedido.getEstado().getNome());
    }
}