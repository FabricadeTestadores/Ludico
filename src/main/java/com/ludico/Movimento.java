package com.ludico;

import javafx.animation.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public class Movimento {
    private static Movimento instancia;
    private Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private Peca peca;
    private Jogador[] jogs;
    private TelaPerguntas tela = TelaPerguntas.instanciar(null);
    private float[] x_principais = tabuleiro.getXQuadradosPrincipais(), y_principais = tabuleiro.getYQuadradosPrincipais();

    private Movimento() {
    }

    public void setJogadores(Jogador[] jogs) {
        this.jogs = jogs;
    }

    private Jogador definirJogador() {
        for (Jogador jog : jogs) {
            if (jog.getCor().equals(peca.getCor())) {
                return jog;
            }
        }
        return null;
    }

    public static Movimento instanciar() {
        if (instancia == null)
            instancia = new Movimento();

        return instancia;
    }

    public void mover(Peca peca) {
        if (!peca.getJogadaDisponivel())
            return;

        int valor_dado = tabuleiro.getValorDado();
        this.peca = peca;

        if (!verificarAcertouPergunta(valor_dado)) {
            InicioJogo.setTempoEspera(500);
            peca.setJogadaFinalizada(false);
            return;
        }

        peca.setJogadaFinalizada(false);
        peca.getImagem().setViewOrder(-1);

        if (peca.getTipoPosicao().equals("base")) {
            sairDaBase();
        } else {
            InicioJogo.setTempoEspera(250 + 500 * valor_dado);

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.5f), e -> moverComPulo()));
            timeline.setCycleCount(valor_dado);
            timeline.play();
        }
    }

    private void moverImagem(float tempo, float dx, float dy, ImageView img) {
        TranslateTransition mover = new TranslateTransition(Duration.seconds(tempo), img);
        mover.setByX(dx);
        mover.setByY(dy);
        mover.setCycleCount(1);
        mover.play();
    }

    private void moverBotao(float tempo, float dx, float dy, Button btn) {
        TranslateTransition mover = new TranslateTransition(Duration.seconds(tempo), btn);
        mover.setByX(dx);
        mover.setByY(dy);
        mover.setCycleCount(1);
        mover.play();
    }

    private void sairDaBase() {
        int pos_inicial = peca.getPosicaoInicial();
        float dx = x_principais[pos_inicial] - peca.getXBase();
        float dy = y_principais[pos_inicial] - peca.getYBase();
        ImageView img = peca.getImagem();
        Button btn = peca.getBotao();

        moverImagem(0.5f, dx, dy, img);
        moverBotao(0.5f, dx, dy, btn);

        InicioJogo.setTempoEspera(750);
        peca.setPosicao(pos_inicial);
        peca.setTipoPosicao("quad_principal");
    }

    private void moverComPulo() {
        int pos = peca.getPosicao();
        float dx, dy;
        String tipo_pos = peca.getTipoPosicao();
        ImageView img = peca.getImagem();
        Button btn = peca.getBotao();

        if (tipo_pos.equals("quad_final") && pos == 4) {
            PauseTransition pausa = new PauseTransition(Duration.seconds(0.5f));
            pausa.setOnFinished(e -> finalizar());
            pausa.play();

            dx = peca.getXFinal(5) - peca.getXFinal(4);
            dy = peca.getYFinal(5) - peca.getYFinal(4);
            peca.setJogarNovamente(true);
            peca.setTipoPosicao("linha_chegada");
        } else if (tipo_pos.equals("quad_final")) {
            dx = peca.getXFinal(pos + 1) - peca.getXFinal(pos);
            dy = peca.getYFinal(pos + 1) - peca.getYFinal(pos);
            peca.setPosicao(pos + 1);
        } else if (pos == peca.getPosicaoFinal()) {
            dx = peca.getXFinal(0) - x_principais[pos];
            dy = peca.getYFinal(0) - y_principais[pos];
            peca.setTipoPosicao("quad_final");
            peca.setPosicao(0);
        } else {
            dx = x_principais[(pos + 1) % 52] - x_principais[pos];
            dy = y_principais[(pos + 1) % 52] - y_principais[pos];
            peca.setPosicao((pos + 1) % 52);
        }

        moverImagem(0.25f, dx, dy, img);
        moverBotao(0.25f, dx, dy, btn);
        animarPeca(peca.getImagem());
    }

    private void animarPeca(ImageView img) {
        ScaleTransition expandir = new ScaleTransition(Duration.seconds(0.125), img);
        expandir.setToX(1.5f);
        expandir.setToY(1.5f);

        ScaleTransition reduzir = new ScaleTransition(Duration.seconds(0.125), img);
        reduzir.setToX(1.0f);
        reduzir.setToY(1.0f);

        expandir.setOnFinished(event -> reduzir.play());
        expandir.play();
    }

    private void finalizar() {
        Jogador jog = definirJogador();
        Objects.requireNonNull(jog).mostrarImagemChegada();
        peca.getImagem().setOpacity(0f);
    }

    private boolean verificarAcertouPergunta(int valor_dado) {
        int pos = peca.getPosicao();
        String tipo_pos = peca.getTipoPosicao();

        if (!(tipo_pos.equals("base") || tipo_pos.equals("quad_final") && valor_dado + pos == 5 ||
                tipo_pos.equals("quad_principal") && valor_dado == 6 && pos == peca.getPosicaoFinal()))
            return true;

        tela.gerarTela(peca.getCorEscura());
        return tela.getPerguntaAcertada();
    }
}
