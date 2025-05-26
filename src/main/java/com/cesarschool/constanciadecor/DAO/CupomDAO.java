package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Cupom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


public class CupomDAO {

    public void addCupom(Cupom cupom) throws SQLException {
        String sql = "INSERT INTO cupom (codigo, dataInicio, dataVencimento, valorDesconto, descricao, condicoesUso) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cupom.getCodigo());
            stmt.setDate(2, java.sql.Date.valueOf(cupom.getDataInicio()));
            stmt.setDate(3, java.sql.Date.valueOf(cupom.getDataVencimento()));
            stmt.setDouble(4, cupom.getValorDesconto());
            stmt.setString(5, cupom.getDescricao());
            stmt.setString(6, cupom.getCondicoesUso());
            stmt.executeUpdate();
        }
    }

    public Cupom getCupomByCompra(int numeroCompra) throws SQLException {
        String sql = "SELECT * FROM cupom WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numeroCompra);
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

    public List<Cupom> getAllCupons() throws SQLException {
        List<Cupom> cupons = new ArrayList<>();
        String sql = "SELECT * FROM cupom";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cupons.add(new Cupom(
                        rs.getInt("codigo"),
                        rs.getDate("dataInicio").toLocalDate(),
                        rs.getDate("dataVencimento").toLocalDate(),
                        rs.getDouble("valorDesconto"),
                        rs.getString("descricao"),
                        rs.getString("condicoesUso")
                ));
            }
        }
        return cupons;
    }


    public void deleteCupom(int numeroCompra) throws SQLException {
        String sql = "DELETE FROM cupom WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numeroCompra);
            stmt.executeUpdate();
        }
    }
}
