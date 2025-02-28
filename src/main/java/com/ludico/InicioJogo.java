package com.ludico;

import javafx.application.Platform;
import javafx.stage.Stage;

public class InicioJogo {
    private Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private EncontroPecas encontro;
    private static Stage stage;
    private static int tempo_espera = 500;
    private int indice, qtd_jogs;
    private Peca peca;
    private Jogador jog;
    private Jogador[] jogs;

    public InicioJogo(Jogador[] jogs, EncontroPecas encontro) {
        this.jogs = jogs;
        this.encontro = encontro;
        qtd_jogs = jogs.length;
        indice = qtd_jogs - 1;
    }

    public static void setTempoEspera(int tempo_espera) {
        InicioJogo.tempo_espera = tempo_espera;
    }

    public void comecarLoopPrincipal(Stage stage) {
        InicioJogo.stage = stage;

        while (true) {
            indice = (indice + 1) % qtd_jogs;
            jog = jogs[indice];

            tabuleiro.ativarBotaoDado(jog.getCorClara());

            while (tabuleiro.getBotaoAtivado()) {
                verificarFinalizarPrograma();
                esperar(100);
            }

            if (!jog.verificarJogadasDisponiveis()) {
                esperar(500);
                continue;
            }

            jog.ativarBotoes(true);
            peca = getPecaEscolhida();
            jog.ativarBotoes(false);
            esperar(tempo_espera);
            encontro.verificarAtaque(jog, peca);
            peca.getImagem().setViewOrder(0f);

            if (jog.verificarGanhou())
                break;

            verificarJogarNovamente(tabuleiro.getValorDado());
        }
        esperar(500);

        Platform.runLater(() -> {
            stage.close();
            Main.finalizarJogo(jog);
        });
    }

    private void esperar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void verificarJogarNovamente(int valor_dado) {
        if (valor_dado == 6 || peca.getJogarNovamente()) {
            peca.setJogarNovamente(false);
            --indice;
        }
    }

    private Peca getPecaEscolhida() {
        Peca peca;

        while ((peca = jog.getPecaEscolhida()) == null) {
            verificarFinalizarPrograma();
            esperar(100);
        }

        return peca;
    }

    private void verificarFinalizarPrograma() {
        if (!stage.isShowing())
            System.exit(1);
    }
}
