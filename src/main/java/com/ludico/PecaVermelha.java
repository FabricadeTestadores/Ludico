package com.ludico;

public class PecaVermelha extends Peca {
    public PecaVermelha(float x_base, float y_base) {
        super(x_base, y_base);
    }

    protected String definirCor() {
        return "vermelho";
    }

    protected void definirPosicoes() {
        pos_inicial = 2;
        pos_final = 0;
    }
}
