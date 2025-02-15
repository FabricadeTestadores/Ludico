package com.ludico;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Imagens {
    private float largura = Main.getLargura(), altura = Main.getAltura();
    private Pane root = Main.getRoot();

    public Imagens() {
        colocarPapelParede();
    }

    private void colocarPapelParede() {
        try {
            Image img = new Image(getClass().getResource("/imagens/madeira.jpg").toExternalForm());
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(largura);
            imgView.setFitHeight(altura);
            root.getChildren().add(imgView);
        } catch (NullPointerException e) {
            System.err.println("Erro: Imagem 'madeira.jpg' não encontrada no classpath.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }
    }

    public void colocarCentro() {
        try {
            float x = largura * 0.4375f - 1f, y = altura * 0.40625f - 1f;
            Rectangle borda = new Rectangle();
            Image img = new Image(getClass().getResource("/imagens/centro.png").toExternalForm());
            ImageView imgView = new ImageView(img);

            borda.setWidth(largura / 8f);
            borda.setHeight(largura / 8f);
            borda.setLayoutX(x);
            borda.setLayoutY(y);
            borda.setStroke(Color.BLACK);

            x += 2f;
            y += 2f;

            imgView.setFitWidth(largura / 8f - 2f);
            imgView.setFitHeight(largura / 8f - 2f);
            imgView.setLayoutX(x);
            imgView.setLayoutY(y);

            root.getChildren().addAll(borda, imgView);
        } catch (NullPointerException e) {
            System.err.println("Erro: Imagem 'centro.png' não encontrada no classpath.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }
    }

    public void colocarEstrelas(float[] x_estrelas, float[] y_estrelas) {
        for (int i = 0; i < 4; i++) {
            try {
                Image img = new Image(getClass().getResource("/imagens/estrela.png").toExternalForm());

                ImageView imgView = new ImageView(img);
                imgView.setFitWidth(largura / 24f);
                imgView.setFitHeight(largura / 24f);
                imgView.setLayoutX(x_estrelas[i]);
                imgView.setLayoutY(y_estrelas[i]);

                root.getChildren().add(imgView);
            } catch (NullPointerException e) {
                System.err.println("Erro: Imagem 'estrela.png' não encontrada no classpath.");
            } catch (Exception e) {
                System.err.println("Erro ao carregar a imagem: " + e.getMessage());
            }
        }
    }
}
