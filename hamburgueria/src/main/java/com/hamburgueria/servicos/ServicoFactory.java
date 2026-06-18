package com.hamburgueria.servicos;

public class ServicoFactory {

    public static IServico obterServico(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "REALIZAR_PEDIDO"      -> new ServicoRealizarPedido();
            case "EMITIR_NOTA"          -> new ServicoEmitirNota();
            case "CANCELAR_PEDIDO"      -> new ServicoCancelarPedido();
            case "ENTREGAR_PEDIDO"      -> new ServicoEntregarPedido();
            case "PROCESSAR_PAGAMENTO"  -> new ServicoProcessarPagamento();
            default -> throw new IllegalArgumentException("Serviço desconhecido: " + tipo);
        };
    }
}