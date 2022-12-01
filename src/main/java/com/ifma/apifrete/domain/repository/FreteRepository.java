package com.ifma.apifrete.domain.repository;


import com.ifma.apifrete.domain.model.Frete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Integer> {

    List<Frete> findByDescricaoContaining(String descricao);


    Page<Frete> findByDescricaoContaining(String descricao , Pageable pageable);
}
