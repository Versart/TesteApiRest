package com.ifma.apifrete.domain.service;

import com.ifma.apifrete.domain.exception.NegocioException;
import com.ifma.apifrete.domain.model.Cliente;
import com.ifma.apifrete.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> todos() {
        return  clienteRepository.findAll();
    }
    @Transactional
    public Cliente saveCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarCliente(Integer id) {
        return clienteRepository.findById(id);
    }
    @Transactional
    public void deleteCliente(Integer id) {
        clienteRepository.deleteById(id);
    }




}
