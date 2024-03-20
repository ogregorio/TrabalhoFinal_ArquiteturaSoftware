package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models;

import lombok.Getter;

public class Content {

    @Getter final String function;
    @Getter private final String properties;

    public Content(String function, String properties) {
        this.function = function;
        this.properties = properties;
    }
}