package com.ludico;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main {
    private static float largura = 720f, altura = largura / 1.5f;
    private static Pane root = new Pane();

    public static float getLargura() {
        return largura;
    }

    public static float getAltura() {
        return altura;
    }

    public static Pane getRoot() {
        return root;
    }

    public static void iniciarJogo(int qtd_jogs, String cor_inicial, String[] topicos) {
        Jogador[] jogs;
        Configuracoes conf = new Configuracoes();
        Scene scene = new Scene(root, largura, altura);
        Stage stage = new Stage();

        topicos = conf.organizarTopicos(topicos);
        jogs = conf.gerarJogadores(qtd_jogs, cor_inicial);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        TelaInicial.main(args);
    }
}