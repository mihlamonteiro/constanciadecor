package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.AdministradorAromasDAO;
import com.cesarschool.constanciadecor.DAO.ProdutoDAO;
import com.cesarschool.constanciadecor.model.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoDAO dao = new ProdutoDAO();
    private final AdministradorAromasDAO administradorDAO = new AdministradorAromasDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Produto produto) {
        try {
            if (produto.getCpfAdministrador() == null || administradorDAO.getAdministradorByCpf(produto.getCpfAdministrador()) == null) {
                return ResponseEntity.badRequest().body("Administrador responsável não encontrado.");
            }

            dao.addProduto(produto);
            return ResponseEntity.status(201).body("Produto cadastrado com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        try {
            return ResponseEntity.ok(dao.getAllProdutos());
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> buscarPorCodigo(@PathVariable int codigo) {
        try {
            Produto produto = dao.getProdutoByCodigo(codigo);
            return (produto != null) ? ResponseEntity.ok(produto) : ResponseEntity.notFound().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> deletar(@PathVariable int codigo) {
        try {
            dao.deleteProduto(codigo);
            return ResponseEntity.ok("Produto excluído com sucesso!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao excluir produto: " + e.getMessage());
        }
    }
}
