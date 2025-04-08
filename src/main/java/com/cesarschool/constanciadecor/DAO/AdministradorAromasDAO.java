package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.model.AdministradorAromas;
import com.cesarschool.constanciadecor.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorAromasDAO {

    // Inserir novo administrador
    public void addAdministrador(AdministradorAromas adm) throws SQLException {
        String sql = "INSERT INTO administrador_aromas (cpf, nome, data_nasc, telefone, rua, cep, bairro, numero, cidade) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adm.getCpf());
            stmt.setString(2, adm.getNome());
            stmt.setDate(3, Date.valueOf(adm.getDataNasc()));
            stmt.setString(4, adm.getTelefone());
            stmt.setString(5, adm.getRua());
            stmt.setString(6, adm.getCep());
            stmt.setString(7, adm.getBairro());
            stmt.setString(8, adm.getNumero());
            stmt.setString(9, adm.getCidade());

            stmt.executeUpdate();
        }
    }

    // Buscar por CPF
    public AdministradorAromas getAdministradorByCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM administrador_aromas WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AdministradorAromas(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("data_nasc").toLocalDate(),
                        rs.getString("telefone"),
                        rs.getString("rua"),
                        rs.getString("cep"),
                        rs.getString("bairro"),
                        rs.getString("numero"),
                        rs.getString("cidade")
                );
            }
        }

        return null;
    }

    // Listar todos os administradores
    public List<AdministradorAromas> getAllAdministradores() throws SQLException {
        List<AdministradorAromas> lista = new ArrayList<>();
        String sql = "SELECT * FROM administrador_aromas";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AdministradorAromas adm = new AdministradorAromas(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getDate("data_nasc").toLocalDate(),
                        rs.getString("telefone"),
                        rs.getString("rua"),
                        rs.getString("cep"),
                        rs.getString("bairro"),
                        rs.getString("numero"),
                        rs.getString("cidade")
                );
                lista.add(adm);
            }
        }

        return lista;
    }

    // Atualizar dados de um administrador
    public void updateAdministrador(AdministradorAromas adm) throws SQLException {
        String sql = "UPDATE administrador_aromas SET nome = ?, data_nasc = ?, telefone = ?, rua = ?, cep = ?, bairro = ?, numero = ?, cidade = ? WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adm.getNome());
            stmt.setDate(2, Date.valueOf(adm.getDataNasc()));
            stmt.setString(3, adm.getTelefone());
            stmt.setString(4, adm.getRua());
            stmt.setString(5, adm.getCep());
            stmt.setString(6, adm.getBairro());
            stmt.setString(7, adm.getNumero());
            stmt.setString(8, adm.getCidade());
            stmt.setString(9, adm.getCpf());

            stmt.executeUpdate();
        }
    }

    // Deletar por CPF
    public void deleteAdministrador(String cpf) throws SQLException {
        String sql = "DELETE FROM administrador_aromas WHERE cpf = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.executeUpdate();
        }
    }
}
