package com.ifma.apifrete.api.controller;

import com.ifma.apifrete.domain.model.Cliente;
import com.ifma.apifrete.domain.repository.ClienteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    private String nome= "Maria";
    private String endereco = "rua B";
    private String telefone = "99999999";

    @BeforeEach
    public void start() {
        cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);

        clienteRepository.save(cliente);
    }

    @AfterEach
    public void end() {
        clienteRepository.deleteAll();
    }

    @Test
    public void deveMostrarTodosOsClientes() {
        ResponseEntity<String> response = testRestTemplate.exchange(
          "/clientes",HttpMethod.GET,null,String.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void naoDeveSalvarClienteComErroDeValidacao(){
        Cliente cliente = new Cliente();
        cliente.setNome(null);
        cliente.setEndereco("Rua A");
        cliente.setTelefone("989898");
        HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente);

        ResponseEntity<List<String>> response = testRestTemplate
                .exchange("/clientes", HttpMethod.POST, httpEntity,
                        new ParameterizedTypeReference<List<String>>() {
                        });

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("O nome deve ser preenchido"));
    }

    @Test
    public void deveRemoverUmCliente() {
         List<Cliente> clienteList = clienteRepository.findAll();
        assertEquals(1,clienteList.size());
        clienteRepository.deleteById(cliente.getId());
        clienteList = clienteRepository.findAll();
        assertEquals(0,clienteList.size());
    }
}
