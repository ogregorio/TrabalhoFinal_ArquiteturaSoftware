package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.controller;

import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.Content;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.models.RequestPayload;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service.FunctionExecutorService;
import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class OpenAIController {

	@Autowired
	private final FunctionExecutorService functionService;

	@Autowired
	private final OpenAIService openAiService;

	@Autowired
	public OpenAIController(FunctionExecutorService functionService, OpenAIService openAiService) {
		this.functionService = functionService;
		this.openAiService = openAiService;
	}

	@PostMapping("/ask")
	public ResponseEntity<String> ask(@RequestBody RequestPayload reqBody, @RequestHeader("Authorization") String auth) {
		Content func = openAiService.getFunction(reqBody.getQuestion(), auth);
		String message = functionService.execute(func.getFunction(), func.getProperties());
		return ResponseEntity.ok(message);
	}
}
