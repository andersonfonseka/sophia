package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;

public class Escreva extends Comando {

    private Expressao expressao;

    public Escreva() {}

    public Escreva(Expressao expressao) {
        this.expressao = expressao;
    }

    public Expressao getExpressao() {
        return expressao;
    }

    public void setExpressao(Expressao expressao) {
        this.expressao = expressao;
    }

    @Override
    public String toString() {
        return "Escreva " + expressao + "";
    }

    @Override
    public String toTree(String prefixo) {
        return prefixo + "+-- " + this;
    }
    
}