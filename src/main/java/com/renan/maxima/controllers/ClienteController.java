package com.renan.maxima.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes")
public class ClienteController {

	@GetMapping("/teste")
	@Operation(summary = "Get de teste", description = "Faz um get para testar")
	public String exemplo() {
		return "Exemplo de resposta do controller";
	}

}
