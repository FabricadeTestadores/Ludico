package com.ludico;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FimJogo extends Application {
    private float largura = Main.getLargura(), altura = Main.getAltura();
    private String cor, corHexadecimal;

    public FimJogo(String cor, String corHexadecimal) {
        this.cor = cor;
        this.corHexadecimal = corHexadecimal;
    }

    public void start(Stage primaryStage) {
        Text mensagem = new Text("JOGADOR " + cor + " VENCEU!");
        mensagem.setFont(new Font(largura / 18f));
        mensagem.setFill(Color.web(corHexadecimal));
        mensagem.setEffect(new DropShadow(10, Color.BLACK));

        Rectangle fundo = new Rectangle(largura, altura, Color.BLACK);
        fundo.setOpacity(0.7);

        StackPane root = new StackPane(fundo, mensagem);
        Scene scene = new Scene(root, largura, altura);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
