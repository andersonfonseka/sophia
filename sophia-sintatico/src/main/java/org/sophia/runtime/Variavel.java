package org.sophia.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.sophia.compilador.ast.comando.TipoVariavel;

public class Variavel {

    private final TipoVariavel tipo;
    
    private String nome;

    private Object valor;
    
    private Object[] colecao;

    public Variavel(TipoVariavel tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
    }
    
    public Variavel(TipoVariavel tipo, String nome, Object valor) {
        this.tipo = tipo;
        this.nome = nome;
        this.valor = valor;
    
        if (valor instanceof Number) {
            BigDecimal val = (BigDecimal) valor;
            this.colecao = new Object[val.intValue()];
        }
    }

    public TipoVariavel getTipo() {
        return tipo;
    }
    
    public String getNome() {
		return nome;
	}

	public Object getValor() {
        return valor;
    }
	
	public Object getValor(BigDecimal posicao) {
        return this.colecao[posicao.intValue()];
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }
    
    public void setValor(BigDecimal posicao, Object valor) {
        this.colecao[posicao.intValue()] = valor;
    }
    
    public boolean isColecaoNula() {
    	
    	if (this.tipo != TipoVariavel.LISTA) {
    		return true;
    	}
    	
    	return this.colecao == null;
    }

	public Object[] getColecao() {
		return colecao;
	}

}