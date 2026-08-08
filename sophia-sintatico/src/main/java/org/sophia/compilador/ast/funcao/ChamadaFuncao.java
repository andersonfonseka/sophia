package org.sophia.compilador.ast.funcao;

import java.util.ArrayList;
import java.util.List;

import org.sophia.compilador.ast.Expressao;
import org.sophia.compilador.ast.comando.Comando;

public class ChamadaFuncao extends Comando {

    private String nome;

    private List<Expressao> argumentos;

    public ChamadaFuncao(String nome) {
        this.nome = nome;
        this.argumentos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Expressao> getArgumentos() {
        return argumentos;
    }

    public void adicionarArgumento(Expressao argumento) {
        this.argumentos.add(argumento);
    }

    @Override
    public String toTree(String prefixo) {

        StringBuilder sb = new StringBuilder();

        sb.append(prefixo)
          .append("+-- ChamadaFuncao (")
          .append(nome)
          .append(")");

        if (!argumentos.isEmpty()) {

            sb.append("\n");

            sb.append(prefixo)
              .append("|   +-- Argumentos");

            for (Expressao argumento : argumentos) {

                sb.append("\n");

                sb.append(prefixo)
                  .append("|   |   +-- ")
                  .append(argumento);
            }
        }

        return sb.toString();
    }
}
