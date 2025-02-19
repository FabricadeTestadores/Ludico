package com.ludico;

public class JogadorVerde extends Jogador {
    protected String definirCor() {
        return "verde";
    }

    protected String definirCorClara() {
        return "#00FF00";
    }

    protected Peca[] gerarPecas() {
        Tabuleiro tabuleiro = Tabuleiro.instanciar();
        Peca[] pecas = new Peca[4];
        float[] x_bases = tabuleiro.getXCirculosPequenos(1), y_bases = tabuleiro.getYCirculosPequenos(1);

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaVerde(x_bases[i], y_bases[i], this);

        return pecas;
    }
}
