package org.sophia.runtime;

import org.sophia.compilador.ast.comando.TipoVariavel;

public class Variavel {

    private final TipoVariavel tipo;

    private Object valor;

    public Variavel(TipoVariavel tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public TipoVariavel getTipo() {
        return tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

}