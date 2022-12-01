package com.ifma.apifrete.domain.service;

import com.ifma.apifrete.domain.model.Cidade;
import com.ifma.apifrete.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> todos(){
        return cidadeRepository.findAll();
    }

    @Transactional
    public Cidade saveCidade(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Optional<Cidade> buscarCidade(Integer id) {
        return cidadeRepository.findById(id);
    }

    @Transactional
    public void deleteCidade(Integer id) {
        cidadeRepository.deleteById(id);
    }

}
