package org.sophia.compilador.ast.comando;

import java.util.List;

import org.sophia.compilador.ast.Expressao;

public class Se extends Comando {

    private final Expressao condicao;

    private final List<Comando> comandosVerdadeiros;

    private final List<Comando> comandosFalsos;

    public Se(
            Expressao condicao,
            List<Comando> comandosVerdadeiros,
            List<Comando> comandosFalsos) {

        this.condicao = condicao;
        this.comandosVerdadeiros = comandosVerdadeiros;
        this.comandosFalsos = comandosFalsos;
    }

    public Expressao getCondicao() {
        return condicao;
    }

    public List<Comando> getComandosVerdadeiros() {
        return comandosVerdadeiros;
    }

    public List<Comando> getComandosFalsos() {
        return comandosFalsos;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Se\n");

        sb.append("  Condição\n");
        sb.append("    ").append(condicao).append("\n");

        sb.append("  Verdadeiro\n");
        for (Comando comando : comandosVerdadeiros) {
            sb.append("    ").append(comando).append("\n");
        }

        if (!comandosFalsos.isEmpty()) {
            sb.append("  Falso\n");
            for (Comando comando : comandosFalsos) {
                sb.append("    ").append(comando).append("\n");
            }
        }

        sb.append("FimSe");

        return sb.toString();
    }
    
    @Override
    public String toTree(String prefixo) {

        StringBuilder sb = new StringBuilder();

        sb.append(prefixo).append("+-- Se\n");

        sb.append(prefixo).append("|   +-- Condicao\n");
        sb.append(prefixo).append("|   |   +-- ").append(condicao).append("\n");

        sb.append(prefixo).append("|   +-- Entao\n");

        for (Comando comando : comandosVerdadeiros) {
            sb.append(comando.toTree(prefixo + "|   |   "));
            sb.append("\n");
        }

        if (!comandosFalsos.isEmpty()) {

            sb.append(prefixo).append("|   +-- Senao\n");

            for (Comando comando : comandosFalsos) {
                sb.append(comando.toTree(prefixo + "|       "));
                //sb.append("\n");
            }

        }

        return sb.toString();
    }
    
    
}
