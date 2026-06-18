package com.hamburgueria.aprovacao_desconto;

import com.hamburgueria.configuracoes.Configuracoes;

public class Gerente extends Aprovador {

    @Override
    public void processar(SolicitacaoDesconto solicitacao) {
        double limite = Configuracoes.getInstance().getLimiteDescontoGerente();
        if (solicitacao.getPercentualDesconto() <= limite) {
        } else {
            passarParaProximo(solicitacao);
        }
    }
}