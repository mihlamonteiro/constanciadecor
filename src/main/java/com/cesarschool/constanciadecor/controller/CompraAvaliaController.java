package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.dto.CompraDetalhadaDTO;
import com.cesarschool.constanciadecor.dto.ComprasPorMesDTO;
import com.cesarschool.constanciadecor.DAO.CompraAvaliaDAO;
import com.cesarschool.constanciadecor.model.CompraAvalia;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/compras")
public class CompraAvaliaController {

    private final CompraAvaliaDAO dao = new CompraAvaliaDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody CompraAvalia compra) {
        try {
            dao.addCompra(compra);
            return ResponseEntity.status(201).body("Compra registrada com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao registrar compra: " + e.getMessage());
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
    @GetMapping("/cliente/{cpf}")
    public ResponseEntity<List<CompraDetalhadaDTO>> listarComprasPorCliente(@PathVariable String cpf) {
        try {
            List<CompraDetalhadaDTO> compras = dao.listarComprasPorCliente(cpf);
            return ResponseEntity.ok(compras);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/{numero}")
    public ResponseEntity<CompraAvalia> buscarPorNumero(@PathVariable int numero) {
        try {
            CompraAvalia compra = dao.getCompraByNumero(numero);
            return (compra != null) ? ResponseEntity.ok(compra) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/dashboard/compras-por-mes")
    public ResponseEntity<List<ComprasPorMesDTO>> comprasPorMes() {
        try {
            return ResponseEntity.ok(dao.getComprasPorMes());
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
            return ResponseEntity.internalServerError().body("Erro ao excluir: " + e.getMessage());
        }
    }
}
