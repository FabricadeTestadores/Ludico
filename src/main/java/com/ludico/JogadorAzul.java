package com.ludico;

public class JogadorAzul extends Jogador {

    protected String definirCorClara() {
        return "#0000FF";
    }

    protected String definirCorEscura() {
        return "#00008C";
    }

    protected Peca[] gerarPecas() {
        Tabuleiro tabuleiro = Tabuleiro.instanciar();
        Peca[] pecas = new Peca[4];
        float[] x_bases = tabuleiro.getXCirculosPequenos(3), y_bases = tabuleiro.getYCirculosPequenos(3);

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaAzul(x_bases[i], y_bases[i]);

        return pecas;
    }
}
