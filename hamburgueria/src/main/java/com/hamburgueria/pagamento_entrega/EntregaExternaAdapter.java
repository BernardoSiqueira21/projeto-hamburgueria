package com.hamburgueria.pagamento_entrega;

public class EntregaExternaAdapter implements iEntrega {

    private final SistemaEntregaExterna sistemaExterno;
    private String rastreamento = "";

    public EntregaExternaAdapter(SistemaEntregaExterna sistemaExterno) {
        this.sistemaExterno = sistemaExterno;
    }

    @Override
    public boolean despachar(String endereco, String cep) {
        String enderecoJson = "{\"rua\":\"" + endereco + "\",\"cep\":\"" + cep + "\"}";
        sistemaExterno.criarDespacho(enderecoJson, 0.5);
        rastreamento = sistemaExterno.gerarRastreamento();
        sistemaExterno.confirmarColeta();
        return true;
    }

    @Override public String getRastreamento()            { return rastreamento; }
    @Override public double getTaxaEntrega(String cep)   { return sistemaExterno.calcularFrete(cep); }
}