package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.functions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.interfaces.IFunction;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.Function;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.openai.Parameter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DuvidasFrequentesFunction implements IFunction {

    @Override
    public String execute(Object payload) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree((String) payload);

            String pergunta = jsonNode.has("pergunta") ? jsonNode.get("pergunta").asText() : null;

            if (pergunta != null) {
                return getMessageForPergunta(pergunta);
            } else {
                return "Atributo 'pergunta' não encontrado no payload.";
            }

        } catch (Exception e) {
            return "Erro ao processar payload JSON: " + e.getMessage();
        }
    }

    private String getMessageForPergunta(String pergunta) {
        switch (pergunta) {
            case "COMO_ENCONTRAR_ARTIGOS":
                return "Para encontrar artigos, você pode utilizar bases de dados acadêmicas como PubMed, Scopus ou Web of Science.";
            case "COMO_FAZER_CITACOES":
                return "Para fazer citações corretamente, siga o estilo de citação exigido pela sua instituição ou revista acadêmica.";
            case "COMO_ESTUDAR_PARA_PROVA":
                return "Para estudar para uma prova, revise suas anotações, faça resumos e resolva exercícios relacionados ao conteúdo.";
            case "COMO_FAZER_TRABALHO_DE_CONCLUSAO":
                return "Para fazer um trabalho de conclusão, defina um tema, faça uma revisão bibliográfica e siga as orientações de seu orientador.";
            case "ONDE_ENCONTRAR_MATERIAL_DE_ESTUDO":
                return "Você pode encontrar material de estudo em livros, artigos acadêmicos, cursos online e tutoriais disponíveis na internet.";
            default:
                return "Pergunta não reconhecida.";
        }
    }

    @Override
    public Function getDefinition() {
        // definition of a function in OpenAI Contract
        Function function = new Function();
        function.setName("DuvidasFrequentes");
        function.setDescription("Retorna uma resposta para uma pergunta frequente existente.");
        function.setRequired(new String[] { "pergunta" });

        // Definition of parameters in OpenAI Contract
        Parameter parameter = new Parameter();
        parameter.setType("object");
        Map map = new HashMap<>();
        map.put("type", "string");
        map.put("description", "possible answer");
        map.put("enum", new String[]{
                "COMO_ENCONTRAR_ARTIGOS",
                "COMO_FAZER_CITACOES",
                "COMO_ESTUDAR_PARA_PROVA",
                "COMO_FAZER_TRABALHO_DE_CONCLUSAO",
                "ONDE_ENCONTRAR_MATERIAL_DE_ESTUDO"
        });
        parameter.setProperties(Map.ofEntries(new AbstractMap.SimpleEntry("pergunta", map)));
        function.setParameters(parameter);

        return function;
    }
}
