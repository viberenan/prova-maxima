package com.renan.maxima.service;

import com.renan.maxima.dto.ClienteDTO;
import com.renan.maxima.entity.Cliente;
import com.renan.maxima.repository.ClienteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

	private final ClienteRepository repository;

	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	public ClienteService(ClienteRepository clienteRepository) {
		this.repository = clienteRepository;
	}

	public ClienteDTO salvarCliente(ClienteDTO dto) {
		try {
			Cliente newCliente = dto.toEntity();
			newCliente = repository.save(newCliente);
			return new ClienteDTO(newCliente);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

}
