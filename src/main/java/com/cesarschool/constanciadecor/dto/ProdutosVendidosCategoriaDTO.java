package com.cesarschool.constanciadecor.dto;

public class ProdutosVendidosCategoriaDTO {
    private String categoria;
    private int total;

    public ProdutosVendidosCategoriaDTO(String categoria, int total) {
        this.categoria = categoria;
        this.total = total;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getTotal() {
        return total;
    }
}
