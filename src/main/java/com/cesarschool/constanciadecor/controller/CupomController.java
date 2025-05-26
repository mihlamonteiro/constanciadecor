package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.CupomDAO;
import com.cesarschool.constanciadecor.model.Cupom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/cupons")
@CrossOrigin(origins = "*")
public class CupomController {

    private final CupomDAO dao = new CupomDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Cupom cupom) {
        try {
            dao.addCupom(cupom);
            return ResponseEntity.status(201).body("Cupom adicionado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao adicionar cupom: " + e.getMessage());
        }
    }

    @GetMapping("/{numeroCompra}")
    public ResponseEntity<Cupom> buscarPorCompra(@PathVariable int numeroCompra) {
        try {
            Cupom cupom = dao.getCupomByCompra(numeroCompra);
            return (cupom != null) ? ResponseEntity.ok(cupom) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Cupom>> listarTodos() {
        try {
            return ResponseEntity.ok(dao.getAllCupons());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @DeleteMapping("/{numeroCompra}")
    public ResponseEntity<String> deletar(@PathVariable int numeroCompra) {
        try {
            dao.deleteCupom(numeroCompra);
            return ResponseEntity.ok("Cupom deletado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao deletar cupom: " + e.getMessage());
        }
    }
}