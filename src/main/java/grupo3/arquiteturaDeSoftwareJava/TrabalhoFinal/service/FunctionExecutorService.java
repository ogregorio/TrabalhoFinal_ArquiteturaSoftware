package grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.service;

import grupo3.arquiteturaDeSoftwareJava.TrabalhoFinal.interfaces.IFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunctionExecutorService {
	
    @Autowired
    private List<IFunction> functions;

    public String execute(String name, Object payload) {
        for (IFunction function : functions) {
            if (function.getClass().getSimpleName().equals(name + "Function")) {
                return function.execute(payload);
            }
        }
        return "Function not found for name: " + name;
    }
}
