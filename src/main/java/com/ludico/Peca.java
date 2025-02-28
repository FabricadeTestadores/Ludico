package com.ludico;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.Objects;

public abstract class Peca {
    protected Tabuleiro tabuleiro = Tabuleiro.instanciar();
    protected boolean jogada_finalizada = true, jogar_dnv = false;
    protected int pos_atual, pos_inicial, pos_final;
    protected float x_base, y_base, largura = Main.getLargura(), altura = Main.getAltura();
    protected float[] x_finais, y_finais;
    protected Pane root = Main.getRoot();
    protected String tipo_pos = "base", cor = definirCor(), cor_escura = definirCorEscura();
    protected ImageView img;
    protected Image img_sem_fundo, img_com_fundo, img_original;
    protected Button btn = new Button();
    protected Jogador jog;

    public Peca(float x_base, float y_base, Jogador jog) {
        this.x_base = x_base;
        this.y_base = y_base;
        this.jog = jog;
        definirPosicoes();
        definirImagens();
        definirBotao();
    }

    protected abstract String definirCor();

    protected abstract String definirCorEscura();

    protected abstract void definirPosicoes();

    protected void definirImagens() {
        try {
            String caminho1 = String.format("/imagens/peca_%s.png", cor);
            String caminho2 = String.format("/imagens/peca_%s-fundo_prata.png", cor);
            img_sem_fundo = new Image(Objects.requireNonNull(getClass().getResource(caminho1)).toExternalForm());
            img_com_fundo = new Image(Objects.requireNonNull(getClass().getResource(caminho2)).toExternalForm());
            img_original = img_sem_fundo;
            img = new ImageView(img_sem_fundo);
            img.setFitWidth(largura / 24f);
            img.setFitHeight(altura / 16f);
            img.setLayoutX(x_base);
            img.setLayoutY(y_base);
            root.getChildren().add(img);
        } catch (NullPointerException e) {
            System.err.println("Erro: Imagem não encontrada no classpath.");
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + e.getMessage());
        }
    }

    protected void definirBotao() {
        btn.setPrefSize(largura / 24f, altura / 16f);
        btn.setLayoutX(x_base);
        btn.setLayoutY(y_base);
        btn.setOpacity(0f);
        btn.setOnMouseEntered(e -> img.setImage(img_com_fundo));
        btn.setOnMouseExited(e -> img.setImage(img_sem_fundo));
        btn.setDisable(true);
        btn.setOnAction(e -> Movimento.instanciar().mover(this));
        root.getChildren().add(btn);
    }

    public void ativarBotao(boolean ativar) {
        btn.setDisable(!ativar);
    }

    public void setJogarNovamente(boolean jogar_dnv) {
        this.jogar_dnv = jogar_dnv;
    }

    public boolean getJogarNovamente() {
        return jogar_dnv;
    }

    public void setPosicao(int pos_atual) {
        this.pos_atual = pos_atual;
    }

    public int getPosicao() {
        return pos_atual;
    }

    public int getPosicaoInicial() {
        return pos_inicial;
    }

    public int getPosicaoFinal() {
        return pos_final;
    }

    public float getXBase() {
        return x_base;
    }

    public float getYBase() {
        return y_base;
    }

    public float getXFinal(int i) {
        return x_finais[i];
    }

    public float getYFinal(int i) {
        return y_finais[i];
    }

    public void setTipoPosicao(String tipo_pos) {
        this.tipo_pos = tipo_pos;
    }

    public String getTipoPosicao() {
        return tipo_pos;
    }

    public boolean getJogadaDisponivel() {
        int valor_dado = tabuleiro.getValorDado();

        return !tipo_pos.equals("linha_chegada") && (!tipo_pos.equals("base") || valor_dado >= 6)
                && (!tipo_pos.equals("quad_final") || valor_dado + pos_atual <= 5);
    }

    public void setJogadaFinalizada(boolean jogada_finalizada) {
        this.jogada_finalizada = jogada_finalizada;
    }

    public boolean getJogadaFinalizada() {
        return jogada_finalizada;
    }

    public ImageView getImagem() {
        return img;
    }

    public Button getBotao() {
        return btn;
    }

    public String getCorEscura() {
        return cor_escura;
    }

    public Jogador getJogador() {
        return jog;
    }

    public String getCor() {
        return cor;
    }

    public void trocarImagem(boolean original, int i) {
        if (original)
            img_sem_fundo = img_original;
        else
            img_sem_fundo = jog.getImgEncontro(i);

        img.setImage(img_sem_fundo);
    }
}
