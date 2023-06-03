package com.renan.maxima.service;

import java.util.HashSet;
import java.util.Set;

import com.renan.maxima.dto.ClienteDTO;
import com.renan.maxima.dto.EnderecoDTO;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class ClienteServiceTest {
	
	@Autowired
	private ClienteService service;
	
	private ClienteDTO dto = new ClienteDTO();
	
	@Test
	@Order(1)
	void salvarCliente(){
		
		EnderecoDTO dto1 = new EnderecoDTO();
		dto1.setBairro("Vila Nova");
		dto1.setCidade("Goiânia");
		dto1.setLogradouro("Rua 207");
		dto1.setNumero("89");
		dto1.setPais("Brasil");
		dto1.setEstado("GO");
		dto1.setLatitude(40.7128);
		dto1.setLongitude(-74.0060);
		
		EnderecoDTO dto2 = new EnderecoDTO();
		dto2.setBairro("Bueno");
		dto2.setCidade("Goiânia");
		dto2.setPais("Brasil");
		dto2.setLogradouro("T2");
		dto2.setNumero("S/N");
		dto2.setEstado("GO");
		dto2.setLatitude(40.7128);
		dto2.setLongitude(-74.0060);
		
		Set<EnderecoDTO> enderecos = new HashSet<EnderecoDTO>();
		enderecos.add(dto1);
		enderecos.add(dto2);
		
		this.dto.setCnpj("11111111");
		this.dto.setCodCliente("123");
		this.dto.setNome("Teste");
		this.dto.setEnderecos(enderecos);
		
		service.salvarCliente(dto);
		
		
	}
	
	@Test
	@Order(2)
	void buscarTodosClientes() {
		Page<ClienteDTO> dto = service.buscarTodosOsClientesComFiltro("Tes", null, null, null);
		System.out.println(dto.toString());
	}
	
}
