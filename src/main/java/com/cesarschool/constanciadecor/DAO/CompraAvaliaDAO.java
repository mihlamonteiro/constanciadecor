package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.model.CompraAvalia;
import com.cesarschool.constanciadecor.dto.MediaAvaliacaoMensalDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraAvaliaDAO {

    public void addCompra(CompraAvalia compra) throws SQLException {
        double valorFinal = compra.getValor_total();

        // Se houver cupom, buscar o valor de desconto e aplicar
        if (compra.getCodigo_cupom() != null) {
            String cupomQuery = "SELECT valorDesconto FROM cupom WHERE codigo = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(cupomQuery)) {

                stmt.setInt(1, compra.getCodigo_cupom());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        double desconto = rs.getDouble("valorDesconto");
                        valorFinal = Math.max(0, valorFinal - desconto);
                    }
                }
            }
        }

        // Inserir a compra com o valor final calculado
        String sql = "INSERT INTO compra_avalia (numero, data_compra, valor_total, cpf_cliente, codigo_cupom) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, compra.getNumero());
            stmt.setDate(2, compra.getData_compra());
            stmt.setDouble(3, valorFinal);
            stmt.setString(4, compra.getCpf_cliente());

            if (compra.getCodigo_cupom() != null) {
                stmt.setInt(5, compra.getCodigo_cupom());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();
        }
    }
    public List<MediaAvaliacaoMensalDTO> getMediaAvaliacoesPorMes(int ano) throws SQLException {
        List<MediaAvaliacaoMensalDTO> lista = new ArrayList<>();
        String sql = "SELECT MONTH(data_avaliacao) AS mes, AVG(nota) AS mediaNota " +
                "FROM compra_avalia " +
                "WHERE nota > 0 AND YEAR(data_avaliacao) = ? " +
                "GROUP BY MONTH(data_avaliacao) " +
                "ORDER BY mes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String mes = String.format("%02d", rs.getInt("mes"));
                    double mediaNota = rs.getDouble("mediaNota");
                    lista.add(new MediaAvaliacaoMensalDTO(mes, mediaNota));
                }
            }
        }

        return lista;
    }

    public void avaliarCompra(int numero, CompraAvalia dados) throws SQLException {
        String sql = "UPDATE compra_avalia SET avaliacao = ?, nota = ?, data_avaliacao = ? WHERE numero = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dados.getAvaliacao());
            stmt.setInt(2, dados.getNota());
            stmt.setDate(3, dados.getData_avaliacao());
            stmt.setInt(4, numero);

            stmt.executeUpdate();
        }
    }

    public List<CompraAvalia> getAllCompras() throws SQLException {
        List<CompraAvalia> lista = new ArrayList<>();
        String sql = "SELECT * FROM compra_avalia";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CompraAvalia c = new CompraAvalia();
                c.setNumero(rs.getInt("numero"));
                c.setData_compra(rs.getDate("data_compra"));
                c.setData_avaliacao(rs.getDate("data_avaliacao"));
                c.setValor_total(rs.getDouble("valor_total"));
                c.setAvaliacao(rs.getString("avaliacao"));
                c.setNota(rs.getInt("nota"));
                c.setCpf_cliente(rs.getString("cpf_cliente"));
                c.setCodigo_cupom(rs.getObject("codigo_cupom", Integer.class));
                lista.add(c);
            }
        }

        return lista;
    }

    public CompraAvalia getCompraByNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM compra_avalia WHERE numero = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numero);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CompraAvalia c = new CompraAvalia();
                    c.setNumero(rs.getInt("numero"));
                    c.setData_compra(rs.getDate("data_compra"));
                    c.setData_avaliacao(rs.getDate("data_avaliacao"));
                    c.setValor_total(rs.getDouble("valor_total"));
                    c.setAvaliacao(rs.getString("avaliacao"));
                    c.setNota(rs.getInt("nota"));
                    c.setCpf_cliente(rs.getString("cpf_cliente"));
                    c.setCodigo_cupom(rs.getObject("codigo_cupom", Integer.class));
                    return c;
                }
            }
        }

        return null;
    }

    public void deleteCompra(int numero) throws SQLException {
        String sql = "DELETE FROM compra_avalia WHERE numero = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numero);
            stmt.executeUpdate();
        }
    }
}
