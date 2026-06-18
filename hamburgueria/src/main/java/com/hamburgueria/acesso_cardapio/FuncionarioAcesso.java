package com.hamburgueria.acesso_cardapio;

public class FuncionarioAcesso {

    public enum Perfil { CLIENTE, ATENDENTE, GERENTE, ADMIN }

    private final String nome;
    private final Perfil perfil;
    private final String senha;

    public FuncionarioAcesso(String nome, Perfil perfil, String senha) {
        this.nome   = nome;
        this.perfil = perfil;
        this.senha  = senha;
    }

    public boolean autenticar(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    public String getNome()   { return nome; }
    public Perfil getPerfil() { return perfil; }

    @Override
    public String toString() {
        return nome + " [" + perfil + "]";
    }
}