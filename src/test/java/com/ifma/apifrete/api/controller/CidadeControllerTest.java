package com.ifma.apifrete.api.controller;
import com.ifma.apifrete.domain.model.Cidade;

import com.ifma.apifrete.domain.repository.CidadeRepository;
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
public class CidadeControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cidade cidade;

    private String nome= "São Luís";
    private String uf = "MA";
    private BigDecimal taxa = new BigDecimal(10);

    @BeforeEach
    public void start(){
        cidade = new Cidade();
        cidade.setNome(nome);
        cidade.setUf(uf);
        cidade.setTaxa(taxa);

        cidadeRepository.save(cidade);
    }

    @AfterEach
    public void end() {
        cidadeRepository.deleteAll();
    }

    @Test
    public void deveMostrarTodasAsCidades() {
        ResponseEntity<String> response = testRestTemplate.exchange(
                "/cidades", HttpMethod.GET,null,String.class
        );
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void nãoDeveSalvarCidadeComErroDeValidacao() {
        Cidade cidade1 = new Cidade();
        cidade1.setNome(nome);
        cidade1.setUf(uf);
        cidade1.setTaxa(null);
        HttpEntity<Cidade> httpEntity = new HttpEntity<>(cidade1);

        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                "/cidades", HttpMethod.POST, httpEntity, new ParameterizedTypeReference<List<String>>() {
                }
        );
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("A taxa deve ser preenchida"));
    }

    @Test
    public void deveRemoverUmaCidade() {
        List<Cidade> cidadeList = cidadeRepository.findAll();
        assertEquals(1,cidadeList.size());
        cidadeRepository.deleteById(cidade.getId());
        cidadeList = cidadeRepository.findAll();
        assertEquals(0,cidadeList.size());
    }
}
