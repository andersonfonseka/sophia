package org.sophia.compilador.ast.tipo;

import java.math.BigDecimal;

import org.sophia.compilador.ast.Expressao;

public class Lista extends Expressao {

    private final Object[] valor;

    public Lista(BigDecimal valor) {
        this.valor = new Object[valor.intValue()];
    }
    
    public Object getValor() {
    	return this.valor;
    }

    @Override
    public String toString() {
        return "Lista (" + valor.toString()  + ")";
    }
}