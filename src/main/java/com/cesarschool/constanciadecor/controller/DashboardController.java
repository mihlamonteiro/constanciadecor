// src/main/java/com/cesarschool/constanciadecor/controller/DashboardController.java
package com.cesarschool.constanciadecor.controller;

import com.cesarschool.constanciadecor.DAO.CompraAvaliaDAO;
import com.cesarschool.constanciadecor.dto.ComprasPorMesDTO;
import com.cesarschool.constanciadecor.dto.MediaAvaliacaoMensalDTO;
import com.cesarschool.constanciadecor.config.DatabaseConnection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.time.Month;
import java.util.*;

@RestController
@RequestMapping("/compras/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final CompraAvaliaDAO dao = new CompraAvaliaDAO();

    // --- Contagens legadas ---

    @GetMapping("/avaliacoes-mensais")
    public ResponseEntity<Map<String, Integer>> getAvaliacoesMensais() {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT MONTH(data_avaliacao) AS mes, COUNT(*) AS total "
                + "FROM avaliacao GROUP BY mes ORDER BY mes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(Month.of(rs.getInt("mes")).name(), rs.getInt("total"));
            }
            return ResponseEntity.ok(resultado);

        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/vendas-mensais")
    public ResponseEntity<Map<String, Integer>> getVendasMensais() {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT MONTH(data) AS mes, COUNT(*) AS total "
                + "FROM compra GROUP BY mes ORDER BY mes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                resultado.put(Month.of(rs.getInt("mes")).name(), rs.getInt("total"));
            }
            return ResponseEntity.ok(resultado);

        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/total-clientes")
    public ResponseEntity<Map<String, Integer>> getTotalClientes() {
        String sql = "SELECT COUNT(*) AS total FROM cliente";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Map<String, Integer> m = new HashMap<>();
                m.put("totalClientes", rs.getInt("total"));
                return ResponseEntity.ok(m);
            }
            return ResponseEntity.noContent().build();

        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/clientes-mais-indicaram")
    public ResponseEntity<List<Map<String, Object>>> getClientesMaisIndicaram() {
        String sql = "SELECT cpf_indicador, COUNT(*) AS total "
                + "FROM indica GROUP BY cpf_indicador "
                + "ORDER BY total DESC LIMIT 5";

        List<Map<String, Object>> lista = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> item = new HashMap<>();
                item.put("cpf", rs.getString("cpf_indicador"));
                item.put("total", rs.getInt("total"));
                lista.add(item);
            }
            return ResponseEntity.ok(lista);

        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    // --- Métricas por mês via DTOs ---

    @GetMapping("/compras-por-mes")
    public ResponseEntity<List<ComprasPorMesDTO>> getComprasPorMes(
            @RequestParam("ano") int ano) {
        try {
            List<ComprasPorMesDTO> medias = dao.getMediaComprasPorMes(ano);
            return ResponseEntity.ok(medias);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/avaliacoes-por-mes")
    public ResponseEntity<List<MediaAvaliacaoMensalDTO>> getAvaliacoesPorMes(
            @RequestParam("ano") int ano) {
        try {
            List<MediaAvaliacaoMensalDTO> medias = dao.getMediaAvaliacoesPorMes(ano);
            return ResponseEntity.ok(medias);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
