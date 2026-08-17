package org.sophia.runtime;

import java.util.HashMap;
import java.util.Map;

public class ContextoExecucao {

    private Map<String, Variavel> variaveis = new HashMap<>();

    private ContextoExecucao pai;

    public ContextoExecucao() {
        this.pai = null;
    }

    public ContextoExecucao(ContextoExecucao pai) {
        this.pai = pai;
    }

    public void declarar(String nome, Variavel variavel) {
        variaveis.put(nome, variavel);
    }

    public Variavel obter(String nome) {

        Variavel variavel = variaveis.get(nome);
        
        if (variavel != null) {
            return variavel;
        }
        
        if (pai != null) {
            return pai.obter(nome);
        }
        
       	throw new VariavelNaoDeclaradaException(nome);
    }
    
    public void limpar() {
    	this.variaveis.clear();
    }
}