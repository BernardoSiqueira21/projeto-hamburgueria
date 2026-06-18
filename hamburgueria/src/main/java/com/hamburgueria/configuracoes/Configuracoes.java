package com.hamburgueria.configuracoes;

public class Configuracoes {

    private static Configuracoes instance;

    private String nomeEstabelecimento    = "Burger House";
    private String enderecoEstabelecimento = "Rua das Hamburguerias, 42";
    private double taxaEntrega            = 5.00;
    private int    tempoMedioPreparo      = 20;
    private boolean aceitaPix             = true;
    private boolean aceitaCartao          = true;

    private double limiteDescontoAtendente  = 5.0;
    private double limiteDescontoSupervisor = 15.0;
    private double limiteDescontoGerente    = 30.0;

    private Configuracoes() {}

    public static Configuracoes getInstance() {
        if (instance == null) instance = new Configuracoes();
        return instance;
    }

    public String  getNomeEstabelecimento()              { return nomeEstabelecimento; }
    public void    setNomeEstabelecimento(String v)      { this.nomeEstabelecimento = v; }
    public String  getEnderecoEstabelecimento()          { return enderecoEstabelecimento; }
    public void    setEnderecoEstabelecimento(String v)  { this.enderecoEstabelecimento = v; }
    public double  getTaxaEntrega()                      { return taxaEntrega; }
    public void    setTaxaEntrega(double v)              { this.taxaEntrega = v; }
    public int     getTempoMedioPreparo()                { return tempoMedioPreparo; }
    public void    setTempoMedioPreparo(int v)           { this.tempoMedioPreparo = v; }
    public boolean isAceitaPix()                         { return aceitaPix; }
    public void    setAceitaPix(boolean v)               { this.aceitaPix = v; }
    public boolean isAceitaCartao()                      { return aceitaCartao; }
    public void    setAceitaCartao(boolean v)            { this.aceitaCartao = v; }

    public double getLimiteDescontoAtendente()           { return limiteDescontoAtendente; }
    public void   setLimiteDescontoAtendente(double v)   { this.limiteDescontoAtendente = v; }
    public double getLimiteDescontoSupervisor()          { return limiteDescontoSupervisor; }
    public void   setLimiteDescontoSupervisor(double v)  { this.limiteDescontoSupervisor = v; }
    public double getLimiteDescontoGerente()             { return limiteDescontoGerente; }
    public void   setLimiteDescontoGerente(double v)     { this.limiteDescontoGerente = v; }

    @Override
    public String toString() {
        return "Configuracoes{nome='" + nomeEstabelecimento
                + "', taxaEntrega=" + taxaEntrega
                + ", tempoPreparo=" + tempoMedioPreparo + "min"
                + ", limDescAtendente=" + limiteDescontoAtendente + "%"
                + ", limDescSupervisor=" + limiteDescontoSupervisor + "%"
                + ", limDescGerente=" + limiteDescontoGerente + "%}";
    }
}
