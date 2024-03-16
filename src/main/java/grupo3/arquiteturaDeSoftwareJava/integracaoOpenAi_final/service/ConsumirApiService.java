package grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.modal.MensagemOpenAi;
import grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.modal.ViaCep;

@Service
public class ConsumirApiService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${urlViaCep}")
	private String url;
	
	public ViaCep getDataViaCep(String cep) {
		url = url.replace("input", cep);
		ResponseEntity<ViaCep> response = restTemplate.getForEntity(url, ViaCep.class);
		if(response.getStatusCode().value() != 200) {
			System.out.println("ocorreu um erro.");
			return null;
		}
		return response.getBody();
	}
	
	public MensagemOpenAi getByOpenAi(String corpo) {
		ResponseEntity<MensagemOpenAi> response = restTemplate.getForEntity(url, MensagemOpenAi.class);
		return response.getStatusCode().value() == 200? response.getBody():null;
	}
}
