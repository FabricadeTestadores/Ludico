package com.ludico;

public class PecaVermelha extends Peca {
    public PecaVermelha(float x_base, float y_base, Jogador jog) {
        super(x_base, y_base, jog);
    }

    protected String definirCor() {
        return "vermelho";
    }

    protected String definirCorEscura() {
        return "#8C0000";
    }

    protected void definirPosicoes() {
        pos_inicial = 2;
        pos_final = 0;
        x_finais = tabuleiro.getXQuadradosFinais(0);
        y_finais = tabuleiro.getYQuadradosFinais(0);
    }
}
