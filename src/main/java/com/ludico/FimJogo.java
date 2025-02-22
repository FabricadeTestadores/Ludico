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
    private String cor, cor_hexadecimal;

    public FimJogo(String cor, String corHexadecimal) {
        this.cor = cor;
        this.cor_hexadecimal = corHexadecimal;
    }

    public void start(Stage primaryStage) {
        Text mensagem = new Text("JOGADOR " + cor + " VENCEU!");
        mensagem.setFont(new Font(22f));
        mensagem.setFill(Color.web(cor_hexadecimal));
        mensagem.setEffect(new DropShadow(10, Color.BLACK));

        Rectangle fundo = new Rectangle(400, 300, Color.BLACK);
        fundo.setOpacity(0.7);

        StackPane root = new StackPane(fundo, mensagem);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
