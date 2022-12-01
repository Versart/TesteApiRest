package com.ifma.apifrete.domain.repository;

import com.ifma.apifrete.domain.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeContaining(String nome);

    Optional<Cliente> findByTelefone(String telefone);

    Page<Cliente> findByNomeContaining(String nome, Pageable pageable);
}
