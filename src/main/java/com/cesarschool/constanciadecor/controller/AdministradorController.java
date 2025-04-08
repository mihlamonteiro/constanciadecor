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

    // POST: cadastrar um novo administrador
    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody AdministradorAromas adm) {
        try {
            dao.addAdministrador(adm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Administrador cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar: " + e.getMessage());
        }
    }

    // GET: listar todos os administradores
    @GetMapping
    public ResponseEntity<List<AdministradorAromas>> listar() {
        try {
            List<AdministradorAromas> lista = dao.getAllAdministradores();
            return ResponseEntity.ok(lista);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // GET: buscar administrador por CPF
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

    // PUT: atualizar dados de um administrador
    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody AdministradorAromas adm) {
        try {
            dao.updateAdministrador(adm);
            return ResponseEntity.ok("Administrador atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar: " + e.getMessage());
        }
    }

    // DELETE: excluir administrador pelo CPF
    @DeleteMapping("/{cpf}")
    public ResponseEntity<String> deletar(@PathVariable String cpf) {
        try {
            dao.deleteAdministrador(cpf);
            return ResponseEntity.ok("Administrador excluído com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir: " + e.getMessage());
        }
    }
}
