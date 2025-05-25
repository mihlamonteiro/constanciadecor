package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.ClienteDAO;
import com.cesarschool.constanciadecor.DAO.TelefoneDAO;
import com.cesarschool.constanciadecor.model.Cliente;
import com.cesarschool.constanciadecor.model.Telefone;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteDAO clienteDAO = new ClienteDAO();

    // Adicionar cliente
    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Cliente cliente) {
        try {
            clienteDAO.addCliente(cliente);
            return ResponseEntity.status(201).body("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    // Listar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        try {
            return ResponseEntity.ok(clienteDAO.getAllClientes());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar cliente por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        try {
            Cliente cliente = clienteDAO.getClienteByCpf(cpf);
            return (cliente != null) ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Atualizar cliente
    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody Cliente cliente) {
        try {
            clienteDAO.updateCliente(cliente);
            return ResponseEntity.ok("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    // Deletar cliente
    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> excluir(@PathVariable String cpf) {
        try {
            clienteDAO.deleteCliente(cpf);
            return ResponseEntity.ok("Cliente excluído com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    // Listar CPFs
    @GetMapping("/cpfs")
    public ResponseEntity<List<String>> listarCpfs() {
        try {
            List<String> cpfs = clienteDAO.getAllClientes().stream()
                    .map(Cliente::getCpf)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(cpfs);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Listar endereços formatados
    @GetMapping("/enderecos")
    public ResponseEntity<List<String>> listarEnderecosClientes() {
        try {
            List<String> enderecos = clienteDAO.getAllClientes().stream()
                    .map(c -> String.format("%s, %s - %s, %s - %s",
                            c.getRua(), c.getNumero(), c.getBairro(), c.getCidade(), c.getCep()))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(enderecos);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // --- Telefones ---
    private final TelefoneDAO telefoneDAO = new TelefoneDAO();

    @GetMapping("/{cpf}/telefones")
    public ResponseEntity<List<String>> listarTelefones(@PathVariable String cpf) {
        try {
            List<String> telefones = telefoneDAO.listarPorCliente(cpf).stream()
                    .map(Telefone::getTelefone)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(telefones);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{cpf}/telefones")
    public ResponseEntity<String> atualizarTelefones(@PathVariable String cpf, @RequestBody List<String> novos) {
        try {
            // Remove telefones antigos
            List<Telefone> antigos = telefoneDAO.listarPorCliente(cpf);
            for (Telefone t : antigos) {
                telefoneDAO.removeTelefone(t);
            }
            // Adiciona novos
            for (String numero : novos) {
                telefoneDAO.addTelefone(new Telefone(cpf, numero));
            }
            return ResponseEntity.ok("Telefones atualizados com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar telefones: " + e.getMessage());
        }
    }
}
