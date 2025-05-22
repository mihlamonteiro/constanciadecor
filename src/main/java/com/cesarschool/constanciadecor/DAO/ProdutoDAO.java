package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void addProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO Produtos (codigo, nome, descricao, preco, estoque, dataCadastro, cpf_administrador, nomeImagem) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getCodigo());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setDouble(4, produto.getPreco());
            stmt.setInt(5, produto.getEstoque());
            stmt.setDate(6, Date.valueOf(produto.getDataCadastro()));
            stmt.setString(7, produto.getCpfAdministrador());
            stmt.setString(8, produto.getNomeImagem()); // <- imagem aqui

            stmt.executeUpdate();
        }
    }

    public Produto getProdutoByCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM Produtos WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque"),
                        rs.getDate("dataCadastro").toLocalDate(),
                        rs.getString("cpf_administrador")
                );
                produto.setNomeImagem(rs.getString("nomeImagem"));
                return produto;
            }
        }

        return null;
    }

    public List<Produto> getAllProdutos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM Produtos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto produto = new Produto(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getDouble("preco"),
                        rs.getInt("estoque"),
                        rs.getDate("dataCadastro").toLocalDate(),
                        rs.getString("cpf_administrador")
                );

                // 👇 Adiciona o campo da imagem
                produto.setNomeImagem(rs.getString("nomeImagem"));

                produtos.add(produto);
            }
        }

        return produtos;
    }

    public void deleteProduto(int codigo) throws SQLException {
        String sql = "DELETE FROM Produtos WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.executeUpdate();
        }
    }


    public void updateProduto(int codigo, Produto produto) throws SQLException {
        String sql = "UPDATE Produtos SET nome = ?, descricao = ?, preco = ?, estoque = ?, cpf_administrador = ?, nomeImagem = ? WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.setString(5, produto.getCpfAdministrador());
            stmt.setString(6, produto.getNomeImagem()); // nova imagem
            stmt.setInt(7, codigo);

            stmt.executeUpdate();
        }
    }

}
