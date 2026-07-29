package org.sophia.compilador.ast.comando;

public class Leia extends Comando {

	private final String identificador;

	public Leia(String identificador) {
		this.identificador = identificador;
	}

	public String getIdentificador() {
		return identificador;
	}

	@Override
	public String toTree(String prefixo) {
		return prefixo + "+-- Leia " + identificador + "\n";
	}
}