package com.ludico;

public class Tabuleiro {
    private static Tabuleiro instancia;
    private Imagens imagens = new Imagens();
    private Quadrados quadrados = new Quadrados();
    private Circulos circulos = new Circulos();

    private Tabuleiro() {
        imagens.colocarCentro();
        imagens.colocarEstrelas(quadrados.getXEstrelas(), quadrados.getYEstrelas());
    }

    public static Tabuleiro instanciar() {
        if (instancia == null)
            instancia = new Tabuleiro();
        return instancia;
    }

    public void ativarBotaoDado(String cor) {
        imagens.ativarBotaoDado(cor);
    }

    public int getValorDado() {
        return imagens.getValorDado();
    }

    public boolean getBotaoAtivado() {
        return imagens.getBotaoAtivado();
    }

    public float[] getXQuadradosPrincipais() {
        return quadrados.getXQuadradosPrincipais();
    }

    public float[] getYQuadradosPrincipais() {
        return quadrados.getYQuadradosPrincipais();
    }

    public float[] getXQuadradosFinais(int i) {
        return quadrados.getXQuadradosFinais(i);
    }

    public float[] getYQuadradosFinais(int i) {
        return quadrados.getYQuadradosFinais(i);
    }

    public float[] getXCirculosPequenos(int i) {
        return circulos.getXCirculosPequenos(i);
    }

    public float[] getYCirculosPequenos(int i) {
        return circulos.getYCirculosPequenos(i);
    }
}
