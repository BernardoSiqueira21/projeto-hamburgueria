package com.hamburgueria.pagamento_entrega;

public class PagamentoLegadoAdapter implements iPagamento {

    private final SistemaPagamentoLegado sistemaLegado;
    private final String                 cpfPadrao;
    private String                       ultimoComprovante = "";

    public PagamentoLegadoAdapter(SistemaPagamentoLegado sistemaLegado, String cpfPadrao) {
        this.sistemaLegado = sistemaLegado;
        this.cpfPadrao     = cpfPadrao;
    }

    @Override
    public boolean processar(String cliente, double valor) {
        sistemaLegado.iniciarTransacao(cliente, cpfPadrao, valor);
        String codigo = sistemaLegado.confirmarTransacao();
        boolean status = sistemaLegado.verificarStatus();
        if (status) ultimoComprovante = sistemaLegado.gerarRecibo(codigo);
        return status;
    }

    @Override public String getFormaPagamento() { return "Sistema Legado (via Adapter)"; }
    @Override public String getComprovante()    { return ultimoComprovante; }
}