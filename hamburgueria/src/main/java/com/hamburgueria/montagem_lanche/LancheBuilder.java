package com.hamburgueria.montagem_lanche;

public class LancheBuilder {

    private LanchePersonalizado.Builder builder;

    public LancheBuilder iniciar(String nome, String tipoPao, String tipoCarne) {
        this.builder = new LanchePersonalizado.Builder(nome, tipoPao, tipoCarne);
        return this;
    }

    public LancheBuilder adicionarQueijo() {
        builder.comQueijo();
        return this;
    }

    public LancheBuilder adicionarBacon() {
        builder.comBacon();
        return this;
    }

    public LancheBuilder adicionarAlface() {
        builder.comAlface();
        return this;
    }

    public LancheBuilder adicionarTomate() {
        builder.comTomate();
        return this;
    }

    public LancheBuilder adicionarCebola() {
        builder.comCebola();
        return this;
    }

    public LancheBuilder adicionarPickles() {
        builder.comPickles();
        return this;
    }

    public LancheBuilder adicionarMolho(String molho) {
        builder.comMolho(molho);
        return this;
    }

    public LancheBuilder adicionarObservacoes(String obs) {
        builder.comObservacoes(obs);
        return this;
    }

    public LancheBuilder definirPrecoBase(double preco) {
        builder.comPrecoBase(preco);
        return this;
    }

    public LanchePersonalizado build() {
        return builder.build();
    }
}
