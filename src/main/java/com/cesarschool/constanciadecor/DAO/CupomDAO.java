package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Cupom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CupomDAO {

    public void addCupom(Cupom c) throws SQLException {
        String sql = "INSERT INTO cupom (codigo, dataInicio, dataVencimento, valorDesconto, descricao, condicoesUso) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getCodigo());
            stmt.setDate(2, Date.valueOf(c.getDataInicio()));
            stmt.setDate(3, Date.valueOf(c.getDataVencimento()));
            stmt.setDouble(4, c.getValorDesconto());
            stmt.setString(5, c.getDescricao());
            stmt.setString(6, c.getCondicoesUso());

            stmt.executeUpdate();
        }
    }

    public List<Cupom> getAll() throws SQLException {
        List<Cupom> lista = new ArrayList<>();
        String sql = "SELECT * FROM cupom";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cupom c = new Cupom(
                        rs.getInt("codigo"),
                        rs.getDate("dataInicio").toLocalDate(),
                        rs.getDate("dataVencimento").toLocalDate(),
                        rs.getDouble("valorDesconto"),
                        rs.getString("descricao"),
                        rs.getString("condicoesUso")
                );
                lista.add(c);
            }
        }

        return lista;
    }

    public Cupom getByCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM cupom WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cupom(
                        rs.getInt("codigo"),
                        rs.getDate("dataInicio").toLocalDate(),
                        rs.getDate("dataVencimento").toLocalDate(),
                        rs.getDouble("valorDesconto"),
                        rs.getString("descricao"),
                        rs.getString("condicoesUso")
                );
            }
        }

        return null;
    }

    public void update(Cupom c) throws SQLException {
        String sql = "UPDATE cupom SET dataInicio = ?, dataVencimento = ?, valorDesconto = ?, descricao = ?, condicoesUso = ? WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(c.getDataInicio()));
            stmt.setDate(2, Date.valueOf(c.getDataVencimento()));
            stmt.setDouble(3, c.getValorDesconto());
            stmt.setString(4, c.getDescricao());
            stmt.setString(5, c.getCondicoesUso());
            stmt.setInt(6, c.getCodigo());

            stmt.executeUpdate();
        }
    }

    public void delete(int codigo) throws SQLException {
        String sql = "DELETE FROM cupom WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }
}
