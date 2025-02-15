package com.ludico;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Quadrados {
    private float largura = Main.getLargura(), altura = Main.getAltura(), tam_quad = largura / 24f;
    private float[] x_estrelas = new float[4], y_estrelas = new float[4];
    private float[] x_quad_principais = new float[52], y_quad_principais = new float[52];
    private float[][] x_quad_finais = new float[4][6], y_quad_finais = new float[4][6];
    private Pane root = Main.getRoot();
    private ArrayList<Float> x_quad = new ArrayList<>(), y_quad = new ArrayList<>();

    public Quadrados() {
        gerarQuadradosPrincipais();
        gerarRetasFinais();
        ajustarPosicaoEstrelas();
    }

    private void ajustarQuadradosPrincipais(int max, float x, float y, float j, float k) {
        for (int i = 0; i < max; i++) {
            Rectangle quad = new Rectangle(x + j * i * tam_quad, y + k * i * tam_quad, tam_quad, tam_quad);
            quad.setFill(Color.WHITE);
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2f);
            root.getChildren().add(quad);
            x_quad.add(x + j * i * tam_quad);
            y_quad.add(y + k * i * tam_quad);

            switch (x_quad.size()) {
                case 3:
                    quad.setFill(Color.web("#8C0000"));
                    break;
                case 16:
                    quad.setFill(Color.web("#008C00"));
                    break;
                case 29:
                    quad.setFill(Color.web("#B8860B"));
                    break;
                case 42:
                    quad.setFill(Color.web("#00008C"));
            }
        }
    }

    private void ajustarRetasFinais(String cor, int indice, float x, float y, float j, float k) {
        for (int i = 0; i < 6; i++) {
            Rectangle quad = new Rectangle(x + j * i * tam_quad, y + k * i * tam_quad, tam_quad, tam_quad);
            quad.setFill(Color.web(cor));
            quad.setStroke(Color.BLACK);
            quad.setStrokeWidth(2f);
            root.getChildren().add(quad);
            x_quad_finais[indice][i] = x + j * i * tam_quad;
            y_quad_finais[indice][i] = y + k * i * tam_quad;
        }
    }

    private void gerarQuadradosPrincipais() {
        float x = largura * (23f / 48f), y = altura * 0.90625f;

        ajustarQuadradosPrincipais(1, x, y, 0f, 0f);
        x -= tam_quad;
        ajustarQuadradosPrincipais(6, x, y, 0f, -1f);
        x -= tam_quad;
        y -= tam_quad * 6f;
        ajustarQuadradosPrincipais(6, x, y, -1f, 0f);
        x -= tam_quad * 5f;
        y -= tam_quad;
        ajustarQuadradosPrincipais(2, x, y, 0f, -1f);
        x += tam_quad;
        y -= tam_quad;
        ajustarQuadradosPrincipais(5, x, y, 1f, 0f);
        x += tam_quad * 5f;
        y -= tam_quad;
        ajustarQuadradosPrincipais(6, x, y, 0f, -1f);
        x += tam_quad;
        y -= tam_quad * 5f;
        ajustarQuadradosPrincipais(2, x, y, 1f, 0f);
        x += tam_quad;
        y += tam_quad;
        ajustarQuadradosPrincipais(5, x, y, 0f, 1f);
        x += tam_quad;
        y += tam_quad * 5f;
        ajustarQuadradosPrincipais(6, x, y, 1f, 0f);
        x += tam_quad * 5f;
        y += tam_quad;
        ajustarQuadradosPrincipais(2, x, y, 0f, 1f);
        y += tam_quad;
        x -= tam_quad;
        ajustarQuadradosPrincipais(5, x, y, -1f, 0f);
        x -= tam_quad * 5f;
        y += tam_quad;
        ajustarQuadradosPrincipais(6, x, y, 0f, 1f);

        for (int i = 0; i < 52; i++) {
            x_quad_principais[i] = x_quad.get(i);
            y_quad_principais[i] = y_quad.get(i);
        }
    }

    private void gerarRetasFinais() {
        float x = largura * (23f / 48f), y = altura * 0.84375f;

        ajustarRetasFinais("#8C0000", 0, x, y, 0f, -1f);
        x -= (int) (largura / 4f);
        y -= (int) (altura * 0.375f);
        ajustarRetasFinais("#008C00", 1, x, y, 1f, 0f);
        x += (int) (largura / 4f);
        y -= (int) (altura * 0.375f);
        ajustarRetasFinais("#B8860B", 2, x, y, 0f, 1f);
        x += (int) (largura / 4f);
        y += (int) (altura * 0.375f);
        ajustarRetasFinais("#00008C", 3, x, y, -1f, 0f);
    }

    private void ajustarPosicaoEstrelas() {
        for (int i = 0; i < 4; i++) {
            x_estrelas[i] = x_quad_principais[49 - 13 * i];
            y_estrelas[i] = y_quad_principais[49 - 13 * i];
        }
    }

    public float[] getXQuadradosPrincipais() {
        return x_quad_principais;
    }

    public float[] getYQuadradosPrincipais() {
        return y_quad_principais;
    }

    public float[] getXQuadradosFinais(int i) {
        return x_quad_finais[i];
    }

    public float[] getYQuadradosFinais(int i) {
        return y_quad_finais[i];
    }

    public float[] getXEstrelas() {
        return x_estrelas;
    }

    public float[] getYEstrelas() {
        return y_estrelas;
    }
}
