package com.ludico;

public class PecaAmarela extends Peca {
    public PecaAmarela(float x_base, float y_base) {
        super(x_base, y_base);
    }

    protected String definirCor() {
        return "amarelo";
    }

    protected void definirPosicoes() {
        pos_inicial = 28;
        pos_final = 26;
        x_finais = tabuleiro.getXQuadradosFinais(2);
        y_finais = tabuleiro.getYQuadradosFinais(2);
    }
}
