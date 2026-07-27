package org.sophia.compilador.ast.expressao;

import java.math.BigDecimal;

import org.sophia.compilador.ast.Expressao;

public class LiteralNumero extends Expressao {

    private final BigDecimal valor;

    public LiteralNumero(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "LiteralNumero (" + valor.toPlainString() + ")";
    }
}