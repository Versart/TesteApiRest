package com.ifma.apifrete.api.controller;

import com.ifma.apifrete.domain.model.Cliente;
import com.ifma.apifrete.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.ok(clienteService.todos());
    }
    @GetMapping("/{clienteId}")
    public ResponseEntity<Cliente> findClienteById(@PathVariable Integer clienteId) {
        return clienteService.buscarCliente(clienteId).map(
                cliente -> ResponseEntity.ok(cliente)
        ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente saveCliente(@Valid @RequestBody Cliente cliente) {
        return clienteService.saveCliente(cliente);
    }

    @DeleteMapping("/{clienteId}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Integer clienteId) {
        Optional<Cliente> clientePesquisa = clienteService.buscarCliente(clienteId);
        if(clientePesquisa.isPresent()){
            clienteService.deleteCliente(clienteId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{clienteId}")
    public ResponseEntity<Cliente> alterarCliente(@PathVariable Integer clienteId, @Valid @RequestBody Cliente cliente) {
        Optional<Cliente> clienteBuscado = clienteService.buscarCliente(clienteId);
        if(clienteBuscado.isPresent()) {
            cliente.setId(clienteId);
            Cliente clienteAlterado = clienteService.saveCliente(cliente);
            return ResponseEntity.ok(clienteAlterado);
        }
        return ResponseEntity.notFound().build();
    }


}
