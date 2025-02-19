package com.ludico;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class EncontroPecas {
    private static EncontroPecas instancia;
    private AuxiliarEncontro aux_encontro = new AuxiliarEncontro();
    private Peca peca_atacada;
    private Peca[] pecas;
    private Peca[][] quad_principais;
    private int prox_pos, total_pecas, qtd_pecas_amigas, qtd_pecas_inimigas;
    private boolean safe_zone = true;
    private boolean[] bloqueios = new boolean[52];

    private EncontroPecas() {
        for (int i = 0; i < 52; i++)
            bloqueios[i] = false;
    }

    public void criarQuadradosPrincipais(int qtd_jogs) {
        quad_principais = new Peca[52][qtd_jogs * 4];
    }

    public static EncontroPecas instanciar() {
        if (instancia == null)
            instancia = new EncontroPecas();
        return instancia;
    }

    public void encontroBase(Jogador jog, Peca peca) {
        Peca[] pecas = quad_principais[peca.getPosicaoInicial()];

        ordenarPecas(pecas);
        contarPecas(pecas, peca);
        pecas[total_pecas] = peca;
        ++total_pecas;

        PauseTransition pausa = new PauseTransition(Duration.seconds(0.5f));
        pausa.setOnFinished(e -> ajustarImagem(pecas));
        pausa.play();
    }

    public int analisarEncontro(Jogador jog, Peca peca, int valor_dado) {
        int pos = peca.getPosicao(), pos_final = peca.getPosicaoFinal();
        Peca[][] quad_finais = jog.getQuadradosOcupados();
        safe_zone = true;

        if (peca.getTipoPosicao().equals("quad_principal")) {
            limparPeca(quad_principais[pos], peca);
            ordenarPecas(quad_principais[pos]);
            contarPecas(quad_principais[pos], peca);
            ajustarImagem(quad_principais[pos]);
            valor_dado = verificarBloqueio(valor_dado, pos, pos_final);
            prox_pos = (pos + valor_dado) % 52;
            pecas = verificarTipoQuadrado(jog, valor_dado, pos, pos_final);

            if (!safe_zone)
                bloqueios[pos] = qtd_pecas_amigas > 1;
        } else {
            limparPeca(quad_finais[pos], peca);
            ordenarPecas(quad_finais[pos]);
            contarPecas(quad_finais[pos], peca);
            ajustarImagem(quad_finais[pos]);
            prox_pos = pos + valor_dado;
            pecas = quad_finais[prox_pos];
        }

        contarPecas(pecas, peca);

        if (!safe_zone && qtd_pecas_inimigas == 1)
            InicioJogo.setPecaAtacada(peca_atacada);

        return valor_dado;
    }

    private Peca[] verificarTipoQuadrado(Jogador jog, int valor_dado, int pos, int pos_final) {
        for (int i = 1; i <= valor_dado; i++) {
            if ((pos + i) % 52 == pos_final && i < valor_dado) {
                prox_pos = valor_dado - i - 1;
                return jog.getQuadradosOcupados()[prox_pos];
            }
        }
        safe_zone = analisarSafeZone();
        return quad_principais[prox_pos];
    }

    public void contarPecas(Peca[] pecas, Peca peca) {
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

    public void adicionarPeca(Peca peca) {
        pecas[total_pecas] = peca;
    }

    public void limparPeca(Peca[] pecas, Peca peca) {
        aux_encontro.limparPeca(pecas, peca);
    }

    public void ordenarPecas(Peca[] pecas) {
        aux_encontro.ordenarPecas(pecas);
    }

    public void ajustarImagem(Peca[] pecas) {
        aux_encontro.ajustarImagem(pecas, total_pecas);
    }

    private int verificarBloqueio(int valor_dado, int pos, int pos_final) {
        return aux_encontro.verificarBloqueio(valor_dado, pos, pos_final, bloqueios);
    }

    public void ativarBloqueioOuNao() {
        if (!safe_zone && pecas.length > 4)
            bloqueios[prox_pos] = qtd_pecas_amigas > 1;
    }

    private boolean analisarSafeZone() {
        return aux_encontro.analisarSafeZone(prox_pos);
    }

    public Peca[] getPecas() {
        return pecas;
    }
}
