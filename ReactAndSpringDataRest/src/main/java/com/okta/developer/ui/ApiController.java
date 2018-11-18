package com.okta.developer.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiController {

	@RequestMapping("/")
	public String index() {
		return "index";
		
	}
}
