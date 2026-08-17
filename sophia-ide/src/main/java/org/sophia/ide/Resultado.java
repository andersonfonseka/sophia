package org.sophia.ide;

public class Resultado {

    private final String saida;
    private final String erro;
    private final boolean sucesso;
    private final int linha;
    private final int coluna;

    private Resultado(
            String saida,
            String erro,
            boolean sucesso,
            int linha,
            int coluna) {

        this.saida = saida;
        this.erro = erro;
        this.sucesso = sucesso;
        this.linha = linha;
        this.coluna = coluna;
    }

    public static Resultado sucesso(String saida) {
        return new Resultado(saida, null, true, -1, -1);
    }

    public static Resultado erro(String erro) {
        return new Resultado(null, erro, false, -1, -1);
    }

    public static Resultado erro(
            String erro,
            int linha,
            int coluna) {

        return new Resultado(
                null,
                erro,
                false,
                linha,
                coluna
        );
    }

    public String getSaida() {
        return saida;
    }

    public String getErro() {
        return erro + "linha: " + linha + ", coluna: " + coluna;
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
}