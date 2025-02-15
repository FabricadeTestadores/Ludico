package com.ludico;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Circulos {
    private float largura = Main.getLargura(), altura = Main.getAltura();
    private Pane root = Main.getRoot();
    private float[][] x_circ_pequenos = new float[4][4], y_circ_pequenos = new float[4][4];

    public Circulos() {
        colocarCirculosGrandes();
    }

    private void colocarCirculosGrandes() {
        float x = largura * 0.3125f, y = altura * (25f / 32f), raio = largura * (5f / 48f);
        String cor;
        Circle circulo;

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    cor = "#8C0000";
                    break;
                case 1:
                    cor = "#008C00";
                    y -= largura * 0.375f;
                    break;
                case 2:
                    cor = "#B8860B";
                    x += largura * 0.375f;
                    break;
                default:
                    cor = "#00008C";
                    y += largura * 0.375f;
            }

            circulo = new Circle(x, y, raio);
            circulo.setFill(Color.web(cor));
            circulo.setStroke(Color.WHITE);
            circulo.setStrokeWidth(4f);

            root.getChildren().add(circulo);
            colocarCirculosPequenos(i, x, y, raio);
        }
    }

    private void colocarCirculosPequenos(int i, float x, float y, float raio) {
        float baseRaio = raio / 4f, offset = raio / 2.5f;

        Circle circulo1 = new Circle(x - offset, y - offset, baseRaio);
        circulo1.setFill(Color.TRANSPARENT);
        circulo1.setStroke(Color.WHITE);
        circulo1.setStrokeWidth(3);
        x_circ_pequenos[i][0] = x - offset - largura / 48f;
        y_circ_pequenos[i][0] = y - offset - largura / 48f;

        Circle circulo2 = new Circle(x + offset, y - offset, baseRaio);
        circulo2.setFill(Color.TRANSPARENT);
        circulo2.setStroke(Color.WHITE);
        circulo2.setStrokeWidth(3);
        x_circ_pequenos[i][1] = x + offset - largura / 48f;
        y_circ_pequenos[i][1] = y - offset - largura / 48f;

        Circle circulo3 = new Circle(x - offset, y + offset, baseRaio);
        circulo3.setFill(Color.TRANSPARENT);
        circulo3.setStroke(Color.WHITE);
        circulo3.setStrokeWidth(3);
        x_circ_pequenos[i][2] = x - offset - largura / 48f;
        y_circ_pequenos[i][2] = y + offset - largura / 48f;

        Circle circulo4 = new Circle(x + offset, y + offset, baseRaio);
        circulo4.setFill(Color.TRANSPARENT);
        circulo4.setStroke(Color.WHITE);
        circulo4.setStrokeWidth(3);
        x_circ_pequenos[i][3] = x + offset - largura / 48f;
        y_circ_pequenos[i][3] = y + offset - largura / 48f;

        root.getChildren().addAll(circulo1, circulo2, circulo3, circulo4);
    }

    public float[] getXCirculosPequenos(int i) {
        return x_circ_pequenos[i];
    }

    public float[] getYCirculosPequenos(int i) {
        return y_circ_pequenos[i];
    }
}
