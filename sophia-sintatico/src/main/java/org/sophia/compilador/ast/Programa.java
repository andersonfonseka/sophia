package org.sophia.compilador.ast;

import java.util.ArrayList;
import java.util.List;

import org.sophia.compilador.ast.comando.Comando;

public class Programa {

    private String titulo;

    private final List<Comando> comandos;

    public Programa() {
        this.comandos = new ArrayList<>();
    }

    public Programa(String titulo) {
        this();
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Comando> getComandos() {
        return comandos;
    }

    public Comando adicionarComando(Comando comando) {
        comandos.add(comando);
        return comando;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Programa\n");
        sb.append("+- Titulo\n");
        sb.append("|  +-- ").append(titulo).append("\n");
        sb.append("+- Comandos\n");

        for (Comando comando : comandos) {
            sb.append(comando.toTree("|  "));
            sb.append("\n");
        }

        return sb.toString();
    }
}
