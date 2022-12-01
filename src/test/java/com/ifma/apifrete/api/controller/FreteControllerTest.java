package com.ifma.apifrete.api.controller;

import com.ifma.apifrete.domain.model.Cidade;
import com.ifma.apifrete.domain.model.Cliente;
import com.ifma.apifrete.domain.model.Frete;
import com.ifma.apifrete.domain.repository.CidadeRepository;
import com.ifma.apifrete.domain.repository.ClienteRepository;
import com.ifma.apifrete.domain.repository.FreteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FreteControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    private Frete frete;

    private Cidade cidade;

    private Cliente cliente;


    @BeforeEach
    public void start() {
        cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setUf("SP");
        cidade.setTaxa(new BigDecimal(10));
        cidadeRepository.save(cidade);

        cliente = new Cliente();
        cliente.setNome("Pedro");
        cliente.setEndereco("Rua X");
        cliente.setTelefone("99999999");
        clienteRepository.save(cliente);

        frete = new Frete();
        frete.setPeso(3f);
        frete.setValor(cidade.getTaxa());
        frete.setCidade(cidade);
        frete.setCliente(cliente);
        frete.setDescricao("pacote");



        freteRepository.save(frete);
    }

    @AfterEach
    public void end() {
        freteRepository.deleteAll();
    }

    @Test
    public void deveMostrarTodosOsFretes() {
        ResponseEntity<String> response = testRestTemplate.exchange(
                "/fretes", HttpMethod.GET,null,String.class
        );

        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void naoDeveSalvarFreteComErroDeValidacao() {
        Frete frete = new Frete();
        frete.setPeso(3f);
        frete.setValor(cidade.getTaxa());
        frete.setCidade(null);
        frete.setCliente(cliente);
        frete.setDescricao("pacote");

        HttpEntity<Frete> httpEntity = new HttpEntity<>(frete);
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                "/fretes", HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<String>>() {
                }
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("A cidade deve ser preenchida"));

    }

    @Test
    public void deveRemoverUmFrete() {
        List<Frete> freteList = freteRepository.findAll();
        assertEquals(1,freteList.size());
        freteRepository.deleteById(frete.getId());
        freteList = freteRepository.findAll();
        assertEquals(0,freteList.size());
    }

}
