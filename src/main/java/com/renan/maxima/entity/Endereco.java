package com.renan.maxima.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "endereco")
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;
	
	@NotBlank(message = "logradouro é obrigatório")
	@Column(name = "logradouro", nullable = false)
	private String logradouro;
	
	@NotBlank(message = "número é obrigatório")
	@Column(name = "numero", nullable = false)
	private String numero;
	
	@NotBlank(message = "bairro é obrigatório")
	@Column(name = "bairro", nullable = false)
	private String bairro;
	
	@NotBlank(message = "cidade é obrigatório")
	@Column(name = "cidade", nullable = false)
	private String cidade;
	
	@NotBlank(message = "estado é obrigatório")
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@NotBlank(message = "país é obrigatório")
	@Column(name = "pais", nullable = false)
	private String pais;
	
	@NotNull(message = "latitude é obrigatório")
	@Column(name = "latitude", nullable = false)
	private Double latitude;
	
	@NotNull(message = "longitude é obrigatório")
	@Column(name = "longitude", nullable = false)
	private Double longitude;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
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

}
