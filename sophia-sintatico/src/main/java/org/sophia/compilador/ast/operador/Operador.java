package org.sophia.compilador.ast.operador;

public enum Operador {

    MAIS("mais"),

    MENOS("menos"),

    VEZES("vezes"),
    
    VEZ("vez"),

    DIVIDIDO_POR("dividido por"),
    
    RESTO_DE("resto de"),

    MAIOR_QUE("maior que"),

    MENOR_QUE("menor que"),

    IGUAL_A("igual a");

    private final String texto;

    Operador(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public static Operador fromTexto(String texto) {

        for (Operador operador : values()) {
            if (operador.texto.equalsIgnoreCase(texto)) {
                return operador;
            }
        }

        throw new IllegalArgumentException(
                "Operador desconhecido: " + texto);
    }
}