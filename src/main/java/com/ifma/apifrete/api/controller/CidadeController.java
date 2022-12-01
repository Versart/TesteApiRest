package com.ifma.apifrete.api.controller;

import com.ifma.apifrete.domain.model.Cidade;
import com.ifma.apifrete.domain.service.CidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<Cidade>> getAll(){
        return ResponseEntity.ok(cidadeService.todos());
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> findCidadeById(@PathVariable Integer cidadeId) {
        return cidadeService.buscarCidade(cidadeId).map(
            cidade -> ResponseEntity.ok(cidade)
        ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade saveCidade(@Valid @RequestBody Cidade cidade){
        return cidadeService.saveCidade(cidade);
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Void> deleteCidade(@PathVariable Integer cidadeId) {
        Optional<Cidade> cidadePesquisa = cidadeService.buscarCidade(cidadeId);
        if(cidadePesquisa.isPresent()){
            cidadeService.deleteCidade(cidadeId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<Cidade> alterarCidade(@PathVariable Integer cidadeId, @Valid @RequestBody Cidade cidade) {
        Optional<Cidade> cidadePesquisa = cidadeService.buscarCidade(cidadeId);
        if(cidadePesquisa.isPresent()){
            cidade.setId(cidadeId);
            Cidade cidadeAlterada = cidadeService.saveCidade(cidade);
            return ResponseEntity.ok(cidadeAlterada);
        }
        return ResponseEntity.notFound().build();
    }


}
