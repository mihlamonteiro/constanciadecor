package com.cesarschool.constanciadecor.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.sql.*;
import java.time.Month;
import java.util.*;

import com.cesarschool.constanciadecor.config.DatabaseConnection;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/avaliacoes-mensais")
    public ResponseEntity<Map<String, Integer>> getAvaliacoesMensais() {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT MONTH(data_avaliacao) AS mes, COUNT(*) AS total FROM avaliacao GROUP BY mes ORDER BY mes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int mes = rs.getInt("mes");
                resultado.put(Month.of(mes).name(), rs.getInt("total"));
            }

            return ResponseEntity.ok(resultado);
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/vendas-mensais")
    public ResponseEntity<Map<String, Integer>> getVendasMensais() {
        Map<String, Integer> resultado = new LinkedHashMap<>();
        String sql = "SELECT MONTH(data) AS mes, COUNT(*) AS total FROM compra GROUP BY mes ORDER BY mes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int mes = rs.getInt("mes");
                resultado.put(Month.of(mes).name(), rs.getInt("total"));
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
                Map<String, Integer> resultado = new HashMap<>();
                resultado.put("totalClientes", rs.getInt("total"));
                return ResponseEntity.ok(resultado);
            }

            return ResponseEntity.noContent().build();
        } catch (SQLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/clientes-mais-indicaram")
    public ResponseEntity<List<Map<String, Object>>> getClientesQueMaisIndicaram() {
        String sql = "SELECT cpf_indicador, COUNT(*) AS total FROM indica GROUP BY cpf_indicador ORDER BY total DESC LIMIT 5";

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
}
