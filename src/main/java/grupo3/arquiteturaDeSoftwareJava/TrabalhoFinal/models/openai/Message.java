package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    private String role = "user";
    private String content;

    public Message(String content) {
        this.content = content;
    }
}
