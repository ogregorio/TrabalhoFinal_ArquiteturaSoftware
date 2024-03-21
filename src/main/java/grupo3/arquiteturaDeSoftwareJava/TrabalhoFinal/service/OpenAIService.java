package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.functions.DuvidasFrequentesFunction;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.interfaces.IFunction;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.Content;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.Function;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.Message;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.OpenAIPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAIService {
    
	@Autowired
    private List<IFunction> functions;

	@Autowired
	private RestTemplate restTemplate;
	
	/**
	 * 
	 * @param prompt (pergunta)
	 * @param authorization
	 * @return
	 */
    public Content getFunction(String prompt, String authorization) {
        String endpoint = "https://api.openai.com/v1/chat/completions";
        String token = authorization.split(" ")[1];
        if (token == null) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        String requestBody = buildRequestBody(prompt);
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(endpoint, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return extractContent(response.getBody());
        } else {
            return null;
        }
    }

    private String buildRequestBody(String prompt) {
        List<Message> messages = new ArrayList();
        messages.add(new Message(prompt));

        Function[] definitions = new Function[functions.size()];

        for (int i = 0; i < functions.size(); i++) {
            definitions[i] = functions.get(i).getDefinition();
        }

        OpenAIPayload payload = new OpenAIPayload(messages, definitions);

        try {
            return new ObjectMapper().writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Content extractContent(String completion) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonResponse = mapper.readTree(completion);
            String function = jsonResponse.get("choices").get(0).get("message").get("function_call").get("name").asText();
            String arguments = jsonResponse.get("choices").get(0).get("message").get("function_call").get("arguments").asText();
            return new Content(function, arguments);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
