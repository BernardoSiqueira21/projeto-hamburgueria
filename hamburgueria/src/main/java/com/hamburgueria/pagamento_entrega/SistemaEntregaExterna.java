package com.hamburgueria.pagamento_entrega;

public class SistemaEntregaExterna {

    private String trackingCode = "";

    public void criarDespacho(String enderecoJson, double pesoKg) {}

    public String gerarRastreamento() {
        trackingCode = "TRK-" + (int)(Math.random() * 90000 + 10000);
        return trackingCode;
    }

    public double calcularFrete(String cep) { return cep.startsWith("36") ? 5.90 : 12.90; }

    public void confirmarColeta() {}
}