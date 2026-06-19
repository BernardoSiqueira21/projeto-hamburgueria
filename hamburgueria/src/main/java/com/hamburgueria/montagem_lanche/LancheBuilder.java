package com.hamburgueria.montagem_lanche;

import com.hamburgueria.personalizacao_lanche.IngredienteDecorator;
import com.hamburgueria.personalizacao_lanche.LancheBase;
import com.hamburgueria.personalizacao_lanche.ComQueijo;
import com.hamburgueria.personalizacao_lanche.ComBacon;
import com.hamburgueria.personalizacao_lanche.ComAlface;
import com.hamburgueria.personalizacao_lanche.ComTomate;
import com.hamburgueria.personalizacao_lanche.ComPickles;
import com.hamburgueria.personalizacao_lanche.ComMolhoEspecial;

public class LancheBuilder {

    private LanchePersonalizado.Builder builder;
    private String  nome;
    private double  precoBase;
    private double  calorias;
    private boolean queijo;
    private boolean bacon;
    private boolean alface;
    private boolean tomate;
    private boolean pickles;
    private boolean molhoEspecial;

    public LancheBuilder iniciar(String nome, String tipoPao, String tipoCarne) {
        this.builder       = new LanchePersonalizado.Builder(nome, tipoPao, tipoCarne);
        this.nome          = nome;
        this.precoBase     = 20.00;
        this.calorias      = 500.0;
        this.queijo        = false;
        this.bacon         = false;
        this.alface        = false;
        this.tomate        = false;
        this.pickles       = false;
        this.molhoEspecial = false;
        return this;
    }

    public LancheBuilder adicionarQueijo()   { builder.comQueijo();  queijo  = true; return this; }
    public LancheBuilder adicionarBacon()    { builder.comBacon();   bacon   = true; return this; }
    public LancheBuilder adicionarAlface()   { builder.comAlface();  alface  = true; return this; }
    public LancheBuilder adicionarTomate()   { builder.comTomate();  tomate  = true; return this; }
    public LancheBuilder adicionarCebola()   { builder.comCebola();           return this; }
    public LancheBuilder adicionarPickles()  { builder.comPickles(); pickles = true; return this; }

    public LancheBuilder adicionarMolho(String molho) {
        builder.comMolho(molho);
        molhoEspecial = true;
        return this;
    }

    public LancheBuilder adicionarObservacoes(String obs) { builder.comObservacoes(obs); return this; }

    public LancheBuilder definirPrecoBase(double preco) {
        builder.comPrecoBase(preco);
        this.precoBase = preco;
        return this;
    }

    public LancheBuilder definirCalorias(double cal) { this.calorias = cal; return this; }

    /** Retorna o LanchePersonalizado (Builder puro) */
    public LanchePersonalizado build() { return builder.build(); }

    public IngredienteDecorator buildDecorado() {
        IngredienteDecorator lanche = new LancheBase(nome, precoBase, calorias);
        if (queijo)        lanche = new ComQueijo(lanche);
        if (bacon)         lanche = new ComBacon(lanche);
        if (alface)        lanche = new ComAlface(lanche);
        if (tomate)        lanche = new ComTomate(lanche);
        if (pickles)       lanche = new ComPickles(lanche);
        if (molhoEspecial) lanche = new ComMolhoEspecial(lanche);
        return lanche;
    }
}