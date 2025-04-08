package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.CompraAvalia;
import com.cesarschool.constanciadecor.dto.CompraDetalhadaDTO;
import com.cesarschool.constanciadecor.dto.ComprasPorMesDTO;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraAvaliaDAO {

    public void addCompra(CompraAvalia compra) throws SQLException {
        String sql = "INSERT INTO Compra_Avalia (numero, data, valor_total, avaliacao, nota, cpf_cliente, codigo_produto, codigo_cupom) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, compra.getNumero());
            stmt.setDate(2, Date.valueOf(compra.getData()));
            stmt.setDouble(3, compra.getValorTotal());
            stmt.setString(4, compra.getAvaliacao());
            stmt.setInt(5, compra.getNota());
            stmt.setString(6, compra.getCpfCliente());
            stmt.setInt(7, compra.getCodigoProduto());
            stmt.setInt(8, compra.getCodigoCupom());

            stmt.executeUpdate();
        }
    }

    public List<ComprasPorMesDTO> getComprasPorMes() throws SQLException {
        List<ComprasPorMesDTO> lista = new ArrayList<>();

        String sql = """
        SELECT 
            DATE_FORMAT(data, '%Y-%m') AS mes,
            COUNT(*) AS total
        FROM Compra_Avalia
        GROUP BY mes
        ORDER BY mes
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String mes = rs.getString("mes");
                int total = rs.getInt("total");
                lista.add(new ComprasPorMesDTO(mes, total));
            }
        }

        return lista;
    }


    public List<CompraAvalia> getAllCompras() throws SQLException {
        List<CompraAvalia> compras = new ArrayList<>();
        String sql = "SELECT * FROM Compra_Avalia";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CompraAvalia compra = new CompraAvalia(
                        rs.getInt("numero"),
                        rs.getDate("data").toLocalDate(),
                        rs.getDouble("valor_total"),
                        rs.getString("avaliacao"),
                        rs.getInt("nota"),
                        rs.getString("cpf_cliente"),
                        rs.getInt("codigo_produto"),
                        rs.getInt("codigo_cupom")
                );
                compras.add(compra);
            }
        }

        return compras;
    }

    public List<CompraDetalhadaDTO> listarComprasPorCliente(String cpfCliente) throws SQLException {
        List<CompraDetalhadaDTO> lista = new ArrayList<>();
        String sql = """
        SELECT c.numero, c.data, c.valor_total, c.avaliacao, c.nota,
               p.nome AS nome_produto, p.descricao AS descricao_produto, p.preco,
               cu.descricao AS descricao_cupom, cu.valorDesconto
        FROM Compra_Avalia c
        JOIN Produtos p ON c.codigo_produto = p.codigo
        LEFT JOIN Cupom cu ON c.codigo_cupom = cu.codigo
        WHERE c.cpf_cliente = ?
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpfCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CompraDetalhadaDTO dto = new CompraDetalhadaDTO();
                dto.setNumero(rs.getInt("numero"));
                dto.setData(rs.getDate("data").toLocalDate());
                dto.setValorTotal(rs.getDouble("valor_total"));
                dto.setAvaliacao(rs.getString("avaliacao"));
                dto.setNota(rs.getInt("nota"));

                dto.setNomeProduto(rs.getString("nome_produto"));
                dto.setDescricaoProduto(rs.getString("descricao_produto"));
                dto.setPrecoProduto(rs.getDouble("preco"));

                dto.setDescricaoCupom(rs.getString("descricao_cupom"));
                dto.setValorDesconto(rs.getDouble("valorDesconto"));

                lista.add(dto);
            }
        }

        return lista;
    }


    public CompraAvalia getCompraByNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM Compra_Avalia WHERE numero = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CompraAvalia(
                        rs.getInt("numero"),
                        rs.getDate("data").toLocalDate(),
                        rs.getDouble("valor_total"),
                        rs.getString("avaliacao"),
                        rs.getInt("nota"),
                        rs.getString("cpf_cliente"),
                        rs.getInt("codigo_produto"),
                        rs.getInt("codigo_cupom")
                );
            }
        }

        return null;
    }

    public void deleteCompra(int numero) throws SQLException {
        String sql = "DELETE FROM Compra_Avalia WHERE numero = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numero);
            stmt.executeUpdate();
        }
    }
}
