package com.ludico;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends Application {
    private int qtd_jogs;
    private float largura = Main.getLargura(), altura = Main.getAltura();
    private String cor_inicial;
    private String[] topicos;

    public void start(Stage stage) {
        String[] topicos = {"POO", "Banco de Dados", "Programação Básica",
                "Criação de Jogos", "Testes de Software", "Organização de Projetos"};

        List<HBox> checkboxContainers = new ArrayList<>();
        for (String topico : topicos) {
            CheckBox checkBox = new CheckBox();
            Label label = new Label(topico);
            label.setStyle("-fx-font-size: 14px;");
            label.setMinWidth(200);
            label.setMaxWidth(Double.MAX_VALUE);

            HBox container = new HBox(10, checkBox, label);
            container.setAlignment(Pos.CENTER);
            checkboxContainers.add(container);
        }

        Button confirmar = new Button("Confirmar");
        confirmar.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        confirmar.setOnAction(e -> {
            boolean peloMenosUmSelecionado = checkboxContainers.stream()
                    .map(hbox -> (CheckBox) hbox.getChildren().get(0))
                    .anyMatch(CheckBox::isSelected);
            if (!peloMenosUmSelecionado) {
                new Alert(Alert.AlertType.WARNING, "Selecione pelo menos um tópico!", ButtonType.OK).showAndWait();
            } else {
                this.topicos = checkboxContainers.stream()
                        .filter(hbox -> ((CheckBox) hbox.getChildren().get(0)).isSelected())
                        .map(hbox -> ((Label) hbox.getChildren().get(1)).getText())
                        .toList().toArray(new String[0]);

                stage.close();
                escolherQtdJogadores();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(checkboxContainers);
        layout.getChildren().add(confirmar);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, largura, altura);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Escolha os Tópicos:");
        stage.show();
    }

    private void escolherQtdJogadores() {
        Stage stage = new Stage();
        Button doisJogadores = new Button("2 Jogadores");
        Button quatroJogadores = new Button("4 Jogadores");

        doisJogadores.setOnAction(e -> {
            qtd_jogs = 2;
            stage.close();
            escolherCorInicial();
        });

        quatroJogadores.setOnAction(e -> {
            qtd_jogs = 4;
            stage.close();
            escolherCorInicial();
        });

        VBox layout = new VBox(largura / 30f);
        layout.getChildren().addAll(doisJogadores, quatroJogadores);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(layout, largura, altura);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Escolha a Quantidade de Jogadores");
        stage.setResizable(false);
        stage.show();
    }

    private void escolherCorInicial() {
        String[] cores = {"Vermelho", "Verde", "Amarelo", "Azul"};
        Stage stage = new Stage();
        ComboBox<String> comboBoxCores = new ComboBox<>();
        comboBoxCores.getItems().addAll(cores);
        comboBoxCores.setValue("Vermelho");

        Button confirmar = new Button("Confirmar");
        confirmar.setStyle("-fx-background-color: green; -fx-text-fill: white;");

        confirmar.setOnAction(e -> {
            cor_inicial = comboBoxCores.getValue().toLowerCase();
            stage.close();
            Main.iniciarJogo(qtd_jogs, cor_inicial, topicos);
        });

        VBox layout = new VBox(largura / 30f);
        layout.getChildren().addAll(new Label("Escolha a cor do primeiro jogador:"), comboBoxCores, confirmar);
        layout.setStyle("-fx-padding: 20;");
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(layout, largura, altura);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Escolha a Cor do Primeiro Jogador");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
