package org.sophia.runtime;

public class ErroExecucao extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int linha;
    private final int coluna;

    public ErroExecucao(
            String mensagem,
            int linha,
            int coluna) {

        super(mensagem);

        this.linha = linha;
        this.coluna = coluna;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
}
