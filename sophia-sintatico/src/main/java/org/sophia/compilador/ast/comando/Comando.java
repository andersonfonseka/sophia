package org.sophia.compilador.ast.comando;

import org.sophia.lexico.Simbolo;

public abstract class Comando {

	private int linha;
    private int coluna;

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public void setLocalizacao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }

    public void setLocalizacao(Simbolo simbolo) {
        this.linha = simbolo.getLinha();
        this.coluna = simbolo.getColuna();
    }
	
	public abstract String toTree(String prefixo);

}
