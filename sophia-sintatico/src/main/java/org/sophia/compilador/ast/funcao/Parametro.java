package org.sophia.compilador.ast.funcao;

public class Parametro {

    private String tipo;
    private String nome;

    public Parametro(String tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return tipo.toUpperCase() + " " + nome;
    }
}