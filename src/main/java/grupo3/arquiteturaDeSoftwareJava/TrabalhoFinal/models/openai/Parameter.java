package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Parameter {
    private String type;
    private Map<String, Object> properties;
}
