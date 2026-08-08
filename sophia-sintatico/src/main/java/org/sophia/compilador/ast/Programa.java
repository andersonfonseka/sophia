package org.sophia.compilador.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sophia.compilador.ast.comando.Comando;
import org.sophia.compilador.ast.funcao.Funcao;
import org.sophia.compilador.ast.funcao.Parametro;

public class Programa {

    private String titulo;

    private Map<String, Funcao> funcoes;
    
    private final List<Comando> comandos;

    public Programa() {
    	this.funcoes = new HashMap<>();
        this.comandos = new ArrayList<>();
    }

    public Programa(String titulo) {
        this();
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Comando> getComandos() {
        return comandos;
    }

    public Comando adicionarComando(Comando comando) {
        comandos.add(comando);
        return comando;
    }
    
    public Map<String, Funcao> getFuncoes() {
		return funcoes;
	}

	public Funcao adicionarFuncao(Funcao funcao) {
		funcoes.put(funcao.getNome(), funcao);
		return funcao;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Programa\n");
        sb.append("+- Titulo\n");
        sb.append("|  +-- ").append(titulo).append("\n");

        sb.append("+- Funcoes\n");

        for (Funcao funcao : funcoes.values()) {
            sb.append("|  +-- Funcao " + funcao.getNome());
            sb.append("\n");
            
            if (!funcao.getParametros().isEmpty()) {
                sb.append("|  |   +-- Parametros\n");

                for (Parametro parametro : funcao.getParametros()) {
                    sb.append("|  |   |   +-- ")
                      .append(parametro.getTipo().toUpperCase())
                      .append(" ")
                      .append(parametro.getNome())
                      .append("\n");
                }
            }
            
            if (funcao.getTipoRetorno() != null) {
                sb.append("|  |   +-- Retorno\n")
                  .append("|  |   |   +-- ")
                  .append(funcao.getTipoRetorno())
                  .append("\n");
            }
            
            for (Comando comando : funcao.getComandos()) {
                sb.append(comando.toTree("|  |   "));
                sb.append("\n");
            }
        }
        
        sb.append("+- Comandos\n");

        for (Comando comando : comandos) {
            sb.append(comando.toTree("|  "));
            sb.append("\n");
        }

        return sb.toString();
    }
}
