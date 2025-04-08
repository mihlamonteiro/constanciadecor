package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Cupom;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CupomDAO {

    public void addCupom(Cupom cupom) throws SQLException {
        String sql = "INSERT INTO Cupom (codigo, dataInicio, dataVencimento, valorDesconto, descricao, condicoesUso) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cupom.getCodigo());
            stmt.setDate(2, Date.valueOf(cupom.getDataInicio()));
            stmt.setDate(3, Date.valueOf(cupom.getDataVencimento()));
            stmt.setDouble(4, cupom.getValorDesconto());
            stmt.setString(5, cupom.getDescricao());
            stmt.setString(6, cupom.getCondicoesUso());

            stmt.executeUpdate();
        }
    }

    public List<Cupom> getAllCupons() throws SQLException {
        List<Cupom> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cupom";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cupom cupom = new Cupom(
                        rs.getInt("codigo"),
                        rs.getDate("dataInicio").toLocalDate(),
                        rs.getDate("dataVencimento").toLocalDate(),
                        rs.getDouble("valorDesconto"),
                        rs.getString("descricao"),
                        rs.getString("condicoesUso")
                );
                lista.add(cupom);
            }
        }

        return lista;
    }

    public Cupom getCupomByCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM Cupom WHERE codigo = ?";

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

    public void deleteCupom(int codigo) throws SQLException {
        String sql = "DELETE FROM Cupom WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }
}
