package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.expressao.Identificador;

public class Atribuicao extends Comando {

    private final Identificador identificador;

    private final Expressao expressao;

    public Atribuicao(
            Identificador identificador,
            Expressao expressao) {

        this.identificador = identificador;
        this.expressao = expressao;

    }

    public Identificador getIdentificador() {
        return identificador;
    }

    public Expressao getExpressao() {
        return expressao;
    }

    @Override
    public String toString() {

        return "Atribuicao "
                + identificador
                + " = "
                + expressao;

    }

    @Override
    public String toTree(String prefixo) {
        return prefixo
                + "+-- Atribuicao "
                + identificador
                + " = "
                + expressao;
    }

}
