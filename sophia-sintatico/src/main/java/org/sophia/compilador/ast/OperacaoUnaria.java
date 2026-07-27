package org.sophia.compilador.ast;

public abstract class OperacaoUnaria extends Expressao {

    protected final Expressao expressao;

    public OperacaoUnaria(Expressao expressao) {
        this.expressao = expressao;
    }

    public Expressao getExpressao() {
        return expressao;
    }

}