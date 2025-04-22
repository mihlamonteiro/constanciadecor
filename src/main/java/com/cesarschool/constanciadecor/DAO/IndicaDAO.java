package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Indica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndicaDAO {

    public void adicionar(Indica i) throws SQLException {
        String sql = "INSERT INTO indica (cpf_indicador, cpf_indicado) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, i.getCpfIndicador());
            stmt.setString(2, i.getCpfIndicado());
            stmt.executeUpdate();
        }
    }

    public List<Indica> listarTodos() throws SQLException {
        List<Indica> lista = new ArrayList<>();
        String sql = "SELECT * FROM indica";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Indica(
                        rs.getString("cpf_indicador"),
                        rs.getString("cpf_indicado")
                ));
            }
        }
        return lista;
    }

    public void remover(Indica i) throws SQLException {
        String sql = "DELETE FROM indica WHERE cpf_indicador = ? AND cpf_indicado = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, i.getCpfIndicador());
            stmt.setString(2, i.getCpfIndicado());
            stmt.executeUpdate();
        }
    }

    public boolean existeIndicacao(String indicador, String indicado) throws SQLException {
        String sql = "SELECT 1 FROM indica WHERE cpf_indicador = ? AND cpf_indicado = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, indicador);
            stmt.setString(2, indicado);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}
