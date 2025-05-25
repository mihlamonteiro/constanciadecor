package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Telefone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDAO {

    public void addTelefone(Telefone telefone) throws SQLException {
        String sql = "INSERT INTO Telefone (cpf_cliente, telefone) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, telefone.getCpfCliente());
            stmt.setString(2, telefone.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void removeTelefone(Telefone telefone) throws SQLException {
        String sql = "DELETE FROM Telefone WHERE cpf_cliente = ? AND telefone = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, telefone.getCpfCliente());
            stmt.setString(2, telefone.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void updateTelefonesPorCliente(String cpfCliente, List<String> novosTelefones) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Remover todos os telefones atuais do cliente
            String deleteSql = "DELETE FROM Telefone WHERE cpf_cliente = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setString(1, cpfCliente);
                stmt.executeUpdate();
            }

            // 2. Inserir os novos telefones
            String insertSql = "INSERT INTO Telefone (cpf_cliente, telefone) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                for (String telefone : novosTelefones) {
                    stmt.setString(1, cpfCliente);
                    stmt.setString(2, telefone);
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public List<Telefone> listarPorCliente(String cpfCliente) throws SQLException {
        List<Telefone> lista = new ArrayList<>();
        String sql = "SELECT * FROM Telefone WHERE cpf_cliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpfCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Telefone(
                        rs.getString("cpf_cliente"),
                        rs.getString("telefone")
                ));
            }
        }

        return lista;
    }
}
