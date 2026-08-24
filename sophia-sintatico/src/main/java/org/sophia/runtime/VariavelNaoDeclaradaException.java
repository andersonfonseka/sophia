package org.sophia.runtime;

public class VariavelNaoDeclaradaException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String nome;

    public VariavelNaoDeclaradaException(String nome) {
        super("A variável '" + nome + "' não foi declarada.\n");
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

}