package com.hamburgueria.aprovacao_desconto;

public abstract class Aprovador {

    protected Aprovador proximo;

    public void setProximo(Aprovador proximo) { this.proximo = proximo; }

    public abstract void processar(SolicitacaoDesconto solicitacao);

    protected void passarParaProximo(SolicitacaoDesconto solicitacao) {
        if (proximo != null) proximo.processar(solicitacao);
    }
}
