package com.hamburgueria.aprovacao_desconto;

import com.hamburgueria.configuracoes.Configuracoes;

public class Supervisor extends Aprovador {

    @Override
    public void processar(SolicitacaoDesconto solicitacao) {
        double limite = Configuracoes.getInstance().getLimiteDescontoSupervisor();
        if (solicitacao.getPercentualDesconto() <= limite) {
        } else {
            passarParaProximo(solicitacao);
        }
    }
}