package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Indica;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IndicaDAO {

    public void adicionarIndicacao(Indica indicacao) throws SQLException {
        String sql = "INSERT INTO Indica (cpf_indicador, cpf_indicado) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, indicacao.getCpfIndicador());
            stmt.setString(2, indicacao.getCpfIndicado());
            stmt.executeUpdate();
        }
    }

    public void removerIndicacao(Indica indicacao) throws SQLException {
        String sql = "DELETE FROM Indica WHERE cpf_indicador = ? AND cpf_indicado = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, indicacao.getCpfIndicador());
            stmt.setString(2, indicacao.getCpfIndicado());
            stmt.executeUpdate();
        }
    }

    public List<Indica> listarIndicacoesPorCliente(String cpfIndicador) throws SQLException {
        List<Indica> lista = new ArrayList<>();
        String sql = "SELECT * FROM Indica WHERE cpf_indicador = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfIndicador);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Indica(
                        rs.getString("cpf_indicador"),
                        rs.getString("cpf_indicado")
                ));
            }
        }

        return lista;
    }
}
