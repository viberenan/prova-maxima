package com.renan.maxima.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.renan.maxima.entity.Cliente;

public class ClienteDTO {

	private Long id;
	private String codCliente;
	private String nome;
	private String cnpj;
	private Set<EnderecoDTO> enderecos = new HashSet<EnderecoDTO>();

	public ClienteDTO() {
	}

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.cnpj = cliente.getCnpj();
		this.codCliente = cliente.getCodCliente();
		this.nome = cliente.getNome();
		if (!cliente.getEndereco().isEmpty()) {
			this.enderecos = cliente.getEndereco().stream().map(e -> new EnderecoDTO(e)).collect(Collectors.toSet());
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(String codCliente) {
		this.codCliente = codCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Set<EnderecoDTO> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Set<EnderecoDTO> enderecos) {
		this.enderecos = enderecos;
	}

	public Cliente toEntity() {
		Cliente newCliente = new Cliente();
		newCliente.setNome(nome);
		newCliente.setCnpj(cnpj);
		newCliente.setCodCliente(codCliente);
		if (!enderecos.isEmpty()) {
			newCliente.setEndereco(enderecos.stream().map(e -> e.toEntity(newCliente)).collect(Collectors.toSet()));
		}
		return newCliente;
	}

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
