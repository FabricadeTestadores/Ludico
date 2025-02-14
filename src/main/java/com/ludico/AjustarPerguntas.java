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
        embaralharPerguntasRespostas();
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

    private void embaralharPerguntasRespostas() {
        int k;
        String copia;

        for (int i = qtd_perguntas - 1; i > 0; i--) {
            for (int j = 3; j > 0; j--) {
                k = (int) (Math.random() * (j + 1));
                copia = respostas[j][i];
                respostas[j][i] = respostas[k][i];
                respostas[k][i] = copia;
            }

            k = (int) (Math.random() * (i + 1));

            for (int j = 0; j < 4; j++) {
                copia = respostas[j][i];
                respostas[j][i] = respostas[j][k];
                respostas[j][k] = copia;
            }

            copia = perguntas[i];
            perguntas[i] = perguntas[k];
            perguntas[k] = copia;
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
