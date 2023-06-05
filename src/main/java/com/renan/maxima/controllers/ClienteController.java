package com.renan.maxima.controllers;

import com.renan.maxima.dto.ClienteDTO;
import com.renan.maxima.exception.NaoEncontradoException;
import com.renan.maxima.service.ClienteService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes")
public class ClienteController {

	@Autowired
	private ClienteService service;

	private static final Logger log = LoggerFactory.getLogger(ClienteController.class);

	private final HttpServletRequest req;

	@Autowired
	public ClienteController(HttpServletRequest request) {
		this.req = request;
	}

	@GetMapping("/teste")
	@Operation(summary = "Get de teste", description = "Faz um get para testar")
	public String exemplo() {
		return "Exemplo de resposta do controller";
	}

	@PostMapping
	@Operation(summary = "Salvar Cliente", description = "Salvar um novo Cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Cliente salvo com sucesso"),
			@ApiResponse(responseCode = "400", description = "Erro no formulário preenchido"),
			@ApiResponse(responseCode = "500", description = "Erro ao Salvar o cliente") })
	public ResponseEntity<?> salvarCliente(@RequestBody @Valid ClienteDTO dto) {
		try {
			ClienteDTO newCliente = service.salvarCliente(dto);
			log.info(req.getMethod() + " - " + req.getRequestURI() + " - Status 201");
			return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
		} catch (ConstraintViolationException ex) {
			log.info(req.getMethod() + " - " + req.getRequestURI() + " - Status 400");
			throw ex;
		} catch (Exception e) {
			log.error(req.getMethod() + " - " + req.getRequestURI() + " - Status 500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar Cliente", description = "Atualiza um Cliente")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Cliente não entrontado"),
			@ApiResponse(responseCode = "500", description = "Erro ao atualizar o cliente") })
	public ResponseEntity<?> AtualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO dto) {
		try {
			ClienteDTO cliente = service.editarCliente(id, dto);
			log.info(req.getMethod() + " - " + req.getRequestURI() + " - Status 200");
			return ResponseEntity.status(HttpStatus.OK).body(cliente);
		} catch (NaoEncontradoException ex) {
			log.info(req.getMethod() + " - " + req.getRequestURI() + " - Status 404");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (Exception e) {
			log.error(req.getMethod() + " - " + req.getRequestURI() + " - Status 500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping
	@Operation(summary = "Buscar Clientes", description = "Busca Lista de clientes paginada")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Retorna lista paginada de clientes"),
			@ApiResponse(responseCode = "500", description = "Erro ao listar clientes") })
	public ResponseEntity<?> buscarClintesComFiltro(@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "cod", required = false) String codCliente,
			@RequestParam(value = "cnpj", required = false) String cnpj, Pageable pageable) {
		try {
			Page<ClienteDTO> pageCliente = service.buscarTodosOsClientesComFiltro(nome, codCliente, cnpj, pageable);
			log.info(req.getMethod() + " - " + req.getRequestURI() + " - Status 200");
			return ResponseEntity.status(HttpStatus.OK).body(pageCliente);
		} catch (Exception e) {
			log.error(req.getMethod() + " - " + req.getRequestURI() + " - Status 500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar Cliente", description = "Deleta um cliente pelo seu id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cliente excluido com sucesso"),
			@ApiResponse(responseCode = "500", description = "Erro ao excluir cliente") })
	public ResponseEntity<?> excluirCliente(@PathVariable Long id) {
		try {
			this.service.excluirCliente(id);
			log.info(req.getMethod() + " - " + req.getRequestURI() + " - Status 200");
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			log.error(req.getMethod() + " - " + req.getRequestURI() + " - Status 500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
