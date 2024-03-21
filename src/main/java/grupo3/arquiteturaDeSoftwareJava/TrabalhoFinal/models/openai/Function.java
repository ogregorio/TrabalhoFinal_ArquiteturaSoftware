package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai;

import lombok.Data;

@Data
public class Function {
    private String name;
    private String description;
    private String[] required;
    private Parameter parameters;
}
