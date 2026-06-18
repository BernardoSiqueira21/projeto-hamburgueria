package com.hamburgueria.montagem_lanche;

public class LanchePersonalizado {

    private final String nome;
    private final String tipoPao;
    private final String tipoCarne;
    private final boolean queijo;
    private final boolean bacon;
    private final boolean alface;
    private final boolean tomate;
    private final boolean cebola;
    private final boolean pickles;
    private final String molho;
    private final String observacoes;
    private final double preco;


    private LanchePersonalizado(Builder builder) {
        this.nome        = builder.nome;
        this.tipoPao     = builder.tipoPao;
        this.tipoCarne   = builder.tipoCarne;
        this.queijo      = builder.queijo;
        this.bacon       = builder.bacon;
        this.alface      = builder.alface;
        this.tomate      = builder.tomate;
        this.cebola      = builder.cebola;
        this.pickles     = builder.pickles;
        this.molho       = builder.molho;
        this.observacoes = builder.observacoes;
        this.preco       = builder.preco;
    }

    // Getters
    public String getNome()        { return nome; }
    public String getTipoPao()     { return tipoPao; }
    public String getTipoCarne()   { return tipoCarne; }
    public boolean temQueijo()     { return queijo; }
    public boolean temBacon()      { return bacon; }
    public boolean temAlface()     { return alface; }
    public boolean temTomate()     { return tomate; }
    public boolean temCebola()     { return cebola; }
    public boolean temPickles()    { return pickles; }
    public String getMolho()       { return molho; }
    public String getObservacoes() { return observacoes; }
    public double getPreco()       { return preco; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("╔═══════════════════════════════════╗\n");
        sb.append(String.format("║  %-33s║%n", "🍔 " + nome));
        sb.append("╠═══════════════════════════════════╣\n");
        sb.append(String.format("║  Pão:    %-25s║%n", tipoPao));
        sb.append(String.format("║  Carne:  %-25s║%n", tipoCarne));
        sb.append(String.format("║  Molho:  %-25s║%n", molho));
        sb.append("║  Ingredientes:                    ║\n");
        if (queijo)  sb.append("║    ✔ Queijo                       ║\n");
        if (bacon)   sb.append("║    ✔ Bacon                        ║\n");
        if (alface)  sb.append("║    ✔ Alface                       ║\n");
        if (tomate)  sb.append("║    ✔ Tomate                       ║\n");
        if (cebola)  sb.append("║    ✔ Cebola                       ║\n");
        if (pickles) sb.append("║    ✔ Pickles                      ║\n");
        if (observacoes != null && !observacoes.isEmpty()) {
            sb.append(String.format("║  Obs: %-28s║%n", observacoes));
        }
        sb.append("╠═══════════════════════════════════╣\n");
        sb.append(String.format("║  PREÇO: R$ %-23s║%n", String.format("%.2f", preco)));
        sb.append("╚═══════════════════════════════════╝");
        return sb.toString();
    }


    public static class Builder {


        private final String nome;
        private final String tipoPao;
        private final String tipoCarne;

        private boolean queijo      = false;
        private boolean bacon       = false;
        private boolean alface      = false;
        private boolean tomate      = false;
        private boolean cebola      = false;
        private boolean pickles     = false;
        private String  molho       = "Sem molho";
        private String  observacoes = "";
        private double  preco       = 20.00;

        public Builder(String nome, String tipoPao, String tipoCarne) {
            this.nome      = nome;
            this.tipoPao   = tipoPao;
            this.tipoCarne = tipoCarne;
        }

        public Builder comQueijo()              { this.queijo  = true;  this.preco += 3.00; return this; }
        public Builder comBacon()               { this.bacon   = true;  this.preco += 5.00; return this; }
        public Builder comAlface()              { this.alface  = true;  this.preco += 1.00; return this; }
        public Builder comTomate()              { this.tomate  = true;  this.preco += 1.00; return this; }
        public Builder comCebola()              { this.cebola  = true;  this.preco += 1.00; return this; }
        public Builder comPickles()             { this.pickles = true;  this.preco += 1.00; return this; }
        public Builder comMolho(String molho)   { this.molho   = molho; this.preco += 2.00; return this; }
        public Builder comObservacoes(String o) { this.observacoes = o; return this; }
        public Builder comPrecoBase(double p)   { this.preco   = p;     return this; }

        public LanchePersonalizado build() {
            return new LanchePersonalizado(this);
        }
    }
}