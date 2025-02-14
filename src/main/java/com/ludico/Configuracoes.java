package com.ludico;

public class Configuracoes {

    public String[] organizarTopicos(String[] topicos) {
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

        return topicos;
    }

    public Jogador[] gerarJogadores(int qtd_jogs, String cor_inicial) {
        String[] cores = gerarCores(qtd_jogs, cor_inicial);
        Jogador[] jogs = new Jogador[qtd_jogs];

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

        return jogs;
    }

    private String[] gerarCores(int qtd_jogs, String cor_inicial) {
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
}
