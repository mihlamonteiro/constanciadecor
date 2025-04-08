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
