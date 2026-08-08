package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;

public class Retorne extends Comando {

    private final Expressao expressao;

    public Retorne(Expressao expressao) {
        this.expressao = expressao;
    }

    public Expressao getExpressao() {
        return expressao;
    }

    @Override
    public String toTree(String prefixo) {
        return prefixo + "+-- Retorne " + expressao;
    }
}