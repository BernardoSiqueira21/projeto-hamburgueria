package com.hamburgueria.comunicacao_interna;

import com.hamburgueria.notificacoes.Observable;
import com.hamburgueria.notificacoes.Observer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Central implements Observable {

    private final Map<String, Pessoa>  pessoas   = new HashMap<>();
    private final Map<String, Setor>   setores   = new HashMap<>();
    private final List<Observer>       observers = new ArrayList<>();

    @Override
    public void adicionarObserver(Observer observer) { observers.add(observer); }

    @Override
    public void removerObserver(Observer observer) { observers.remove(observer); }

    @Override
    public void notificarObservers(String evento, Object dados) {
        for (Observer o : observers) o.atualizar(evento, dados);
    }

    public void registrarPessoa(Pessoa pessoa) { pessoas.put(pessoa.getNome(), pessoa); }

    public void registrarSetor(Setor setor) { setores.put(setor.getNome(), setor); }

    public void enviarParaSetor(String remetente, String nomeSetor, String mensagem) {
        Setor setor = setores.get(nomeSetor);
        if (setor != null) setor.receberMensagem(remetente, mensagem);
    }

    public void enviarParaPessoa(String remetente, String nomeDestinatario, String mensagem) {
        Pessoa destino = pessoas.get(nomeDestinatario);
        if (destino != null) destino.receberMensagem(remetente, mensagem);
    }

    public void broadcast(String remetente, String mensagem) {
        setores.values().forEach(s -> s.receberMensagem(remetente, mensagem));
        pessoas.values().stream()
                .filter(p -> !p.getNome().equals(remetente))
                .forEach(p -> p.receberMensagem(remetente, mensagem));
        notificarObservers("BROADCAST", remetente + ": " + mensagem);
    }
}