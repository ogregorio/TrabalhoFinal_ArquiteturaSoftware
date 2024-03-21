package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models;

import lombok.Data;

@Data
public class Content {

    private String function;
    private String properties;

    public Content(String function, String properties) {
        this.function = function;
        this.properties = properties;
    }
    
}