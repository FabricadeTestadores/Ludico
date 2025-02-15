package com.ludico;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Movimento {
    private static Movimento instancia;
    private Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private int valor_dado;
    private float[] x_principais = tabuleiro.getXQuadradosPrincipais(), y_principais = tabuleiro.getYQuadradosPrincipais();
    private Peca peca;

    private Movimento() {
    }

    public static Movimento instanciar() {
        if (instancia == null)
            instancia = new Movimento();

        return instancia;
    }

    public void mover(Peca peca) {
        if (!peca.getJogadaDisponivel())
            return;

        this.valor_dado = tabuleiro.getValorDado();
        this.peca = peca;
        this.peca.setJogadaFinalizada(false);

        if (peca.getTipoPosicao().equals("base"))
            sairDaBase();
        else
            moverComPulo();
    }

    private void sairDaBase() {
        ImageView img = peca.getImagem();
        Button btn = peca.getBotao();

        peca.setTipoPosicao("quad_principal");
        peca.setTempoEspera(750);
    }

    private void moverComPulo() {

    }
}
