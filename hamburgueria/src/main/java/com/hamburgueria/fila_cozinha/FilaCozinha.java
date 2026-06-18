package com.hamburgueria.fila_cozinha;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class FilaCozinha {

    private final List<Comando>   filaExecucao = new ArrayList<>();
    private final Deque<Comando>  pilhaUndo    = new ArrayDeque<>();

    public void enfileirar(Comando comando) {
        filaExecucao.add(comando);
    }

    public void executar(Comando comando) {
        comando.executar();
        pilhaUndo.push(comando);
    }

    public void executarFila() {
        for (Comando c : filaExecucao) {
            c.executar();
            pilhaUndo.push(c);
        }
        filaExecucao.clear();
    }

    public void desfazerUltimo() {
        if (pilhaUndo.isEmpty()) {
            return;
        }
        Comando ultimo = pilhaUndo.pop();
        ultimo.desfazer();
    }

    public void desfazerTodos() {
        while (!pilhaUndo.isEmpty()) {
            Comando c = pilhaUndo.pop();
            c.desfazer();
        }
    }

    public int totalNaFila()  { return filaExecucao.size(); }
    public int totalExecutados() { return pilhaUndo.size(); }

    public void listarFila() {
    }
}