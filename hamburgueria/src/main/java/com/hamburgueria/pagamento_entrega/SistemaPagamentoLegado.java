package com.hamburgueria.pagamento_entrega;

public class SistemaPagamentoLegado {

    private String codigoTransacao = "";

    public void iniciarTransacao(String nomeComprador, String cpf, double quantia) {}

    public String confirmarTransacao() {
        codigoTransacao = "LEG-" + System.currentTimeMillis() % 100000;
        return codigoTransacao;
    }

    public String gerarRecibo(String codigo) { return "RECIBO-LEGADO[" + codigo + "]"; }

    public boolean verificarStatus() { return true; }
}