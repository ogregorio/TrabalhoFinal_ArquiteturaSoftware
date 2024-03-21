package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai;

import java.util.List;

import lombok.Data;

@Data
public class OpenAIPayload {
    private List<Message> messages;
    private Function[] functions;
    private int max_tokens = 200;
    private double temperature = 0.7;
    private String model = "gpt-3.5-turbo";

    public OpenAIPayload(List<Message> messages, Function[] functions) {
        this.messages = messages;
        this.functions = functions;
    }
}