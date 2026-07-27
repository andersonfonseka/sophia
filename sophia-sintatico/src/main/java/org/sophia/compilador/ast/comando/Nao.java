package org.sophia.compilador.ast.comando;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.OperacaoUnaria;

public class Nao extends OperacaoUnaria {

    public Nao(Expressao expressao) {
        super(expressao);
    }

    @Override
    public String toString() {
        return "Nao(" + expressao + ")";
    }

}