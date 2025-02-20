package com.ludico;

import java.util.ArrayList;

public class EncontroPecas {
    @SuppressWarnings("unchecked")
    protected ArrayList<Peca>[] casas_principais = new ArrayList[52];
    private ArrayList<Peca> casa;
    private static Movimento mov = Movimento.instanciar();
    private int qtd_pecas_amigas, qtd_pecas_inimigas;
    private int[] casas_seguras = {2, 10, 15, 23, 28, 36, 41, 49};

    public EncontroPecas() {
        for (int i = 0; i < 52; i++)
            casas_principais[i] = new ArrayList<>();
    }

    private void adicionarPeca(Peca peca) {
        casa.add(peca);
        ajustarImagem();
    }

    public void removerPeca(Jogador jog, Peca peca) {
        definirCasa(jog, peca);
        peca.trocarImagem(true, 0);
        casa.removeIf(p -> p == peca);
        ajustarImagem();
    }

    private void removerPeca(Peca peca) {
        peca.trocarImagem(true, 0);
        casa.removeIf(p -> p == peca);
    }

    private void definirCasa(Jogador jog, Peca peca) {
        if (peca.getTipoPosicao().equals("quad_final"))
            casa = jog.getCasa(peca.getPosicao());
        else
            casa = casas_principais[peca.getPosicao()];
    }

    public void verificarAtaque(Jogador jog, Peca peca) {
        if (peca.getTipoPosicao().equals("base"))
            return;

        definirCasa(jog, peca);
        contarPecas(peca);

        if (!verificarSafeZone(peca) && qtd_pecas_inimigas == 1 && qtd_pecas_amigas == 0)
            comecarAtaque(peca, casa.getFirst());
        else
            adicionarPeca(peca);
    }

    private boolean verificarSafeZone(Peca peca) {
        if (peca.getTipoPosicao().equals("quad_final"))
            return true;

        for (int i = 0; i < 8; i++) {
            if (peca.getPosicao() == casas_seguras[i])
                return true;
        }

        return false;
    }

    private void contarPecas(Peca peca) {
        qtd_pecas_amigas = 0;
        qtd_pecas_inimigas = 0;

        for (Peca p : casa) {
            if (p.getCor().equals(peca.getCor()))
                ++qtd_pecas_amigas;
            else
                ++qtd_pecas_inimigas;
        }
    }

    private void comecarAtaque(Peca peca, Peca peca_atacada) {
        peca.setJogarNovamente(true);
        removerPeca(peca_atacada);
        adicionarPeca(peca);
        mov.moverSemPulo(peca_atacada);
    }

    private void ajustarImagem() {
        int[] num_pecas = {0, 0, 0, 0};

        if (casa.size() == 1) {
            casa.getFirst().trocarImagem(true, 0);
            return;
        }

        for (Peca peca : casa) {
            switch (peca.getCor()) {
                case "vermelho":
                    peca.trocarImagem(false, num_pecas[0]);
                    ++num_pecas[0];
                    break;
                case "verde":
                    peca.trocarImagem(false, num_pecas[1]);
                    ++num_pecas[1];
                    break;
                case "amarelo":
                    peca.trocarImagem(false, num_pecas[2]);
                    ++num_pecas[2];
                    break;
                default:
                    peca.trocarImagem(false, num_pecas[3]);
                    ++num_pecas[3];
            }
        }
    }
}
