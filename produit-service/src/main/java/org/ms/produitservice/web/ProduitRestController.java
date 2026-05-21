package org.ms.produitservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Hashtable;
import java.util.Map;

@RestController
public class ProduitRestController {
	@Value("${globalParam:0}")
	private int globalParam;

	@Value("${monParam:0}")
	private int monParam;

	@Value("${email:default@mail.com}")
	private String email;

	@GetMapping("config")
	public Map<String, Object> config() {
		Map<String, Object> params = new Hashtable<>();
		params.put("globalParam", globalParam);
		params.put("monParam", monParam);
		params.put("email", email);
		params.put("threadName", Thread.currentThread().toString());
		return params;
	}
}