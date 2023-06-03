package com.renan.maxima.repository;

import com.renan.maxima.dto.ClienteDTO;
import com.renan.maxima.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	@Query("SELECT new com.renan.maxima.dto.ClienteDTO(c) FROM Cliente c "
            + "WHERE (:nome IS NULL OR c.nome LIKE %:nome%) "
            + "AND (:cod IS NULL OR c.codCliente LIKE %:cod%) "
            + "AND (:cnpj IS NULL OR c.cnpj LIKE %:cnpj%)")
	Page<ClienteDTO> buscarTodosClientesComFiltro(@Param("nome") String nome, @Param("cod") String codCliente,
			@Param("cnpj") String cnpj, Pageable pageable);
}
