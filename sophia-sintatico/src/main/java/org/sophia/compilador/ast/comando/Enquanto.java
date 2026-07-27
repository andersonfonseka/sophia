package org.sophia.compilador.ast.comando;

import java.util.List;

import org.sophia.compilador.ast.Expressao;

public class Enquanto extends Comando {

    private final Expressao condicao;
    private final List<Comando> comandos;

    public Enquanto(
            Expressao condicao,
            List<Comando> comandos) {

        this.condicao = condicao;
        this.comandos = comandos;
    }

    public Expressao getCondicao() {
        return condicao;
    }

    public List<Comando> getComandos() {
        return comandos;
    }

    @Override
    public String toTree(String prefixo) {

        StringBuilder sb = new StringBuilder();

        sb.append(prefixo).append("+-- Enquanto\n");

        // Condição
        sb.append(prefixo).append("|  +-- Condicao\n");
        sb.append(prefixo)
          .append("|  |   +-- ")
          .append(condicao)
          .append("\n");

        // Corpo do laço
        sb.append(prefixo).append("|  +-- Faca\n");

        for (Comando comando : comandos) {

            sb.append(prefixo)
            .append(comando.toTree(prefixo + "|  |   "))
            .append("\n");        }

        return sb.toString();
    }

}
