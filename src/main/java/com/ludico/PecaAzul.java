package com.ludico;

public class PecaAzul extends Peca {
    public PecaAzul(float x_base, float y_base, Jogador jog) {
        super(x_base, y_base, jog);
    }

    protected String definirCor() {
        return "azul";
    }

    protected String definirCorEscura() {
        return "#00008C";
    }

    protected void definirPosicoes() {
        pos_inicial = 41;
        pos_final = 39;
        x_finais = tabuleiro.getXQuadradosFinais(3);
        y_finais = tabuleiro.getYQuadradosFinais(3);
    }
}
