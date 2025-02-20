package com.ludico;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Jogador {
    @SuppressWarnings("unchecked")
    protected ArrayList<Peca>[] casas = new ArrayList[5];
    protected int indice_img = 0;
    protected float largura = Main.getLargura();
    protected String cor = definirCor(), cor_clara = definirCorClara();
    protected Pane root = Main.getRoot();
    protected Peca[] pecas = gerarPecas();
    protected ImageView[] imgs_chegada = gerarImagensChegada();
    protected Image[] imgs_encontro = gerarImagensEncontro();

    public Jogador() {
        for (int i = 0; i < 5; i++)
            casas[i] = new ArrayList<>();
    }

    protected abstract String definirCor();

    protected abstract String definirCorClara();

    protected abstract Peca[] gerarPecas();

    protected Image[] gerarImagensEncontro() {
        Image[] imgs = new Image[4];

        for (int i = 0; i < 4; i++) {
            try {
                imgs[i] = new Image(Objects.requireNonNull(getClass().
                        getResource("/imagens/final_" + cor + (i + 1) + ".png")).toExternalForm());
            } catch (NullPointerException e) {
                System.err.println("Erro: Imagem não encontrada no classpath.");
            } catch (Exception e) {
                System.err.println("Erro ao carregar a imagem: " + e.getMessage());
            }
        }

        return imgs;
    }

    protected ImageView[] gerarImagensChegada() {
        ImageView[] imgs = new ImageView[4];

        try {
            for (int i = 0; i < 4; i++) {
                String caminho = String.format("/imagens/chegada_%s%d.png", cor, i + 1);
                imgs[i] = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(caminho)).toExternalForm()));
                imgs[i].setFitWidth(largura / 8f);
                imgs[i].setFitHeight(largura / 8f);
                imgs[i].setLayoutX(largura * 0.4375f);
                imgs[i].setLayoutY(largura * (13f / 48f));
                imgs[i].setVisible(false);
                root.getChildren().add(imgs[i]);
            }
        } catch (NullPointerException e) {
            System.err.println("Erro: Imagem 'chegada_xy.png' não encontrada no classpath.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }

        return imgs;
    }

    public void mostrarImagemChegada() {
        if (indice_img >= 4)
            return;

        imgs_chegada[indice_img].setVisible(true);
        ++indice_img;
    }

    public boolean verificarJogadasDisponiveis() {
        for (int i = 0; i < 4; i++) {
            if (pecas[i].getJogadaDisponivel())
                return true;
        }
        return false;
    }

    public boolean verificarGanhou() {
        for (int i = 0; i < 4; i++) {
            if (!pecas[i].getTipoPosicao().equals("linha_chegada"))
                return false;
        }
        return true;
    }

    public Peca getPecaEscolhida() {
        for (int i = 0; i < 4; i++) {
            if (!pecas[i].getJogadaFinalizada()) {
                pecas[i].setJogadaFinalizada(true);
                return pecas[i];
            }
        }
        return null;
    }

    public void ativarBotoes(boolean ativar) {
        for (int i = 0; i < 4; i++)
            pecas[i].ativarBotao(ativar);
    }

    public String getCor() {
        return cor;
    }

    public String getCorClara() {
        return cor_clara;
    }

    public ArrayList<Peca> getCasa(int i) {
        return casas[i];
    }

    public Image getImgEncontro(int i) {
        return imgs_encontro[i];
    }
}
