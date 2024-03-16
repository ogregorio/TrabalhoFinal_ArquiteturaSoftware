package grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.modal;

import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MensagemOpenAi {

	private Long id;
	private String mensagem;
	
	public String escreverMensagem(Scanner scanner) {
		String mensagem = "";
		while(mensagem.length() > 200) {
			System.out.println("Digiten a mensagem (Observação: não mais que 200 caracteres): ");
			mensagem = scanner.nextLine();
			if(mensagem.length() > 200) {
				System.out.println("Mensagem com mais de 200 caracteres, escreva novamente.");
			} else {
				break;
			}
		}
		return mensagem;
		
	}
}
