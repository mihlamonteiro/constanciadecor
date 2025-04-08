package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void addFuncionario(Funcionario func) throws SQLException {
        String sql = "INSERT INTO Funcionario (cpf, nome, cargo, data_nasc, telefone, cep, bairro, numero, rua, cpf_administrador) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, func.getCpf());
            stmt.setString(2, func.getNome());
            stmt.setString(3, func.getCargo());
            stmt.setDate(4, Date.valueOf(func.getDataNasc()));
            stmt.setString(5, func.getTelefone());
            stmt.setString(6, func.getCep());
            stmt.setString(7, func.getBairro());
            stmt.setString(8, func.getNumero());
            stmt.setString(9, func.getRua());
            stmt.setString(10, func.getCpfAdministrador());

            stmt.executeUpdate();
        }
    }

    public void updateFuncionario(Funcionario func) throws SQLException {
        String sql = "UPDATE Funcionario SET nome = ?, cargo = ?, data_nasc = ?, telefone = ?, cep = ?, bairro = ?, numero = ?, rua = ?, cpf_administrador = ? WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, func.getNome());
            stmt.setString(2, func.getCargo());
            stmt.setDate(3, Date.valueOf(func.getDataNasc()));
            stmt.setString(4, func.getTelefone());
            stmt.setString(5, func.getCep());
            stmt.setString(6, func.getBairro());
            stmt.setString(7, func.getNumero());
            stmt.setString(8, func.getRua());
            stmt.setString(9, func.getCpfAdministrador());
            stmt.setString(10, func.getCpf());

            stmt.executeUpdate();
        }
    }


    public List<Funcionario> getAllFuncionarios() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Funcionario f = new Funcionario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("cargo"),
                        rs.getDate("data_nasc").toLocalDate(),
                        rs.getString("telefone"),
                        rs.getString("cep"),
                        rs.getString("bairro"),
                        rs.getString("numero"),
                        rs.getString("rua"),
                        rs.getString("cpf_administrador")
                );
                lista.add(f);
            }
        }

        return lista;
    }

    public Funcionario getFuncionarioByCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM Funcionario WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Funcionario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("cargo"),
                        rs.getDate("data_nasc").toLocalDate(),
                        rs.getString("telefone"),
                        rs.getString("cep"),
                        rs.getString("bairro"),
                        rs.getString("numero"),
                        rs.getString("rua"),
                        rs.getString("cpf_administrador")
                );
            }
        }

        return null;
    }

    public void deleteFuncionario(String cpf) throws SQLException {
        String sql = "DELETE FROM Funcionario WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }
}
