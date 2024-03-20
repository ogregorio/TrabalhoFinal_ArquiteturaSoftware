package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.interfaces;

import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.Function;

public interface IFunction {
    public String execute(Object payload);
    public Function getDefinition();
}
