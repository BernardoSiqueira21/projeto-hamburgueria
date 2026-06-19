package com.hamburgueria;

import com.hamburgueria.servicos.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CriacaoDeServicosTest {

    @Test
    void criaServicoRealizarPedido() {
        IServico servico = ServicoFactory.obterServico("REALIZAR_PEDIDO");
        assertEquals("RealizarPedido", servico.getNome());
    }

    @Test
    void criaServicoEmitirNota() {
        IServico servico = ServicoFactory.obterServico("EMITIR_NOTA");
        assertEquals("EmitirNota", servico.getNome());
    }

    @Test
    void criaServicoCancelarPedido() {
        IServico servico = ServicoFactory.obterServico("CANCELAR_PEDIDO");
        assertEquals("CancelarPedido", servico.getNome());
    }

    @Test
    void criaServicoEntregarPedido() {
        IServico servico = ServicoFactory.obterServico("ENTREGAR_PEDIDO");
        assertEquals("EntregarPedido", servico.getNome());
    }

    @Test
    void criaServicoProcessarPagamento() {
        IServico servico = ServicoFactory.obterServico("PROCESSAR_PAGAMENTO");
        assertEquals("ProcessarPagamento", servico.getNome());
    }

    @Test
    void servicoInvalidoLancaExcecao() {
        assertThrows(IllegalArgumentException.class,
                () -> ServicoFactory.obterServico("INVALIDO"));
    }

    @Test
    void servicoExecutaSemExcecao() {
        IServico servico = ServicoFactory.obterServico("REALIZAR_PEDIDO");
        assertDoesNotThrow(servico::executar);
    }
}