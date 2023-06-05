package com.renan.maxima.dto;

import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.renan.maxima.entity.Cliente;
import com.renan.maxima.entity.Endereco;

public class EnderecoDTO {

	private Long id;
	private String logradouro;
	private String numero;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;
	private Double latitude;
	private Double longitude;

	public EnderecoDTO() {
	}

	public EnderecoDTO(Endereco endereco) {
		this.bairro = endereco.getBairro();
		this.cidade = endereco.getCidade();
		this.estado = endereco.getEstado();
		this.latitude = endereco.getLatitude();
		this.cep = endereco.getCep();
		this.logradouro = endereco.getLogradouro();
		this.longitude = endereco.getLongitude();
		this.numero = endereco.getNumero();
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Endereco toEntity(Cliente cliente) {
		Endereco newEndereco = new Endereco();
		newEndereco.setId(Objects.nonNull(id) ? id : null);
		newEndereco.setBairro(bairro);
		newEndereco.setCidade(cidade);
		newEndereco.setEstado(estado);
		newEndereco.setLatitude(latitude);
		newEndereco.setLogradouro(logradouro);
		newEndereco.setLongitude(longitude);
		newEndereco.setNumero(numero);
		newEndereco.setCliente(cliente);
		newEndereco.setCep(cep);
		return newEndereco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bairro, cep, cidade, estado, latitude, logradouro, longitude, numero);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EnderecoDTO other = (EnderecoDTO) obj;
		return Objects.equals(bairro, other.bairro) && Objects.equals(cep, other.cep)
				&& Objects.equals(cidade, other.cidade) && Objects.equals(estado, other.estado)
				&& Objects.equals(latitude, other.latitude) && Objects.equals(logradouro, other.logradouro)
				&& Objects.equals(longitude, other.longitude) && Objects.equals(numero, other.numero);
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
