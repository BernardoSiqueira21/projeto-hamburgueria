package com.hamburgueria.notificacoes;

import com.hamburgueria.ciclo_pedido.PedidoContext;
import java.util.ArrayList;
import java.util.List;

public class Mesa implements Observable {

    private final int            numero;
    private String               statusPedido;
    private final List<Observer> observers = new ArrayList<>();
    private PedidoContext        pedidoAtual;

    public Mesa(int numero) {
        this.numero       = numero;
        this.statusPedido = "LIVRE";
    }

    @Override public void adicionarObserver(Observer o) { observers.add(o); }
    @Override public void removerObserver(Observer o)   { observers.remove(o); }

    @Override
    public void notificarObservers(String evento, Object dados) {
        for (Observer o : observers) o.atualizar(evento, dados);
    }

    public void setPedidoAtual(PedidoContext pedido) {
        this.pedidoAtual = pedido;
        setStatusPedido(pedido.getEstado().getNome());
    }

    public void avancarEstadoPedido() {
        if (pedidoAtual == null) return;
        String estadoAntes = pedidoAtual.getEstado().getNome();
        switch (estadoAntes) {
            case "PENDENTE"   -> pedidoAtual.confirmar();
            case "CONFIRMADO" -> pedidoAtual.preparar();
            case "EM_PREPARO" -> pedidoAtual.entregar();
        }
        String estadoDepois = pedidoAtual.getEstado().getNome();
        if (!estadoAntes.equals(estadoDepois)) setStatusPedido(estadoDepois);
    }

    public void setStatusPedido(String status) {
        this.statusPedido = status;
        notificarObservers("STATUS_PEDIDO_ATUALIZADO", "Mesa " + numero + ": " + status);
    }

    public int           getNumero()       { return numero; }
    public String        getStatusPedido() { return statusPedido; }
    public PedidoContext getPedidoAtual()  { return pedidoAtual; }
}