package com.ludico;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class Peca {
    protected Tabuleiro tabuleiro = Tabuleiro.instanciar();
    protected boolean jogada_finalizada = true, jogar_dnv = false;
    protected int tempo_espera, pos_inicial, pos_final, pos_atual = pos_inicial;
    protected float x_base, y_base, largura = Main.getLargura(), altura = Main.getAltura();
    protected Pane root = Main.getRoot();
    protected String tipo_pos = "base", cor = definirCor();
    protected ImageView img;
    protected Image img_sem_fundo, img_com_fundo;
    protected Button btn = new Button();

    public Peca(float x_base, float y_base) {
        this.x_base = x_base;
        this.y_base = y_base;

        definirPosicoes();
        definirImagens();
        definirBotao();
    }

    protected abstract String definirCor();

    protected abstract void definirPosicoes();

    protected void definirImagens() {
        try {
            String caminho1 = String.format("/imagens/peca_%s.png", cor);
            String caminho2 = String.format("/imagens/peca_%s-fundo_prata.png", cor);

            img_sem_fundo = new Image(getClass().getResource(caminho1).toExternalForm());
            img_com_fundo = new Image(getClass().getResource(caminho2).toExternalForm());
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
        if (ativar)
            btn.setDisable(false);
        else
            btn.setDisable(true);
    }

    public void setTempoEspera(int tempo_espera) {
        this.tempo_espera = tempo_espera;
    }

    public void setJogarNovamente(boolean jogar_dnv) {
        this.jogar_dnv = jogar_dnv;
    }

    public boolean getJogarNovamente() {
        return jogar_dnv;
    }

    public int getTempoEspera() {
        return tempo_espera;
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

    public void setTipoPosicao(String tipo_pos) {
        this.tipo_pos = tipo_pos;
    }

    public String getTipoPosicao() {
        return tipo_pos;
    }

    public boolean getJogadaDisponivel() {
        int valor_dado = tabuleiro.getValorDado();

        if (tipo_pos.equals("linha_chegada"))
            return false;
        else if (tipo_pos.equals("base") && valor_dado == 6)
            return true;
        else
            return tipo_pos.equals("quad_principal");
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
}
