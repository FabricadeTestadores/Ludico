package com.ludico;

public class PecaAzul extends Peca {
    public PecaAzul(float x_base, float y_base) {
        super(x_base, y_base);
    }

    protected String definirCor() {
        return "azul";
    }

    protected void definirPosicoes() {
        pos_inicial = 41;
        pos_final = 39;
    }
}
