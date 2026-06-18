package com.hamburgueria.aprovacao_desconto;

public class SolicitacaoDesconto {

    private final int id;
    private final String cliente;
    private final double valorPedido;
    private final double percentualDesconto;
    private final String motivo;

    public SolicitacaoDesconto(int id, String cliente, double valorPedido,
                               double percentualDesconto, String motivo) {
        this.id                  = id;
        this.cliente             = cliente;
        this.valorPedido         = valorPedido;
        this.percentualDesconto  = percentualDesconto;
        this.motivo              = motivo;
    }

    public int getId()                   { return id; }
    public String getCliente()           { return cliente; }
    public double getValorPedido()       { return valorPedido; }
    public double getPercentualDesconto(){ return percentualDesconto; }
    public String getMotivo()            { return motivo; }

    @Override
    public String toString() {
        return String.format("Solicitacao{id=%d, cliente='%s', valor=R$%.2f, desconto=%.0f%%, motivo='%s'}",
                id, cliente, valorPedido, percentualDesconto, motivo);
    }
}