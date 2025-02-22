package com.ludico;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Regras extends Application {

    public void start(Stage primaryStage) {
        String regrasTexto = "\u0002Regras do Lúdico\u0002\n\n"
                + "1. \u0002Objetivo do Jogo\u0002\n"
                + "   Cada jogador deve mover suas quatro peças pelo tabuleiro seguindo o percurso indicado e colocá-las na zona final. O primeiro jogador a finalizar todas as suas peças vence o jogo.\n\n"
                + "2. \u0002Configuração Inicial\u0002\n"
                + "   - Cada jogador inicia com quatro peças em sua respectiva base.\n\n"
                + "3. \u0002Movimentação das Peças\u0002\n"
                + "   - Para iniciar o jogo, cada jogador deve rolar o dado. O jogador com o maior valor começa.\n"
                + "   - Um jogador pode colocar uma peça em jogo ao tirar um \u00026\u0002 no dado. Se já houver peças no tabuleiro, ele pode optar por mover uma delas em vez de colocar uma nova.\n"
                + "   - Se o jogador tirar \u00026\u0002, ele tem direito a uma nova jogada.\n"
                + "   - As peças avançam no sentido horário de acordo com o valor do dado.\n\n"
                + "4. \u0002Captura de Peças\u0002\n"
                + "   - Se uma peça parar em uma casa ocupada por uma peça adversária fora da zona segura, a peça adversária é capturada e retorna para a base.\n\n"
                + "5. \u0002Casas Seguras\u0002\n"
                + "   - As casas seguras são aquelas que são coloridas ou marcadas com uma estrela.\n"
                + "   - Peças posicionadas em casas seguras não podem ser capturadas.\n\n"
                + "6. \u0002Bônus de Chegada\u0002\n"
                + "   - Se um jogador colocar uma de suas peças na linha de chegada, ele tem direito a uma nova jogada.\n\n"
                + "7. \u0002Modo Perguntas\u0002\n"
                + "   - Para sair da base, colocar uma peça na linha de chegada ou capturar uma peça adversária, o jogador deve responder corretamente uma pergunta de computação.\n"
                + "   - Se tentar capturar uma peça e errar a pergunta, a própria peça do jogador é eliminada.\n\n"
                + "8. \u0002Finalização do Jogo\u0002\n"
                + "   - Para vencer, um jogador deve levar suas quatro peças até a zona final.\n"
                + "   - O jogo continua até que um jogador complete esse objetivo.";

        TextArea textArea = new TextArea(regrasTexto);
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-background-color: #f4f4f4; -fx-text-fill: #333;");

        StackPane root = new StackPane(textArea);
        root.setPadding(new Insets(20));
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(root, 600, 500);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
    }
}
