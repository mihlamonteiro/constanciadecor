package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.ClienteDAO;
import com.cesarschool.constanciadecor.DAO.TelefoneDAO;
import com.cesarschool.constanciadecor.model.Telefone;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/telefones")
public class TelefoneController {

    private final TelefoneDAO dao = new TelefoneDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Telefone telefone) {
        try {
            if (clienteDAO.getClienteByCpf(telefone.getCpfCliente()) == null) {
                return ResponseEntity.badRequest().body("Cliente não encontrado.");
            }
            dao.addTelefone(telefone);
            return ResponseEntity.status(201).body("Telefone adicionado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao adicionar telefone: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> remover(@RequestBody Telefone telefone) {
        try {
            dao.removeTelefone(telefone);
            return ResponseEntity.ok("Telefone removido com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao remover telefone: " + e.getMessage());
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<List<Telefone>> listarPorCliente(@PathVariable String cpf) {
        try {
            return ResponseEntity.ok(dao.listarPorCliente(cpf));
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
