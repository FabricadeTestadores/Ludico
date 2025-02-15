package com.ludico;

public class PecaVerde extends Peca {
    public PecaVerde(float x_base, float y_base) {
        super(x_base, y_base);
    }

    protected String definirCor() {
        return "verde";
    }

    protected void definirPosicoes() {
        pos_inicial = 15;
        pos_final = 13;
    }
}
