package com.hamburgueria.fila_cozinha;

import java.util.ArrayList;
import java.util.List;

public class PedidoCozinha {

    private final String       numero;
    private final String       nomeCliente;
    private String             status;
    private final List<String> itens     = new ArrayList<>();
    private final List<String> historico = new ArrayList<>();

    public PedidoCozinha(String numero, String nomeCliente) {
        this.numero      = numero;
        this.nomeCliente = nomeCliente;
        this.status      = "AGUARDANDO";
        registrar("Pedido criado para " + nomeCliente);
    }

    public void abrir()           { status = "ABERTO";     registrar("Pedido aberto"); }
    public void fechar()          { status = "FECHADO";    registrar("Pedido fechado"); }
    public void iniciarPreparo()  { status = "EM_PREPARO"; registrar("Preparo iniciado"); }
    public void finalizarPreparo(){ status = "PRONTO";     registrar("Preparo finalizado"); }
    public void cancelar()        { status = "CANCELADO";  registrar("Pedido cancelado"); }

    public void adicionarItem(String item) { itens.add(item);    registrar("Item adicionado: " + item); }
    public void removerItem(String item)   { itens.remove(item); registrar("Item removido: " + item); }

    public void aplicarDesconto(double p)  { registrar("Desconto de " + p + "% aplicado"); }
    public void removerDesconto(double p)  { registrar("Desconto de " + p + "% removido"); }

    private void registrar(String acao) { historico.add(acao); }

    public String       getNumero()      { return numero; }
    public String       getNomeCliente() { return nomeCliente; }
    public String       getStatus()      { return status; }
    public List<String> getItens()       { return new ArrayList<>(itens); }
    public List<String> getHistorico()   { return new ArrayList<>(historico); }

    public void exibir()          {}
    public void exibirHistorico() {}
}