package com.ifma.apifrete.domain.service;

import com.ifma.apifrete.domain.model.Frete;
import com.ifma.apifrete.domain.repository.FreteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FreteService {

    @Autowired
    private FreteRepository freteRepository;

    public List<Frete> todos() {
        return freteRepository.findAll();
    }

    @Transactional
    public Frete saveFrete(Frete frete) {
        return freteRepository.save(frete);
    }

    public Optional<Frete> buscarFrete(Integer freteId){
        return freteRepository.findById(freteId);
    }

    @Transactional
    public void deleteFrete(Integer freteId) {
        freteRepository.deleteById(freteId);
    }
}
