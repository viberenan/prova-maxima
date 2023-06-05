package com.renan.maxima.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.renan.maxima.dto.ClienteDTO;
import com.renan.maxima.dto.EnderecoDTO;
import com.renan.maxima.entity.Cliente;
import com.renan.maxima.entity.Endereco;
import com.renan.maxima.exception.NaoEncontradoException;
import com.renan.maxima.repository.ClienteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	public Page<ClienteDTO> buscarTodosOsClientesComFiltro(String nome, String codCliente, String cnpj,
			Pageable pageable) {
		try {
			return repository.buscarTodosClientesComFiltro(nome, codCliente, cnpj, pageable);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	public void excluirCliente(Long idCliente) {
		try {
			this.repository.deleteById(idCliente);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

	public ClienteDTO editarCliente(Long idCliente, ClienteDTO dto) {
		try {
			Cliente cliente = this.repository.findById(idCliente)
					.orElseThrow(() -> new NaoEncontradoException("Cliente não encontrado com o ID: " + idCliente));
			cliente.setCnpj((Objects.nonNull(dto.getCnpj())) ? dto.getCnpj() : cliente.getCnpj());
			cliente.setCodCliente((Objects.nonNull(dto.getCodCliente())) ? dto.getCodCliente() : null);
			cliente.setNome((Objects.nonNull(dto.getNome())) ? dto.getNome() : cliente.getNome());

			if (dto.getEnderecos() != null && !dto.getEnderecos().isEmpty()) {
				Set<Endereco> enderecosAtualizados = new HashSet<>();

				for (EnderecoDTO enderecoDTO : dto.getEnderecos()) {
					Optional<Endereco> enderecoExistente = cliente.getEndereco().stream()
							.filter(e -> e.getId().equals(enderecoDTO.getId())).findFirst();

					if (enderecoExistente.isPresent()) {
						Endereco endereco = enderecoExistente.get();
						endereco.setLogradouro(enderecoDTO.getLogradouro());
						endereco.setNumero(enderecoDTO.getNumero());
						endereco.setBairro(enderecoDTO.getBairro());
						endereco.setCidade(enderecoDTO.getCidade());
						endereco.setEstado(enderecoDTO.getEstado());
						endereco.setCep(enderecoDTO.getCep());
						endereco.setLatitude(enderecoDTO.getLatitude());
						endereco.setLongitude(enderecoDTO.getLongitude());

						enderecosAtualizados.add(endereco);
					} else {
						Endereco novoEndereco = new Endereco();
						novoEndereco.setCliente(cliente);
						novoEndereco.setLogradouro(enderecoDTO.getLogradouro());
						novoEndereco.setNumero(enderecoDTO.getNumero());
						novoEndereco.setBairro(enderecoDTO.getBairro());
						novoEndereco.setCidade(enderecoDTO.getCidade());
						novoEndereco.setEstado(enderecoDTO.getEstado());
						novoEndereco.setCep(enderecoDTO.getCep());
						novoEndereco.setLatitude(enderecoDTO.getLatitude());
						novoEndereco.setLongitude(enderecoDTO.getLongitude());

						enderecosAtualizados.add(novoEndereco);
					}
				}
				cliente.getEndereco().retainAll(enderecosAtualizados);
				cliente.getEndereco().addAll(enderecosAtualizados);
			}
			cliente = repository.save(cliente);
			return new ClienteDTO(cliente);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}

}
