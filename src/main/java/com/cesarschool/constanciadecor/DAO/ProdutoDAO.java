package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void addProduto(Produto produto) throws SQLException {
        String sql = "INSERT INTO Produtos (codigo, nome, descricao, preco, preco_producao, estoque, dataCadastro, cpf_administrador, nomeImagem) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getCodigo());
            stmt.setString(2, produto.getNome());
            stmt.setString(3, produto.getDescricao());
            stmt.setDouble(4, produto.getPreco());
            stmt.setDouble(5, produto.getPrecoProducao()); // Adicionado preço de produção
            stmt.setInt(6, produto.getEstoque());
            stmt.setDate(7, Date.valueOf(produto.getDataCadastro()));
            stmt.setString(8, produto.getCpfAdministrador());
            stmt.setString(9, produto.getNomeImagem());

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
                        rs.getString("cpf_administrador"),
                        rs.getDouble("preco_producao") // Adicionado preço de produção
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
                        rs.getString("cpf_administrador"),
                        rs.getDouble("preco_producao") // Adicionado preço de produção
                );
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
        String sql = "UPDATE Produtos SET nome = ?, descricao = ?, preco = ?, preco_producao = ?, estoque = ?, cpf_administrador = ?, nomeImagem = ? WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setDouble(4, produto.getPrecoProducao()); // Adicionado preço de produção
            stmt.setInt(5, produto.getEstoque());
            stmt.setString(6, produto.getCpfAdministrador());
            stmt.setString(7, produto.getNomeImagem());
            stmt.setInt(8, codigo);

            stmt.executeUpdate();
        }
    }
}
