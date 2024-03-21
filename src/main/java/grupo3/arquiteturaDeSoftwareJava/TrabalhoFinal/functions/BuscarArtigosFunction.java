package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.functions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.interfaces.IFunction;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.Function;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class BuscarArtigosFunction implements IFunction {
    private static final String CORE_API_URL = "https://api.core.ac.uk/v3/";

    @Autowired
    private RestTemplate restTemplate;

    public BuscarArtigosFunction(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String execute(Object payload) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree((String) payload);

            String query = jsonNode.has("query") ? jsonNode.get("query").asText() : null;

            if (query == null || query.isEmpty()) {
                return "A consulta não pode estar vazia.";
            }

            String endpoint = CORE_API_URL + "search/journals?q=" + query;

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                return extractContent(response.getBody());
            } else {
                return "Erro ao buscar artigos. Código de status: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Erro ao processar payload JSON: " + e.getMessage();
        }
    }

    private String extractContent(String responseBody) {
        StringBuilder result = new StringBuilder();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseBody);

            JsonNode resultsNode = root.get("results");
            if (resultsNode != null && resultsNode.isArray() && !resultsNode.isEmpty()) {
                for (JsonNode articleNode : resultsNode) {
                    String title = articleNode.get("title").asText();
                    String doi = articleNode.get("identifiers").get(0).asText();
                    String publisher = articleNode.get("publisher").asText();
                    String subjects = articleNode.get("subjects").get(0).asText();

                    result.append("\n")
                            .append("Título: ").append(title).append("\n")
                            .append("DOI: ").append(doi).append("\n")
                            .append("Publicado por: ").append(publisher).append("\n")
                            .append("Assunto: ").append(subjects).append("\n\n");
                }
            } else {
                result.append("Nenhum artigo encontrado.");
            }
        } catch (Exception e) {
            result.append("Erro ao processar a resposta: ").append(e.getMessage());
        }

        return result.toString();
    }

    @Override
    public Function getDefinition() {
        // definition of a function in OpenAI Contract
        Function function = new Function();
        function.setName("BuscarArtigos");
        function.setDescription("Retorna uma lista de artigos dado o termo de pesquisa.");
        function.setRequired(new String[] { "query" });

        // Definition of parameters in OpenAI Contract
        Parameter parameter = new Parameter();
        parameter.setType("object");
        Map map = new HashMap<>();
        map.put("type", "string");
        map.put("description", "query to search journals in core api database in english");
        parameter.setProperties(Map.ofEntries(new AbstractMap.SimpleEntry("query", map)));
        function.setParameters(parameter);

        return function;
    }
}
