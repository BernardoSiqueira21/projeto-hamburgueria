package com.hamburgueria.fila_cozinha;

public class AplicarDescontoComando implements Comando {

    private final PedidoCozinha pedido;
    private final double        percentual;

    public AplicarDescontoComando(PedidoCozinha pedido, double percentual) {
        this.pedido     = pedido;
        this.percentual = percentual;
    }

    @Override public void executar()       { pedido.aplicarDesconto(percentual); }
    @Override public void desfazer()       { pedido.removerDesconto(percentual); }
    @Override public String getDescricao() { return "Aplicar desconto de " + percentual + "% no pedido #" + pedido.getNumero(); }
}