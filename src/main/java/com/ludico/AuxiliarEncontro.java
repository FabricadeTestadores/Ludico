package com.ludico;

import javafx.scene.image.Image;

import java.util.Objects;

public class AuxiliarEncontro {

    public boolean analisarSafeZone(int prox_pos) {
        int[] casas_seguras = {2, 10, 15, 23, 28, 36, 41, 49};

        for (int i = 0; i < 8; i++) {
            if (prox_pos == casas_seguras[i])
                return true;
        }
        return false;
    }

    public void limparPeca(Peca[] pecas, Peca peca) {
        for (int i = 0; i < pecas.length; i++) {
            if (pecas[i] == peca) {
                pecas[i] = null;
                break;
            }
        }
    }

    public int verificarBloqueio(int valor_dado, int pos, int pos_final, boolean[] bloqueios) {
        for (int i = 1; i <= 6; i++) {
            if (bloqueios[(pos + i) % 52])
                return i - 1;
            else if ((pos + i) % 52 == pos_final)
                return valor_dado;
        }
        return valor_dado;
    }

    public void ordenarPecas(Peca[] pecas) {
        boolean ordenado;

        do {
            ordenado = false;

            for (int i = 0; i < pecas.length - 1; i++) {
                if (pecas[i] == null && pecas[i + 1] != null) {
                    Peca copia = pecas[i];
                    pecas[i] = pecas[i + 1];
                    pecas[i + 1] = copia;
                    ordenado = true;
                }
            }
        } while (ordenado);
    }

    public void ajustarImagem(Peca[] pecas, int total_pecas) {
        int[] num_pecas = {0, 0, 0, 0};

        if (total_pecas == 0) {
            return;
        } else if (total_pecas == 1) {
            pecas[0].ajeitarImagem();
            return;
        }

        for (int i = 0; i < total_pecas; i++) {
            switch (pecas[i].getCor()) {
                case "vermelho":
                    ++num_pecas[0];
                    break;
                case "verde":
                    ++num_pecas[1];
                    break;
                case "amarelo":
                    ++num_pecas[2];
                    break;
                default:
                    ++num_pecas[3];
            }
        }

        for (int i = 0; i < total_pecas; i++) {
            switch (pecas[i].getCor()) {
                case "vermelho":
                    pecas[i].setImagem(trocarImagem("/imagens/final_vermelho" + num_pecas[0] + ".png"));
                    break;
                case "verde":
                    pecas[i].setImagem(trocarImagem("/imagens/final_verde" + num_pecas[1] + ".png"));
                    break;
                case "amarelo":
                    pecas[i].setImagem(trocarImagem("/imagens/final_amarelo" + num_pecas[2] + ".png"));
                    break;
                default:
                    pecas[i].setImagem(trocarImagem("/imagens/final_azul" + num_pecas[3] + ".png"));
            }
        }
    }

    private Image trocarImagem(String caminho) {
        Image img = null;

        try {
            img = new Image(Objects.requireNonNull(getClass().getResource(caminho)).toExternalForm());
        } catch (NullPointerException e) {
            System.err.println("Erro: Imagem não encontrada no classpath.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }

        return img;
    }
}
