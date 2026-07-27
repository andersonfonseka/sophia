package org.sophia.compilador.ast.expressao;

import org.sophia.compilador.ast.Expressao;

public class Resto extends Expressao {

    private final Expressao dividendo;

    private final Expressao divisor;

    public Resto(
            Expressao dividendo,
            Expressao divisor) {

        this.dividendo = dividendo;
        this.divisor = divisor;

    }

    public Expressao getDividendo() {
        return dividendo;
    }

    public Expressao getDivisor() {
        return divisor;
    }

    @Override
    public String toString() {

        return "Resto("
                + dividendo
                + ", "
                + divisor
                + ")";

    }

}
