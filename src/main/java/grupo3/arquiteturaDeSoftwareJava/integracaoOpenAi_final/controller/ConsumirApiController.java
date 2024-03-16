package grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.controller;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.modal.MensagemOpenAi;
import grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.modal.ViaCep;
import grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.service.ConsumirApiService;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api")
public class ConsumirApiController {
	
	@Autowired
	private ConsumirApiService apiService;

	@GetMapping("/{cep}")
	public ResponseEntity getViaCep(@PathParam("cep") String cep) {
		ViaCep result = this.apiService.getDataViaCep(cep);
		if(result == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.accepted().body(result);
	}

	@GetMapping("/openApi")
	public ResponseEntity getOpenApi() {
		Integer opcao = 0;
		MensagemOpenAi mensagem;
		Scanner scanner = new Scanner(System.in);
		while(opcao != 4) {
			escolherOpcao();
			opcao = scanner.nextInt();
			if(opcao != 4) {
				String corpo = "";
				mensagem = new MensagemOpenAi();
				corpo = mensagem.escreverMensagem(scanner);
			}
		}
		return ResponseEntity.ok().build();
	}
	
	public void escolherOpcao() {
		System.out.println("Escolha a opção que deseja executar: ");
		System.out.println("1 - Duvidas frequentes");
		System.out.println("2 - Mapas mentais");
		System.out.println("3 - Quiz interativo");
		System.out.println("4 - Finalizar");
	}
}
