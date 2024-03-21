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

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@Component
public class HistoriaDasDatasFunction implements IFunction {
    private static final String CORE_API_URL = "http://numbersapi.com/{month}/{day}/date";

    @Autowired
    private RestTemplate restTemplate;

    public HistoriaDasDatasFunction(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String execute(Object payload) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree((String) payload);

            String dia = jsonNode.has("dia") ? jsonNode.get("dia").asText() : null;
            String mes = jsonNode.has("mes") ? jsonNode.get("mes").asText() : null;

            if (dia == null || dia.isEmpty() || mes == null || mes.isEmpty()) {
                return "Você precisa de um mês e um dia.";
            }

            String endpoint = CORE_API_URL.replace("{day}", dia).replace("{month}", mes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> request = new HttpEntity<>(null, headers);

            ResponseEntity<String> response = restTemplate.exchange(endpoint, HttpMethod.GET, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonNode treatedReponse = new ObjectMapper().readTree((String) response.getBody());
                return treatedReponse.has("text") ? treatedReponse.get("text").asText() : "Não aconteceu nada...";
            } else {
                return "Erro ao buscar data. Código de status: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Erro ao processar payload JSON: " + e.getMessage();
        }
    }
    @Override
    public Function getDefinition() {
        // definition of a function in OpenAI Contract
        Function function = new Function();
        function.setName("HistoriaDasDatas");
        function.setDescription("Retorna uma história por trás de uma data.");
        function.setRequired(new String[] { "dia", "mes" });

        // Definition of parameters in OpenAI Contract
        Parameter parameter = new Parameter();
        parameter.setType("object");

        Map day = new HashMap<>();
        day.put("type", "integer");
        day.put("description", "possible day");

        Map month = new HashMap<>();
        month.put("type", "integer");
        month.put("description", "possible month");

        parameter.setProperties(Map.ofEntries(new AbstractMap.SimpleEntry("dia", day), new AbstractMap.SimpleEntry("mes", month)));
        function.setParameters(parameter);

        return function;
    }
}
