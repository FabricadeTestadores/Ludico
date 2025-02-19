package com.ludico;

public class PecaVerde extends Peca {
    public PecaVerde(float x_base, float y_base, Jogador jog) {
        super(x_base, y_base, jog);
    }

    protected String definirCor() {
        return "verde";
    }

    protected String definirCorEscura() {
        return "#008C00";
    }

    protected void definirPosicoes() {
        pos_inicial = 15;
        pos_final = 13;
        x_finais = tabuleiro.getXQuadradosFinais(1);
        y_finais = tabuleiro.getYQuadradosFinais(1);
    }
}
