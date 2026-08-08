package org.sophia.compilador.ast.funcao;

import java.util.ArrayList;
import java.util.List;

import org.sophia.compilador.ast.comando.Comando;
import org.sophia.compilador.ast.comando.TipoVariavel;

public class Funcao {
	
	private String nome;

	private List<Parametro> parametros;
	
	private TipoVariavel tipoRetorno;
	
    private List<Comando> comandos;
    
    public Funcao(String nome) {
        this.nome = nome;
        this.parametros = new ArrayList<>();
        this.comandos = new ArrayList<>();
    }
    
	public Funcao(String nome, List<Comando> comandos) {
		super();
		this.nome = nome;
		this.comandos = comandos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Comando> getComandos() {
		return comandos;
	}

	public void setComandos(List<Comando> comandos) {
		this.comandos = comandos;
	}
	
	public void adicionarParametro(Parametro parametro) {
	    parametros.add(parametro);
	}

	public List<Parametro> getParametros() {
	    return parametros;
	}

	public void adicionarComando(Comando comando) {
		this.comandos.add(comando);
	}
	
	public TipoVariavel getTipoRetorno() {
	    return tipoRetorno;
	}

	public void setTipoRetorno(TipoVariavel tipoRetorno) {
	    this.tipoRetorno = tipoRetorno;
	}

}
