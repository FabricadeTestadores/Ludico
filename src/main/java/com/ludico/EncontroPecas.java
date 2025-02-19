package com.ludico;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.Objects;

public class EncontroPecas {
    private Peca peca_atacada;
    private Peca[] pecas;
    private Peca[][] quad_principais = new Peca[52][16];
    private int prox_pos, total_pecas, qtd_pecas_amigas, qtd_pecas_inimigas;
    private boolean safe_zone = true;
    private boolean[] bloqueios = new boolean[52];

    public EncontroPecas() {
        for (int i = 0; i < 52; i++)
            bloqueios[i] = false;
    }

    public int analisarEncontro(Jogador jog, Peca peca, int valor_dado) {
        int pos = peca.getPosicao(), pos_final = peca.getPosicaoFinal();
        Peca[][] quad_finais = jog.getQuadradosOcupados();
        safe_zone = true;

        if (peca.getTipoPosicao().equals("quad_principal")) {
            limparPeca(quad_principais[pos], peca);
            ordenarPecas(quad_principais[pos]);
            contarPecas(quad_principais[pos], peca);
            valor_dado = verificarBloqueio(valor_dado, pos, pos_final);
            prox_pos = (pos + valor_dado) % 52;
            pecas = verificarTipoQuadrado(jog, valor_dado, pos, pos_final);
        } else {
            limparPeca(quad_finais[pos], peca);
            ordenarPecas(quad_finais[pos]);
            contarPecas(quad_finais[pos], peca);
            prox_pos = pos + valor_dado;
            pecas = quad_finais[prox_pos];
        }

        contarPecas(pecas, peca);

        if (!safe_zone && qtd_pecas_inimigas == 1)
            InicioJogo.setPecaAtacada(peca_atacada);

        return valor_dado;
    }

    private boolean analisarSafeZone(int prox_pos) {
        int[] casas_seguras = {2, 10, 15, 23, 28, 36, 41, 49};

        for (int i = 0; i < 8; i++) {
            if (prox_pos == casas_seguras[i])
                return true;
        }
        return false;
    }

    private void limparPeca(Peca[] pecas, Peca peca) {
        for (int i = 0; i < pecas.length; i++) {
            if (pecas[i] == peca) {
                pecas[i] = null;
                break;
            }
        }
    }

    private int verificarBloqueio(int valor_dado, int pos, int pos_final) {
        for (int i = 1; i <= 6; i++) {
            if (bloqueios[(pos + i) % 52])
                return i - 1;
            else if ((pos + i) % 52 == pos_final)
                return valor_dado;
        }
        return valor_dado;
    }

    private Peca[] verificarTipoQuadrado(Jogador jog, int valor_dado, int pos, int pos_final) {
        for (int i = 1; i <= valor_dado; i++) {
            if ((pos + i) % 52 == pos_final && i < valor_dado) {
                prox_pos = valor_dado - i - 1;
                return jog.getQuadradosOcupados()[prox_pos];
            }
        }
        safe_zone = analisarSafeZone(prox_pos);
        return quad_principais[prox_pos];
    }

    private void contarPecas(Peca[] pecas, Peca peca) {
        qtd_pecas_amigas = 0;
        qtd_pecas_inimigas = 0;

        for (Peca p : pecas) {
            if (p == null) {
                break;
            } else if (p.getCor().equals(peca.getCor())) {
                ++qtd_pecas_amigas;
            } else {
                ++qtd_pecas_inimigas;
                peca_atacada = p;
            }
        }
        total_pecas = qtd_pecas_amigas + qtd_pecas_inimigas;
    }

    private void ordenarPecas(Peca[] pecas) {
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

    private void ajustarImagem(Peca[] pecas) {
        int qtd_vermelho = 0, qtd_verde = 0, qtd_amarelo = 0, qtd_azul = 0;
        boolean[] registro = {false, false, false, false};

        if (total_pecas == 0) {
            return;
        } else if (total_pecas == 1) {
            pecas[0].ajeitarImagem();
            return;
        }

        for (int i = 0; i < total_pecas; i++) {
            switch (pecas[i].getCor()) {
                case "vermelho":
                    ++qtd_vermelho;
                    break;
                case "verde":
                    ++qtd_verde;
                    break;
                case "amarelo":
                    ++qtd_amarelo;
                    break;
                default:
                    ++qtd_azul;
            }
        }

        for (int i = 0; i < total_pecas; i++) {
            switch (pecas[i].getCor()) {
                case "vermelho":
                    if (!registro[0]) {
                        registro[0] = true;
                        pecas[i].ajeitarImagem();
                        pecas[i].setImagem(trocarImagem(""));
                    } else {

                    }
                    break;
                case "verde":
                    ++qtd_verde;
                    break;
                case "amarelo":
                    ++qtd_amarelo;
                    break;
                default:
                    ++qtd_azul;
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
