package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.ClienteDAO;
import com.cesarschool.constanciadecor.model.Cliente;
import org.springframework.http.HttpStatus;
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
            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        try {
            List<Cliente> lista = dao.getAllClientes();
            return ResponseEntity.ok(lista);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Cliente> buscarPorCpf(@PathVariable String cpf) {
        try {
            Cliente cliente = dao.getClienteByCpf(cpf);
            if (cliente != null) {
                return ResponseEntity.ok(cliente);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody Cliente cliente) {
        try {
            dao.updateCliente(cliente);
            return ResponseEntity.ok("Cliente atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar cliente: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletar(@PathVariable String cpf) {
        try {
            dao.deleteCliente(cpf);
            return ResponseEntity.ok("Cliente removido com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao remover cliente: " + e.getMessage());
        }
    }
}
