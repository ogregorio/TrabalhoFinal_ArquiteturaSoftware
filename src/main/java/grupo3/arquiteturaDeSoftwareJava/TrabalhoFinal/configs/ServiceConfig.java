package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.configs;

import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service.FunctionExecutorService;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service.OpenAIService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {

    @Bean
    public FunctionExecutorService functionExecutorService() {
        return new FunctionExecutorService();
    }

    @Bean
    public OpenAIService openAIService() {
        return new OpenAIService();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}