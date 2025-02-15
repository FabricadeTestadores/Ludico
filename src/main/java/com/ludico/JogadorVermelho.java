package com.ludico;

public class JogadorVermelho extends Jogador {

    protected String definirCorClara() {
        return "#FF0000";
    }

    protected String definirCorEscura() {
        return "#8C0000";
    }

    protected Peca[] gerarPecas() {
        Tabuleiro tabuleiro = Tabuleiro.instanciar();
        Peca[] pecas = new Peca[4];
        float[] x_bases = tabuleiro.getXCirculosPequenos(0), y_bases = tabuleiro.getYCirculosPequenos(0);

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaVermelha(x_bases[i], y_bases[i]);

        return pecas;
    }
}
