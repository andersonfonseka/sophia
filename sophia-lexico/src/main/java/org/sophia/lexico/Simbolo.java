package org.sophia.lexico;

public class Simbolo {

	private int indice;
	
	private String texto;

	private CategoriaSimbolo categoria;

	private int linha;

	private int coluna;
	
	public int getIndice() {
		return indice;
	}

	public void setIndice(int indice) {
		this.indice = indice;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public CategoriaSimbolo getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaSimbolo categoria) {
		this.categoria = categoria;
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	@Override
	public String toString() {
		return "Simbolo [indice=" + indice + ", texto=" + texto + ", categoria=" + categoria + ", linha=" + linha
				+ ", coluna=" + coluna + "]";
	}

}
