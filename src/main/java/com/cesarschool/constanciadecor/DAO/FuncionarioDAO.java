package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Funcionario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void addFuncionario(Funcionario f) throws SQLException {
        String sql = "INSERT INTO funcionario (cpf, nome, cargo, data_nasc, telefone, cep, bairro, numero, rua, cpf_administrador, ativo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getCpf());
            stmt.setString(2, f.getNome());
            stmt.setString(3, f.getCargo());
            stmt.setDate(4, Date.valueOf(f.getData_nasc()));
            stmt.setString(5, f.getTelefone());
            stmt.setString(6, f.getCep());
            stmt.setString(7, f.getBairro());
            stmt.setString(8, f.getNumero());
            stmt.setString(9, f.getRua());
            stmt.setString(10, f.getCpf_administrador());
            stmt.setBoolean(11, f.isAtivo());

            stmt.executeUpdate();
        }
    }

    public List<Funcionario> getAll() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";

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
                        rs.getString("cpf_administrador"),
                        rs.getBoolean("ativo")
                );
                lista.add(f);
            }
        }

        return lista;
    }

    public Funcionario getByCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE cpf = ?";

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
                        rs.getString("cpf_administrador"),
                        rs.getBoolean("ativo")
                );
            }
        }

        return null;
    }

    public void update(Funcionario f) throws SQLException {
        String sql = "UPDATE funcionario SET nome = ?, cargo = ?, data_nasc = ?, telefone = ?, cep = ?, bairro = ?, numero = ?, rua = ?, cpf_administrador = ?, ativo = ? WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getCargo());
            stmt.setDate(3, Date.valueOf(f.getData_nasc()));
            stmt.setString(4, f.getTelefone());
            stmt.setString(5, f.getCep());
            stmt.setString(6, f.getBairro());
            stmt.setString(7, f.getNumero());
            stmt.setString(8, f.getRua());
            stmt.setString(9, f.getCpf_administrador());
            stmt.setBoolean(10, f.isAtivo());
            stmt.setString(11, f.getCpf());

            stmt.executeUpdate();
        }
    }

    public void desativar(String cpf) throws SQLException {
        String sql = "UPDATE funcionario SET ativo = false WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }

    public void ativar(String cpf) throws SQLException {
        String sql = "UPDATE funcionario SET ativo = true WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }
}
