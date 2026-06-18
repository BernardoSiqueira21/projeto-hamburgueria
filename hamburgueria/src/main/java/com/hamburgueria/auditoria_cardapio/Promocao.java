package com.hamburgueria.auditoria_cardapio;

import java.time.LocalDate;

public class Promocao implements Elemento {

    private final String     nome;
    private final String     descricao;
    private final double     percentualDesconto;
    private final LocalDate  validade;
    private final boolean    ativa;

    public Promocao(String nome, String descricao,
                    double percentualDesconto, LocalDate validade, boolean ativa) {
        this.nome               = nome;
        this.descricao          = descricao;
        this.percentualDesconto = percentualDesconto;
        this.validade           = validade;
        this.ativa              = ativa;
    }

    @Override
    public void aceitar(Visitante visitante) {
        visitante.visitar(this);
    }

    public String    getNome()               { return nome; }
    public String    getDescricao()          { return descricao; }
    public double    getPercentualDesconto() { return percentualDesconto; }
    public LocalDate getValidade()           { return validade; }
    public boolean   isAtiva()              { return ativa; }
}