package com.cesarschool.constanciadecor.model;

public class Favorita {
    private int codigoProduto;
    private String cpfCliente;

    public Favorita() {}

    public Favorita(int codigoProduto, String cpfCliente) {
        this.codigoProduto = codigoProduto;
        this.cpfCliente = cpfCliente;
    }

    // Getters e setters
    public int getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(int codigoProduto) { this.codigoProduto = codigoProduto; }

    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }
}
