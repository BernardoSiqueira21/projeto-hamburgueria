package com.hamburgueria.templates_pedido;

public class EnderecoEntrega implements Clonavel {

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String complemento;

    public EnderecoEntrega(String rua, String numero, String bairro,
                           String cidade, String complemento) {
        this.rua         = rua;
        this.numero      = numero;
        this.bairro      = bairro;
        this.cidade      = cidade;
        this.complemento = complemento;
    }

    private EnderecoEntrega(EnderecoEntrega outro) {
        this.rua         = outro.rua;
        this.numero      = outro.numero;
        this.bairro      = outro.bairro;
        this.cidade      = outro.cidade;
        this.complemento = outro.complemento;
    }

    @Override
    public EnderecoEntrega clonar() {
        return new EnderecoEntrega(this);
    }

    public String getRua()          { return rua; }
    public String getNumero()       { return numero; }
    public String getBairro()       { return bairro; }
    public String getCidade()       { return cidade; }
    public String getComplemento()  { return complemento; }

    public void setRua(String rua)              { this.rua = rua; }
    public void setNumero(String numero)        { this.numero = numero; }
    public void setBairro(String bairro)        { this.bairro = bairro; }
    public void setCidade(String cidade)        { this.cidade = cidade; }
    public void setComplemento(String comp)     { this.complemento = comp; }

    @Override
    public String toString() {
        return rua + ", " + numero
                + (complemento != null && !complemento.isEmpty() ? " (" + complemento + ")" : "")
                + " — " + bairro + " — " + cidade;
    }
}