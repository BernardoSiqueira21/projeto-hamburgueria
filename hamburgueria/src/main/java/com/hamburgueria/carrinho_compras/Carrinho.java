package com.hamburgueria.carrinho_compras;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {

    private String       nomeCliente;
    private List<String> itens       = new ArrayList<>();
    private double       total       = 0.0;
    private String       status      = "ABERTO";
    private String       observacoes = "";

    public Carrinho(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public void adicionarItem(String item, double preco) {
        itens.add(item);
        total += preco;
    }

    public void removerItem(String item, double preco) {
        if (itens.remove(item)) total -= preco;
    }

    public void setStatus(String status)   { this.status = status; }
    public void setObservacoes(String obs) { this.observacoes = obs; }

    public CarrinhoSnapshot salvar() {
        return new CarrinhoSnapshot(status, itens, total, observacoes);
    }

    public void restaurar(CarrinhoSnapshot snap) {
        this.itens       = snap.getItens();
        this.total       = snap.getTotal();
        this.status      = snap.getStatus();
        this.observacoes = snap.getObservacoes();
    }

    public void exibir() {}

    public String       getNomeCliente() { return nomeCliente; }
    public String       getStatus()      { return status; }
    public double       getTotal()       { return total; }
    public List<String> getItens()       { return new ArrayList<>(itens); }
}
