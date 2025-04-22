package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.FuncionarioDAO;
import com.cesarschool.constanciadecor.model.Funcionario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioDAO dao = new FuncionarioDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Funcionario funcionario) {
        try {
            dao.addFuncionario(funcionario);
            return ResponseEntity.status(201).body("Funcionário cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        try {
            return ResponseEntity.ok(dao.getAll());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Funcionario> buscarPorCpf(@PathVariable String cpf) {
        try {
            Funcionario funcionario = dao.getByCpf(cpf);
            return (funcionario != null) ? ResponseEntity.ok(funcionario) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> editar(@RequestBody Funcionario funcionario) {
        try {
            dao.update(funcionario);
            return ResponseEntity.ok("Funcionário atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    @PutMapping("/desativar/{cpf}")
    public ResponseEntity<String> desativar(@PathVariable String cpf) {
        try {
            dao.desativar(cpf);
            return ResponseEntity.ok("Funcionário desativado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao desativar funcionário: " + e.getMessage());
        }
    }

    @PutMapping("/ativar/{cpf}")
    public ResponseEntity<String> ativar(@PathVariable String cpf) {
        try {
            dao.ativar(cpf);
            return ResponseEntity.ok("Funcionário ativado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao ativar funcionário: " + e.getMessage());
        }
    }
}
