package com.ifma.apifrete.api.controller;

import com.ifma.apifrete.domain.model.Frete;
import com.ifma.apifrete.domain.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fretes")
public class FreteController {

    @Autowired
    private FreteService freteService;

    @GetMapping
    public ResponseEntity<List<Frete>> getAll() {
        return ResponseEntity.ok(freteService.todos());
    }

    @GetMapping("/{freteId}")
    public ResponseEntity<Frete> findFreteById(@PathVariable Integer freteId) {
        return freteService.buscarFrete(freteId).map(
                frete -> ResponseEntity.ok(frete)
        ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Frete saveFrete(@Valid @RequestBody Frete frete) {
        return freteService.saveFrete(frete);
    }

    @DeleteMapping("/{freteId}")
    public ResponseEntity<Void> deleteFrete(@PathVariable Integer freteId) {
        Optional<Frete> fretePesquisa = freteService.buscarFrete(freteId);

        if(fretePesquisa.isPresent()){
            freteService.deleteFrete(freteId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/freteId")
    public ResponseEntity<Frete> alterarFrete(@PathVariable Integer freteId, @Valid @RequestBody Frete frete){
        Optional<Frete> fretePesquisa = freteService.buscarFrete(freteId);

        if(fretePesquisa.isPresent()) {
            frete.setId(freteId);
            Frete freteAlterado = freteService.saveFrete(frete);
            return ResponseEntity.ok(freteAlterado);
        }
        return ResponseEntity.notFound().build();
    }


}
