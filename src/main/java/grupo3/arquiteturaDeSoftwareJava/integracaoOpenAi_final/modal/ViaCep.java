package grupo3.arquiteturaDeSoftwareJava.integracaoOpenAi_final.modal;

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
public class ViaCep {

	private Long id;
	private String bairro;
	private String cep;
	private String localidade;
}
