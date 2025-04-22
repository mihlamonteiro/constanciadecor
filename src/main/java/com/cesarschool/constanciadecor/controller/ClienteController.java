package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.ClienteDAO;
import com.cesarschool.constanciadecor.model.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteDAO dao = new ClienteDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Cliente cliente) {
        try {
            dao.addCliente(cliente);
            return ResponseEntity.status(201).body("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        try {
            return ResponseEntity.ok(dao.getAllClientes());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        try {
            Cliente cliente = dao.getClienteByCpf(cpf);
            return (cliente != null) ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody Cliente cliente) {
        try {
            dao.updateCliente(cliente);
            return ResponseEntity.ok("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> excluir(@PathVariable String cpf) {
        try {
            dao.deleteCliente(cpf);
            return ResponseEntity.ok("Cliente excluído com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir cliente: " + e.getMessage());
        }
    }
}
