package com.cesarschool.constanciadecor.model;

public class ItemCompra {
    private int codigoProduto;
    private String nomeProduto;
    private double preco;
    private int quantidade;

    public ItemCompra(int codigoProduto, String nomeProduto, double preco, int quantidade) {
        this.codigoProduto = codigoProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public int getCodigoProduto() {
        return codigoProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getSubtotal() {
        return preco * quantidade;
    }
}
