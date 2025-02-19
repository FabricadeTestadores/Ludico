package com.ludico;

public class JogadorAmarelo extends Jogador {
    protected String definirCor() {
        return "amarelo";
    }

    protected String definirCorClara() {
        return "#FFFF00";
    }

    protected Peca[] gerarPecas() {
        Tabuleiro tabuleiro = Tabuleiro.instanciar();
        Peca[] pecas = new Peca[4];
        float[] x_bases = tabuleiro.getXCirculosPequenos(2), y_bases = tabuleiro.getYCirculosPequenos(2);

        for (int i = 0; i < 4; i++)
            pecas[i] = new PecaAmarela(x_bases[i], y_bases[i], this);

        return pecas;
    }
}
