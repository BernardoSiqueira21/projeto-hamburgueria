package com.hamburgueria.acesso_cardapio;

import com.hamburgueria.configuracoes.Configuracoes;
import java.util.Collections;
import java.util.List;

public class CardapioProxy implements ICardapio {

    private CardapioReal      cardapioReal;
    private FuncionarioAcesso usuarioAtual;
    private List<String>      cacheListagem;
    private boolean           cacheValido = false;

    public void setUsuarioAtual(FuncionarioAcesso usuario) {
        this.usuarioAtual = usuario;
    }

    private CardapioReal getCardapioReal() {
        if (cardapioReal == null) {
            cardapioReal = new CardapioReal();
        }
        return cardapioReal;
    }

    private boolean temPermissao(FuncionarioAcesso.Perfil minimo) {
        if (usuarioAtual == null) return false;
        return usuarioAtual.getPerfil().ordinal() >= minimo.ordinal();
    }

    @Override
    public List<String> listarItens() {
        if (!temPermissao(FuncionarioAcesso.Perfil.CLIENTE)) return Collections.emptyList();
        if (cacheValido) return cacheListagem;
        cacheListagem = getCardapioReal().listarItens();
        cacheValido   = true;
        return cacheListagem;
    }

    @Override
    public String buscarItem(String nome) {
        if (!temPermissao(FuncionarioAcesso.Perfil.CLIENTE)) return "";
        return getCardapioReal().buscarItem(nome);
    }

    @Override
    public double consultarPreco(String nome) {
        if (!temPermissao(FuncionarioAcesso.Perfil.CLIENTE)) return -1;
        return getCardapioReal().consultarPreco(nome);
    }

    @Override
    public void adicionarItem(String nome, double preco) {
        if (!temPermissao(FuncionarioAcesso.Perfil.GERENTE)) return;
        Configuracoes cfg = Configuracoes.getInstance();
        if (cfg.getNomeEstabelecimento() == null || cfg.getNomeEstabelecimento().isEmpty()) return;
        getCardapioReal().adicionarItem(nome, preco);
        invalidarCache();
    }

    @Override
    public void removerItem(String nome) {
        if (!temPermissao(FuncionarioAcesso.Perfil.GERENTE)) return;
        getCardapioReal().removerItem(nome);
        invalidarCache();
    }

    @Override
    public void atualizarPreco(String nome, double novoPreco) {
        if (!temPermissao(FuncionarioAcesso.Perfil.GERENTE)) return;
        getCardapioReal().atualizarPreco(nome, novoPreco);
        invalidarCache();
    }

    private void invalidarCache() {
        cacheValido   = false;
        cacheListagem = null;
    }
}