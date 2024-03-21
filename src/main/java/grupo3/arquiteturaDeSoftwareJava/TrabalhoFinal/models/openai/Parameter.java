package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai;

import java.util.Map;

import lombok.Data;

@Data
public class Parameter {
    private String type;
    private Map<String, Object> properties;
}
