package com.hamburgueria.ciclo_pedido;

public class PedidoContext {

    private PedidoEstado estado;
    private String       numeroPedido;

    public PedidoContext(String numeroPedido) {
        this.numeroPedido = numeroPedido;
        this.estado       = new PedidoEstadoPendente();
    }

    public void setEstado(PedidoEstado estado) {
        this.estado = estado;
    }

    public PedidoEstado getEstado()      { return estado; }
    public String       getNumeroPedido(){ return numeroPedido; }

    public void confirmar() { estado.confirmar(this); }
    public void preparar()  { estado.preparar(this);  }
    public void entregar()  { estado.entregar(this);  }
    public void cancelar()  { estado.cancelar(this);  }
    public void devolver()  { estado.devolver(this);  }
}
