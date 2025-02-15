package com.ludico;

public abstract class Jogador {
    protected String cor_clara = definirCorClara(), cor_escura = definirCorEscura();
    protected Peca[] pecas = gerarPecas();

    protected abstract String definirCorClara();

    protected abstract String definirCorEscura();

    protected abstract Peca[] gerarPecas();

    public boolean verificarJogadasDisponiveis() {
        for (int i = 0; i < 4; i++) {
            if (pecas[i].getJogadaDisponivel())
                return true;
        }
        return false;
    }

    public Peca getPecaEscolhida() {
        for (int i = 0; i < 4; i++) {
            if (!pecas[i].getJogadaFinalizada()) {
                pecas[i].setJogadaFinalizada(true);
                return pecas[i];
            }
        }
        return null;
    }

    public void ativarBotoes(boolean ativar) {
        for (int i = 0; i < 4; i++)
            pecas[i].ativarBotao(ativar);
    }

    public String getCorClara() {
        return cor_clara;
    }

    public String getCorEscura() {
        return cor_escura;
    }
}
