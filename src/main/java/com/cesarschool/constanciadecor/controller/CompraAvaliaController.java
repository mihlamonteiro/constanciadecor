// src/main/java/com/cesarschool/constanciadecor/controller/CompraAvaliaController.java
package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.CompraAvaliaDAO;
import com.cesarschool.constanciadecor.model.CompraAvalia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
public class CompraAvaliaController {

    private final CompraAvaliaDAO dao = new CompraAvaliaDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody CompraAvalia compra) {
        try {
            dao.addCompra(compra);
            return ResponseEntity.status(201).body("Compra registrada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao registrar compra: " + e.getMessage());
        }
    }

    @PutMapping("/avaliar/{numero}")
    public ResponseEntity<String> avaliar(@PathVariable int numero,
                                          @RequestBody CompraAvalia dados) {
        try {
            dao.avaliarCompra(numero, dados);
            return ResponseEntity.ok("Avaliação registrada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao avaliar: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CompraAvalia>> listarTodas() {
        try {
            return ResponseEntity.ok(dao.getAllCompras());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{numero}")
    public ResponseEntity<CompraAvalia> buscarPorNumero(@PathVariable int numero) {
        try {
            CompraAvalia compra = dao.getCompraByNumero(numero);
            return (compra != null)
                    ? ResponseEntity.ok(compra)
                    : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{numero}")
    public ResponseEntity<String> deletar(@PathVariable int numero) {
        try {
            dao.deleteCompra(numero);
            return ResponseEntity.ok("Compra removida com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError()
                    .body("Erro ao excluir: " + e.getMessage());
        }
    }

    // **Endpoints de dashboard foram removidos daqui** para evitar mapeamento duplicado.
}
