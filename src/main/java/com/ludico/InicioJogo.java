package com.ludico;

import javafx.application.Platform;
import javafx.stage.Stage;

public class InicioJogo {
    private Tabuleiro tabuleiro = Tabuleiro.instanciar();
    private TelaPerguntas tela = TelaPerguntas.instanciar(null);
    private EncontroPecas encontro = EncontroPecas.instanciar();
    private Movimento mov = Movimento.instanciar();
    private static Peca peca_atacada = null;
    private static int tempo_espera = 500;
    private int indice, qtd_jogs;
    private Peca peca;
    private Jogador jog;
    private Jogador[] jogs;

    public InicioJogo(Jogador[] jogs) {
        this.jogs = jogs;
        qtd_jogs = jogs.length;
        indice = qtd_jogs - 1;
        encontro.criarQuadradosPrincipais(qtd_jogs);
    }

    public static void setPecaAtacada(Peca peca_atacada) {
        InicioJogo.peca_atacada = peca_atacada;
    }

    public static void setTempoEspera(int tempo_espera) {
        InicioJogo.tempo_espera = tempo_espera;
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
            esperar(tempo_espera);
            /*tempo_espera = 0;
            Peca[] pecas = encontro.getPecas();

            if (!peca.getTipoPosicao().equals("base") && pecas != null) {
                if (peca_atacada != null) {
                    tela.gerarTela(peca.getCorEscura());

                    if (tela.getPerguntaAcertada()) {
                        encontro.adicionarPeca(peca);
                        encontro.limparPeca(pecas, peca_atacada);
                        encontro.ordenarPecas(pecas);
                        encontro.contarPecas(pecas, peca);
                        encontro.ajustarImagem(pecas);
                    } else {
                        peca_atacada = peca;
                    }

                    mov.moverSemPulo(peca_atacada);
                    peca_atacada = null;
                } else {
                    encontro.adicionarPeca(peca);
                    encontro.contarPecas(pecas, peca);
                    encontro.ajustarImagem(pecas);
                    encontro.ativarBloqueioOuNao();
                }
            }

            esperar(tempo_espera);*/
            peca.getImagem().setViewOrder(0f);

            if (jog.verificarGanhou())
                break;

            verificarJogarNovamente(tabuleiro.getValorDado());
        }
        esperar(750);

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

        while ((peca = jog.getPecaEscolhida()) == null)
            esperar(100);

        return peca;
    }
}
