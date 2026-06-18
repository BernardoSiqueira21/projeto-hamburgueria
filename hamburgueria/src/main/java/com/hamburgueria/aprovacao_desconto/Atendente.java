package com.hamburgueria.aprovacao_desconto;

import com.hamburgueria.configuracoes.Configuracoes;

public class Atendente extends Aprovador {

    @Override
    public void processar(SolicitacaoDesconto solicitacao) {
        double limite = Configuracoes.getInstance().getLimiteDescontoAtendente();
        if (solicitacao.getPercentualDesconto() <= limite) {
        } else {
            passarParaProximo(solicitacao);
        }
    }
}