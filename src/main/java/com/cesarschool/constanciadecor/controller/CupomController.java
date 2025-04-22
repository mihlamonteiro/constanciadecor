package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.CupomDAO;
import com.cesarschool.constanciadecor.model.Cupom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/cupons")
public class CupomController {

    private final CupomDAO dao = new CupomDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Cupom cupom) {
        try {
            dao.addCupom(cupom);
            return ResponseEntity.status(201).body("Cupom cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar cupom: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cupom>> listarTodos() {
        try {
            return ResponseEntity.ok(dao.getAll());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cupom> buscar(@PathVariable int codigo) {
        try {
            Cupom cupom = dao.getByCodigo(codigo);
            return (cupom != null) ? ResponseEntity.ok(cupom) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizar(@RequestBody Cupom cupom) {
        try {
            dao.update(cupom);
            return ResponseEntity.ok("Cupom atualizado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao atualizar cupom: " + e.getMessage());
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> deletar(@PathVariable int codigo) {
        try {
            dao.delete(codigo);
            return ResponseEntity.ok("Cupom excluído com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir cupom: " + e.getMessage());
        }
    }
}
