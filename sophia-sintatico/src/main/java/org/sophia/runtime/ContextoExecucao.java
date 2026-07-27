package org.sophia.runtime;

import java.util.HashMap;
import java.util.Map;

public class ContextoExecucao {

    private final Map<String, Variavel> variaveis =
            new HashMap<>();

    public void declarar(String nome, Variavel variavel) {
        variaveis.put(nome, variavel);
    }

    public Variavel obter(String nome) {
        return variaveis.get(nome);
    }

}
