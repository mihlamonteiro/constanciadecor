package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.AdministradorAromasDAO;
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
    private final AdministradorAromasDAO administradorDAO = new AdministradorAromasDAO();

    // POST: cadastrar funcionário com validação do administrador
    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Funcionario func) {
        try {
            // Valida se o CPF do administrador foi informado
            if (func.getCpfAdministrador() == null || func.getCpfAdministrador().isBlank()) {
                return ResponseEntity.badRequest().body("CPF do administrador é obrigatório.");
            }

            // Valida se o administrador existe
            if (administradorDAO.getAdministradorByCpf(func.getCpfAdministrador()) == null) {
                return ResponseEntity.badRequest().body("CPF do administrador informado não existe.");
            }

            // Insere o funcionário
            dao.addFuncionario(func);
            return ResponseEntity.status(201).body("Funcionário cadastrado com sucesso!");

        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar funcionário: " + e.getMessage());
        }
    }

    // GET: listar todos os funcionários
    @GetMapping
    public ResponseEntity<List<Funcionario>> listarTodos() {
        try {
            List<Funcionario> funcionarios = dao.getAllFuncionarios();
            return ResponseEntity.ok(funcionarios);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET: buscar funcionário por CPF
    @GetMapping("/{cpf}")
    public ResponseEntity<Funcionario> buscarPorCpf(@PathVariable String cpf) {
        try {
            Funcionario func = dao.getFuncionarioByCpf(cpf);
            return (func != null) ? ResponseEntity.ok(func) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody Funcionario func) {
        try {
            dao.updateFuncionario(func);
            return ResponseEntity.ok("Funcionário atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar funcionário: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deletar(@RequestBody Funcionario func) {
        try {
            String cpf = func.getCpf();
            if (cpf == null || cpf.isBlank()) {
                return ResponseEntity.badRequest().body("CPF do funcionário é obrigatório.");
            }

            dao.deleteFuncionario(cpf);
            return ResponseEntity.ok("Funcionário removido com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir: " + e.getMessage());
        }
    }
    
}
