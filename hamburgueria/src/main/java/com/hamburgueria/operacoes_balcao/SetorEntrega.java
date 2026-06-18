package com.hamburgueria.operacoes_balcao;

public class SetorEntrega extends Setor {

    public SetorEntrega() {
        super("Entrega");
    }

    public void registrarEndereço(String cliente, String endereco) {
    }

    public void despacharEntregador(String cliente) {
    }

    public void confirmarEntrega(String cliente) {
    }

    public double calcularTaxaEntrega(String bairro) {
        double taxa = bairro.equalsIgnoreCase("Centro") ? 3.00 : 7.00;
        return taxa;
    }
}