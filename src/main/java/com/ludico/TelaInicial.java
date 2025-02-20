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
    private Jogador[] jogs;

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

                organizarTopicos();
                TelaPerguntas.instanciar(this.topicos);
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
            gerarJogadores();
            stage.close();
            Main.iniciarJogo(jogs);
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

    private void organizarTopicos() {
        for (int i = 0; i < topicos.length; i++) {
            switch (topicos[i]) {
                case "POO":
                    topicos[i] = "poo";
                    break;
                case "Banco de Dados":
                    topicos[i] = "banco_dados";
                    break;
                case "Programação Básica":
                    topicos[i] = "programacao_basica";
                    break;
                case "Criação de Jogos":
                    topicos[i] = "criacao_jogos";
                    break;
                case "Testes de Software":
                    topicos[i] = "testes_software";
                    break;
                default:
                    topicos[i] = "organizacao_projetos";
            }
        }
    }

    private void gerarJogadores() {
        String[] cores = gerarCores();
        jogs = new Jogador[qtd_jogs];

        for (int i = 0; i < qtd_jogs; i++) {
            switch (cores[i]) {
                case "vermelho":
                    jogs[i] = new JogadorVermelho();
                    break;
                case "verde":
                    jogs[i] = new JogadorVerde();
                    break;
                case "amarelo":
                    jogs[i] = new JogadorAmarelo();
                    break;
                default:
                    jogs[i] = new JogadorAzul();
            }
        }
    }

    private String[] gerarCores() {
        int indice = 0;
        String[] cores = {"vermelho", "verde", "amarelo", "azul"}, aux = new String[qtd_jogs];

        for (int i = 0; i < 4; i++) {
            if (cor_inicial.equals(cores[i])) {
                indice = i;
                break;
            }
        }

        for (int i = 0; i < qtd_jogs; i++) {
            if (qtd_jogs == 2)
                aux[i] = cores[(2 * i + indice) % 4];
            else
                aux[i] = cores[(i + indice) % 4];
        }

        return aux;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
