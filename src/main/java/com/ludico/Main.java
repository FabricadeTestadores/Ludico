package com.ludico;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main {
    private static boolean modo_perguntas;
    private static float largura = 800f, altura = largura / 1.5f;
    private static Pane root = new Pane();

    public static void setModoPerguntas(boolean modo_perguntas) {
        Main.modo_perguntas = modo_perguntas;
    }

    public static boolean getModoPerguntas() {
        return modo_perguntas;
    }

    public static float getLargura() {
        return largura;
    }

    public static float getAltura() {
        return altura;
    }

    public static Pane getRoot() {
        return root;
    }

    public static void iniciarJogo(Jogador[] jogs) {
        Platform.runLater(() -> {
            Scene scene = new Scene(root, largura, altura);
            Stage stage = new Stage();
            EncontroPecas encontro = new EncontroPecas();
            InicioJogo inicio_jogo = new InicioJogo(jogs, encontro);
            Movimento.instanciar().setEncontroPecas(encontro);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

            new Thread(() -> inicio_jogo.comecarLoopPrincipal(stage)).start();
        });
    }

    public static void finalizarJogo(Jogador jog) {
        Stage stage = new Stage();
        FimJogo fim_de_jogo = new FimJogo(jog.getCor().toUpperCase(), jog.getCorClara());

        fim_de_jogo.start(stage);
    }

    public static void main(String[] args) {
        TelaInicial.main(args);
    }
}