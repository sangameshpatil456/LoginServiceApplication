package com.login.model.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {

	@GetMapping("/testing")
	public String getTesting() {
		return "Im Running";
	}
}