package com.hamburgueria;

import com.hamburgueria.aprovacao_desconto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AprovacaoDeDescontosTest {

    private Atendente  atendente;
    private Supervisor supervisor;
    private Gerente    gerente;
    private Dono       dono;

    @BeforeEach
    void montarCadeia() {
        atendente  = new Atendente();
        supervisor = new Supervisor();
        gerente    = new Gerente();
        dono       = new Dono();
        atendente.setProximo(supervisor);
        supervisor.setProximo(gerente);
        gerente.setProximo(dono);
    }

    @Test
    void solicitacaoComClienteCorreto() {
        SolicitacaoDesconto s = new SolicitacaoDesconto(1, "Ana", 100.00, 5, "Teste");
        assertEquals("Ana", s.getCliente());
    }

    @Test
    void solicitacaoComPercentualCorreto() {
        SolicitacaoDesconto s = new SolicitacaoDesconto(1, "Ana", 100.00, 5, "Teste");
        assertEquals(5.0, s.getPercentualDesconto(), 0.01);
    }

    @Test
    void solicitacaoComValorCorreto() {
        SolicitacaoDesconto s = new SolicitacaoDesconto(1, "Ana", 100.00, 5, "Teste");
        assertEquals(100.00, s.getValorPedido(), 0.01);
    }

    @Test
    void atendenteProcessaDescontoPequeno() {
        assertDoesNotThrow(() -> atendente.processar(
                new SolicitacaoDesconto(1, "João", 89.90, 3, "Frequente")));
    }

    @Test
    void supervisorProcessaDescontoMedio() {
        assertDoesNotThrow(() -> atendente.processar(
                new SolicitacaoDesconto(2, "Maria", 150.00, 10, "Aniversário")));
    }

    @Test
    void gerenteProcessaDescontoAlto() {
        assertDoesNotThrow(() -> atendente.processar(
                new SolicitacaoDesconto(3, "Carlos", 320.00, 25, "Corporativo")));
    }

    @Test
    void donoProcessaDescontoMaximo() {
        assertDoesNotThrow(() -> atendente.processar(
                new SolicitacaoDesconto(4, "VIP", 500.00, 50, "Parceria")));
    }

    @Test
    void cadeiaProcessaSemLancarExcecao() {
        assertDoesNotThrow(() -> {
            atendente.processar(new SolicitacaoDesconto(1,"A",100.00,3,"x"));
            atendente.processar(new SolicitacaoDesconto(2,"B",100.00,10,"x"));
            atendente.processar(new SolicitacaoDesconto(3,"C",100.00,25,"x"));
            atendente.processar(new SolicitacaoDesconto(4,"D",100.00,50,"x"));
        });
    }
}