package com.ludico;

import javafx.stage.Stage;

public class InicioJogo {
    private Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private int indice, qtd_jogs;
    private TelaPerguntas tela;
    private Peca peca;
    private Jogador jog;
    private Jogador[] jogs;

    public InicioJogo(TelaPerguntas tela, Jogador[] jogs) {
        this.tela = tela;
        this.jogs = jogs;

        qtd_jogs = jogs.length;
        indice = qtd_jogs - 1;
    }

    public void comecarLoopPrincipal(Stage stage) {
        while (true) {
            indice = (indice + 1) % qtd_jogs;
            jog = jogs[indice];

            tabuleiro.ativarBotaoDado(jog.getCorClara());

            while (tabuleiro.getBotaoAtivado())
                esperar(100);

            if (!jog.verificarJogadasDisponiveis()) {
                esperar(500);
                continue;
            }

            jog.ativarBotoes(true);
            peca = getPecaEscolhida();
            jog.ativarBotoes(false);

            esperar(peca.getTempoEspera());

            if (jog.verificarGanhou())
                break;

            verificarJogarNovamente(tabuleiro.getValorDado());
        }

        stage.close();
        Main.finalizarJogo(jog);
    }

    private void esperar(int time) {
        try {
            Thread.sleep(time);
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

        while ((peca = jog.getPecaEscolhida()) == null)
            esperar(100);

        return peca;
    }
}
