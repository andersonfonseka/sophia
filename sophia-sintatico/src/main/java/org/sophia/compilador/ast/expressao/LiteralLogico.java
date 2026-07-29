package org.sophia.compilador.ast.expressao;

import org.sophia.compilador.ast.Expressao;

public class LiteralLogico extends Expressao {

    private final Boolean valor;

    public LiteralLogico(Boolean valor) {
        this.valor = valor;
    }

    public String getValor() {
    	return valor ? "verdadeiro" : "falso";
    }

    @Override
    public String toString() {
        return "LiteralLogico (" + (valor ? "verdadeiro" : "falso") + ")";
    }
}
