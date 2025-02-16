package com.ludico;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class AjustarPerguntas {
    private int qtd_perguntas;
    private String[] perguntas, resp_corretas;
    private String[][] respostas;

    public AjustarPerguntas(String[] topicos) {
        qtd_perguntas = contarPerguntas(topicos);
        perguntas = new String[qtd_perguntas];
        resp_corretas = new String[qtd_perguntas];
        respostas = new String[4][qtd_perguntas];

        ajustarPerguntasRespostas(topicos);
        embaralharPerguntas();
    }

    private int contarPerguntas(String[] topicos) {
        int qtd_perguntas = 0;

        for (String topico : topicos) {
            String caminho = String.format("perguntas/%s.json", topico);

            try (InputStream inputStream = AjustarPerguntas.class.getClassLoader().getResourceAsStream(caminho);
                 InputStreamReader reader = inputStream != null ? new InputStreamReader(inputStream, StandardCharsets.UTF_8) : null) {

                if (reader == null) {
                    System.err.println("Arquivo não encontrado: " + caminho);
                    continue;
                }

                JsonElement jsonElement = JsonParser.parseReader(reader);
                if (jsonElement.isJsonArray()) {
                    qtd_perguntas += jsonElement.getAsJsonArray().size();
                }
            } catch (Exception e) {
                System.err.println("Erro ao processar o arquivo: " + caminho);
                e.printStackTrace();
            }
        }

        return qtd_perguntas;
    }

    private void ajustarPerguntasRespostas(String[] topicos) {
        int i = 0;

        for (String topico : topicos) {
            String caminho = String.format("perguntas/%s.json", topico);

            try (InputStream inputStream = AjustarPerguntas.class.getClassLoader().getResourceAsStream(caminho);
                 InputStreamReader reader = inputStream != null ? new InputStreamReader(inputStream, StandardCharsets.UTF_8) : null) {

                if (reader == null) {
                    System.err.println("Arquivo não encontrado: " + caminho);
                    continue;
                }

                Gson gson = new Gson();
                Type listType = new TypeToken<List<Map<String, String>>>() {
                }.getType();
                List<Map<String, String>> listaPerguntas = gson.fromJson(reader, listType);

                for (Map<String, String> item : listaPerguntas) {
                    if (i >= qtd_perguntas) {
                        break;
                    }

                    for (int j = 0; j < 4; j++)
                        respostas[j][i] = item.get(String.valueOf(j));

                    resp_corretas[i] = respostas[0][i];
                    perguntas[i] = item.get("pergunta");
                    i++;
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar perguntas do arquivo: " + caminho);
                e.printStackTrace();
            }
        }
    }

    private void embaralharPerguntas() {
        int i, j;

        for (i = qtd_perguntas - 1; i > 0; i--) {
            j = (int) (Math.random() * (i + 1));

            embaralharRespostas(respostas, i);
            trocarValores(perguntas, i, j);
            trocarValores(resp_corretas, i, j);
            trocarValores(respostas[0], i, j);
            trocarValores(respostas[1], i, j);
            trocarValores(respostas[2], i, j);
            trocarValores(respostas[3], i, j);
        }
    }

    private void trocarValores(String vetor[], int i, int j) {
        String copia = vetor[i];
        vetor[i] = vetor[j];
        vetor[j] = copia;
    }

    private void embaralharRespostas(String[][] respostas, int indice) {
        for (int i = 0; i < 4; i++) {
            int j = (int) (Math.random() * (i + 1));
            String copia = respostas[i][indice];
            respostas[i][indice] = respostas[j][indice];
            respostas[j][indice] = copia;
        }
    }

    public String[] getPerguntas() {
        return perguntas;
    }

    public String[] getRespostasCorretas() {
        return resp_corretas;
    }

    public String[][] getRespostas() {
        return respostas;
    }
}
