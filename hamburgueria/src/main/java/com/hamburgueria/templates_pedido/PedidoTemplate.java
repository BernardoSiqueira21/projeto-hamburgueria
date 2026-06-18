package com.hamburgueria.templates_pedido;

import java.util.ArrayList;
import java.util.List;

public class PedidoTemplate implements Clonavel {

    private String            nomeCliente;
    private String            tipoPagamento;
    private String            observacoes;
    private List<String>      itens;
    private EnderecoEntrega   endereco;
    private boolean           delivery;

    public PedidoTemplate(String nomeCliente, String tipoPagamento,
                          String observacoes, boolean delivery,
                          EnderecoEntrega endereco) {
        this.nomeCliente   = nomeCliente;
        this.tipoPagamento = tipoPagamento;
        this.observacoes   = observacoes;
        this.delivery      = delivery;
        this.endereco      = endereco;
        this.itens         = new ArrayList<>();
    }


    private PedidoTemplate(PedidoTemplate outro) {
        this.nomeCliente   = outro.nomeCliente;
        this.tipoPagamento = outro.tipoPagamento;
        this.observacoes   = outro.observacoes;
        this.delivery      = outro.delivery;
        this.itens         = new ArrayList<>(outro.itens);          // cópia da lista
        this.endereco      = outro.endereco != null                 // cópia profunda do endereço
                ? outro.endereco.clonar()
                : null;
    }

    @Override
    public PedidoTemplate clonar() {
        return new PedidoTemplate(this);
    }


    public void adicionarItem(String item)      { itens.add(item); }
    public void removerItem(String item)        { itens.remove(item); }
    public void setNomeCliente(String nome)     { this.nomeCliente = nome; }
    public void setTipoPagamento(String tipo)   { this.tipoPagamento = tipo; }
    public void setObservacoes(String obs)      { this.observacoes = obs; }
    public void setDelivery(boolean delivery)   { this.delivery = delivery; }
    public void setEndereco(EnderecoEntrega e)  { this.endereco = e; }


    public String            getNomeCliente()   { return nomeCliente; }
    public String            getTipoPagamento() { return tipoPagamento; }
    public String            getObservacoes()   { return observacoes; }
    public boolean           isDelivery()       { return delivery; }
    public List<String>      getItens()         { return new ArrayList<>(itens); }
    public EnderecoEntrega   getEndereco()      { return endereco; }

    @Override
    public String toString() {
        return "PedidoTemplate{\n"
                + "  cliente='"      + nomeCliente   + "'\n"
                + "  pagamento='"    + tipoPagamento  + "'\n"
                + "  delivery="      + delivery       + "\n"
                + "  itens="         + itens          + "\n"
                + "  endereço='"     + (endereco != null ? endereco : "N/A") + "'\n"
                + "  observações='"  + observacoes    + "'\n"
                + "}";
    }
}