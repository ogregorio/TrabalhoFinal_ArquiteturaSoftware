package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal;

import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.Content;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service.FunctionExecutorService;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

import java.util.Scanner;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class })
public class IntegracaoOpenAiFinalApplication implements CommandLineRunner {
	@Autowired
	private final FunctionExecutorService functionService;

	@Autowired
	private final OpenAIService openAiService;

	@Autowired
	public IntegracaoOpenAiFinalApplication(FunctionExecutorService functionService, OpenAIService openAiService) {
		this.functionService = functionService;
		this.openAiService = openAiService;
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(IntegracaoOpenAiFinalApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bem-vindo ao ChatCLI!");
		System.out.println("Digite 'sair' a qualquer momento para sair.");
		System.out.print("Digite seu token: ");
		String token = scanner.nextLine();
		while (true) {
			System.out.print("Digite sua pergunta: ");
			String question = scanner.nextLine();

			Content func = openAiService.getFunction(question, token);
			String message = functionService.execute(func.getFunction(), func.getProperties());

			if (question.equalsIgnoreCase("sair")) {
				System.out.println("Saindo do ChatCLI...");
				break;
			}

			System.out.println("Resposta: " + message);
		}

		scanner.close();
	}
}