package org.sophia.compilador.ast.funcao;

import java.util.ArrayList;
import java.util.List;

import org.sophia.compilador.ast.Expressao;

public class ChamadaFuncaoExpressao extends Expressao {

    private final String nome;
    private final List<Expressao> argumentos;

    public ChamadaFuncaoExpressao(String nome) {
        this.nome = nome;
        this.argumentos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Expressao> getArgumentos() {
        return argumentos;
    }

    public void adicionarArgumento(Expressao argumento) {
        argumentos.add(argumento);
    }

    @Override
    public String toString() {
        return "ChamadaFuncaoExpressao (" + nome + ")";
    }
}