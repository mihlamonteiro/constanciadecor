package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Favorita;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritaDAO {

    public void addFavorito(Favorita favorita) throws SQLException {
        String sql = "INSERT INTO Favorita (codigo_produto, cpf_cliente) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, favorita.getCodigoProduto());
            stmt.setString(2, favorita.getCpfCliente());
            stmt.executeUpdate();
        }
    }

    public void removeFavorito(Favorita favorita) throws SQLException {
        String sql = "DELETE FROM Favorita WHERE codigo_produto = ? AND cpf_cliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, favorita.getCodigoProduto());
            stmt.setString(2, favorita.getCpfCliente());
            stmt.executeUpdate();
        }
    }

    public List<Favorita> listarFavoritosPorCliente(String cpfCliente) throws SQLException {
        List<Favorita> lista = new ArrayList<>();
        String sql = "SELECT * FROM Favorita WHERE cpf_cliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Favorita fav = new Favorita(
                        rs.getInt("codigo_produto"),
                        rs.getString("cpf_cliente")
                );
                lista.add(fav);
            }
        }

        return lista;
    }
}
