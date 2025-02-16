package com.ludico;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TelaPerguntas {
    private static TelaPerguntas instancia;
    private boolean pergunta_acertada;
    private int indice_pergunta = 0, num_perguntas;
    private float largura = Main.getLargura() * 0.75f, altura = Main.getAltura() * 0.75f;
    private Stage stage;
    private String resposta = null;
    private String[] perguntas, resp_corretas;
    private String[][] respostas;
    private Button caixa_pergunta;

    private TelaPerguntas(String[] topicos) {
        AjustarPerguntas ajustar = new AjustarPerguntas(topicos);
        perguntas = ajustar.getPerguntas();
        resp_corretas = ajustar.getRespostasCorretas();
        respostas = ajustar.getRespostas();
        num_perguntas = perguntas.length;
    }

    public static TelaPerguntas instanciar(String[] topicos) {
        if (instancia == null)
            instancia = new TelaPerguntas(topicos);

        return instancia;
    }

    public void gerarTela(String cor) {
        float y_botao = altura * 0.4f;
        String formato;
        Button btn_respostas[] = new Button[4];
        Pane root = new Pane();
        Scene scene = new Scene(root, largura, altura);
        Stage stage = new Stage();

        caixa_pergunta = new Button();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.setOnCloseRequest(Event::consume);

        caixa_pergunta.setPrefSize(largura * 0.8f, altura * 0.3f);
        caixa_pergunta.setLayoutX(largura / 10f);
        caixa_pergunta.setLayoutY(altura / 20f);
        caixa_pergunta.setFont(Font.font(largura / 40f));
        caixa_pergunta.setText(perguntas[indice_pergunta]);
        caixa_pergunta.setWrapText(true);
        caixa_pergunta.setStyle(
                "-fx-background-color: blue; -fx-text-fill: white; -fx-border-color: #C0C0C0; -fx-border-width: 2px;");
        root.getChildren().add(caixa_pergunta);

        for (int i = 0; i < 4; i++) {
            formato = String.format(
                    "-fx-background-color: %s; -fx-text-fill: white;  -fx-border-color: #C0C0C0; -fx-border-width: 2px;",
                    cor);

            btn_respostas[i] = new Button();
            btn_respostas[i].setFont(Font.font(largura / 50f));
            btn_respostas[i].setPrefSize(largura * 0.8f, altura * 0.1f);
            btn_respostas[i].setLayoutX(largura / 10f);
            btn_respostas[i].setLayoutY(y_botao);
            btn_respostas[i].setText(respostas[i][indice_pergunta]);
            btn_respostas[i].setStyle(formato);

            root.getChildren().add(btn_respostas[i]);
            y_botao += altura * 0.15f;
        }

        criarEventosBotoes(btn_respostas);
        stage.setTitle("Perguntas");
        stage.setScene(scene);
        this.stage = stage;
    }

    public boolean getPerguntaAcertada() {
        stage.showAndWait();

        indice_pergunta = (indice_pergunta + 1) % num_perguntas;
        resposta = null;

        return pergunta_acertada;
    }

    private void criarEventosBotoes(Button botoes[]) {
        for (int i = 0; i < 4; i++) {
            final int indice_botao = i;

            botoes[i].setOnAction(evento -> {
                String msg;
                resposta = respostas[indice_botao][indice_pergunta];

                if (resposta.equals(resp_corretas[indice_pergunta])) {
                    msg = "ACERTOU!";
                    pergunta_acertada = true;
                } else {
                    msg = "ERROU!";
                    pergunta_acertada = false;
                }

                caixa_pergunta.setText(msg);
                caixa_pergunta.setFont(Font.font(largura / 20f));

                for (int j = 0; j < 4; j++) {
                    botoes[j].setDisable(true);
                }

                PauseTransition pause = new PauseTransition(Duration.seconds(0.75f));
                pause.setOnFinished(event -> stage.close());
                pause.play();
            });
        }
    }
}
