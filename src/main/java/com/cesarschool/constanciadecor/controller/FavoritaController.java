package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.FavoritaDAO;
import com.cesarschool.constanciadecor.model.Favorita;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/favoritos")
public class FavoritaController {

    private final FavoritaDAO dao = new FavoritaDAO();

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody Favorita favorita) {
        try {
            dao.addFavorito(favorita);
            return ResponseEntity.status(201).body("Produto adicionado aos favoritos!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao favoritar: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<String> remover(@RequestBody Favorita favorita) {
        try {
            dao.removeFavorito(favorita);
            return ResponseEntity.ok("Produto removido dos favoritos!");
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().body("Erro ao remover favorito: " + e.getMessage());
        }
    }

    @GetMapping("/{cpfCliente}")
    public ResponseEntity<List<Favorita>> listarPorCliente(@PathVariable String cpfCliente) {
        try {
            List<Favorita> favoritos = dao.listarFavoritosPorCliente(cpfCliente);
            return ResponseEntity.ok(favoritos);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
