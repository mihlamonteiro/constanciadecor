package com.cesarschool.constanciadecor.DAO;

import com.cesarschool.constanciadecor.config.DatabaseConnection;
import com.cesarschool.constanciadecor.dto.ComprasPorMesDTO;
import com.cesarschool.constanciadecor.dto.MediaAvaliacaoMensalDTO;
import com.cesarschool.constanciadecor.model.CompraAvalia;
import com.cesarschool.constanciadecor.model.ItemCompra;
import com.cesarschool.constanciadecor.dto.NovaCompraDTO;
import com.cesarschool.constanciadecor.dto.ProdutosVendidosCategoriaDTO;

import java.sql.*;
import java.util.*;

public class CompraAvaliaDAO {

    public void addCompra(CompraAvalia compra) throws SQLException {
        double valorFinal = compra.getValor_total();

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

    public List<ProdutosVendidosCategoriaDTO> getProdutosVendidosPorCategoria(int ano) throws SQLException {
        List<ProdutosVendidosCategoriaDTO> lista = new ArrayList<>();

        String sql = """
        SELECT p.nome AS categoria, COUNT(*) AS total
        FROM compra_avalia c
        JOIN item_compra i ON c.numero = i.numero_compra
        JOIN produtos p ON p.codigo = i.codigo_produto
        WHERE YEAR(c.data_compra) = ?
        GROUP BY p.nome
        ORDER BY total DESC
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new ProdutosVendidosCategoriaDTO(
                        rs.getString("categoria"),
                        rs.getInt("total")
                ));
            }
        }

        return lista;
    }

    public List<ComprasPorMesDTO> getMediaComprasPorMes(int ano) throws SQLException {
        List<ComprasPorMesDTO> lista = new ArrayList<>();
        String sql = "SELECT MONTH(data_compra) AS mes, AVG(valor_total) AS media FROM compra_avalia WHERE YEAR(data_compra) = ? GROUP BY MONTH(data_compra) ORDER BY mes";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String mes = String.format("%02d", rs.getInt("mes"));
                    double media = rs.getDouble("media");
                    lista.add(new ComprasPorMesDTO(mes, media));
                }
            }
        }

        return lista;
    }

    public List<Map<String, Object>> getComparativoVendas(int ano1, int ano2, int mesLimite) throws SQLException {
        String sql = """
        SELECT
            MONTH(data_compra) AS mes,
            YEAR(data_compra) AS ano,
            SUM(valor_total) AS total
        FROM compra_avalia
        WHERE (YEAR(data_compra) = ? OR YEAR(data_compra) = ?)
        AND MONTH(data_compra) <= ?
        GROUP BY YEAR(data_compra), MONTH(data_compra)
        ORDER BY mes
    """;

        Map<String, Map<Integer, Double>> agrupado = new LinkedHashMap<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ano1);
            stmt.setInt(2, ano2);
            stmt.setInt(3, mesLimite);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int mes = rs.getInt("mes");
                    int ano = rs.getInt("ano");
                    double total = rs.getDouble("total");

                    String chaveMes = String.format("%02d", mes);
                    agrupado.putIfAbsent(chaveMes, new HashMap<>());
                    agrupado.get(chaveMes).put(ano, total);
                }
            }
        }

        List<Map<String, Object>> resultado = new ArrayList<>();

        for (String mes : agrupado.keySet()) {
            Map<String, Object> linha = new HashMap<>();
            linha.put("mes", mes);
            linha.put("ano1", agrupado.get(mes).getOrDefault(ano1, 0.0));
            linha.put("ano2", agrupado.get(mes).getOrDefault(ano2, 0.0));
            resultado.add(linha);
        }

        return resultado;
    }

    public Double getMediaAvaliacaoLoja() throws SQLException {
        String sql = "SELECT AVG(nota) AS media FROM compra_avalia WHERE nota IS NOT NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble("media");
            }
        }
        return null;
    }



    public List<ItemCompra> listarItensDaCompra(int numeroCompra) throws SQLException {
        List<ItemCompra> lista = new ArrayList<>();

        String sql = """
        SELECT p.codigo, p.nome, p.preco, ic.quantidade
        FROM item_compra ic
        JOIN produtos p ON ic.codigo_produto = p.codigo
        WHERE ic.numero_compra = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, numeroCompra);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new ItemCompra(
                        rs.getInt("codigo"),
                        rs.getString("nome"),
                        rs.getDouble("preco"),
                        rs.getInt("quantidade")
                ));
            }
        }

        return lista;
    }

    public List<MediaAvaliacaoMensalDTO> getMediaAvaliacoesPorMes(int ano) throws SQLException {
        List<MediaAvaliacaoMensalDTO> lista = new ArrayList<>();
        String sql = "SELECT MONTH(data_avaliacao) AS mes, AVG(nota) AS mediaNota FROM compra_avalia WHERE nota > 0 AND YEAR(data_avaliacao) = ? GROUP BY MONTH(data_avaliacao) ORDER BY mes";

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

    public void salvarCompraComItens(NovaCompraDTO dto) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            String insertCompra = "INSERT INTO compra_avalia (numero, data_compra, valor_total, cpf_cliente, codigo_cupom) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertCompra)) {
                stmt.setInt(1, dto.getNumero());
                stmt.setDate(2, new java.sql.Date(dto.getData_compra().getTime()));
                stmt.setDouble(3, dto.getValor_total());
                stmt.setString(4, dto.getCpf_cliente());

                if (dto.getCodigo_cupom() != null) {
                    stmt.setInt(5, dto.getCodigo_cupom());
                } else {
                    stmt.setNull(5, java.sql.Types.INTEGER);
                }

                stmt.executeUpdate();
            }

            String insertItem = "INSERT INTO item_compra (numero_compra, codigo_produto, quantidade) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertItem)) {
                for (var item : dto.getItens()) {
                    stmt.setInt(1, dto.getNumero());
                    stmt.setInt(2, item.getCodigoProduto());
                    stmt.setInt(3, item.getQuantidade());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
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
