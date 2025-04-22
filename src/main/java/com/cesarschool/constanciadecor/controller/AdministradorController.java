package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.AdministradorAromasDAO;
import com.cesarschool.constanciadecor.model.AdministradorAromas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/administradores")
public class AdministradorController {

    private final AdministradorAromasDAO dao = new AdministradorAromasDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody AdministradorAromas adm) {
        try {
            dao.addAdministrador(adm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Administrador cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AdministradorAromas>> listar() {
        try {
            return ResponseEntity.ok(dao.getAllAdministradores());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<AdministradorAromas>> listarAtivos() {
        try {
            return ResponseEntity.ok(dao.listarAtivos());
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<AdministradorAromas> buscar(@PathVariable String cpf) {
        try {
            AdministradorAromas adm = dao.getAdministradorByCpf(cpf);
            if (adm != null) {
                return ResponseEntity.ok(adm);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody AdministradorAromas adm) {
        try {
            dao.updateAdministrador(adm);
            return ResponseEntity.ok("Administrador atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar: " + e.getMessage());
        }
    }

    @PutMapping("/desativar/{cpf}")
    public ResponseEntity<String> desativar(@PathVariable String cpf) {
        try {
            dao.desativarAdministrador(cpf);
            return ResponseEntity.ok("Administrador desativado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao desativar: " + e.getMessage());
        }
    }

    @PutMapping("/ativar/{cpf}")
    public ResponseEntity<String> ativar(@PathVariable String cpf) {
        try {
            dao.ativarAdministrador(cpf);
            return ResponseEntity.ok("Administrador ativado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao ativar: " + e.getMessage());
        }
    }
}
