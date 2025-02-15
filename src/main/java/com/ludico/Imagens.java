package com.ludico;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Imagens {
    private int valor_dado;
    private float largura = Main.getLargura(), altura = Main.getAltura();
    private Pane root = Main.getRoot();
    private ImageView img_dado;
    private Rectangle fundo_dado = new Rectangle();
    private Button btn_dado = new Button();
    private Image[] imgs_dados = new Image[6];

    public Imagens() {
        colocarPapelParede();
        colocarDado();
    }

    private void girarDado() {
        valor_dado = (int) (Math.random() * 6 + 1);
        img_dado.setImage(imgs_dados[valor_dado - 1]);
        btn_dado.setOpacity(0.5f);
        btn_dado.setDisable(true);
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

    private void colocarDado() {
        try {
            for (int i = 0; i < 6; i++)
                imgs_dados[i] = new Image(Objects.requireNonNull(getClass().getResource("/imagens/dado" + (i + 1) + ".png"))
                        .toExternalForm());

            img_dado = new ImageView(imgs_dados[5]);
            img_dado.setFitWidth(largura / 16f);
            img_dado.setFitHeight(largura / 16f);
            img_dado.setLayoutX(largura / 16f);
            img_dado.setLayoutY(altura * (29f / 64f));

            fundo_dado.setWidth(largura / 16f);
            fundo_dado.setHeight(largura / 16f);
            fundo_dado.setStroke(Color.BLACK);
            fundo_dado.setStrokeWidth(largura / 240f);
            fundo_dado.setLayoutX(largura / 16f);
            fundo_dado.setLayoutY(altura * (29f / 64f));

            btn_dado.setPrefSize(largura / 16f, largura / 16f);
            btn_dado.setLayoutX(largura / 16f);
            btn_dado.setLayoutY(altura * (29f / 64f));
            btn_dado.setOpacity(0f);
            btn_dado.setOnAction(e -> girarDado());

            root.getChildren().addAll(fundo_dado, img_dado, btn_dado);
        } catch (NullPointerException e) {
            System.err.println("Erro: Imagem 'dadox.png' não encontrada no classpath.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }
    }

    public void ativarBotaoDado(String cor) {
        btn_dado.setDisable(false);
        btn_dado.setOpacity(0f);
        fundo_dado.setFill(Color.web(cor));
    }

    public int getValorDado() {
        return valor_dado;
    }

    public boolean getBotaoAtivado() {
        return !btn_dado.isDisable();
    }
}
