package com.cesarschool.constanciadecor.model;

import java.time.LocalDate;

public class CompraAvalia {
    private int numero;
    private LocalDate data;
    private double valorTotal;
    private String avaliacao;
    private int nota;
    private String cpfCliente;
    private int codigoProduto;
    private int codigoCupom;

    public CompraAvalia() {}

    public CompraAvalia(int numero, LocalDate data, double valorTotal, String avaliacao, int nota,
                        String cpfCliente, int codigoProduto, int codigoCupom) {
        this.numero = numero;
        this.data = data;
        this.valorTotal = valorTotal;
        this.avaliacao = avaliacao;
        this.nota = nota;
        this.cpfCliente = cpfCliente;
        this.codigoProduto = codigoProduto;
        this.codigoCupom = codigoCupom;
    }

    // Getters e Setters
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getAvaliacao() { return avaliacao; }
    public void setAvaliacao(String avaliacao) { this.avaliacao = avaliacao; }

    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }

    public int getCodigoProduto() { return codigoProduto; }
    public void setCodigoProduto(int codigoProduto) { this.codigoProduto = codigoProduto; }

    public int getCodigoCupom() { return codigoCupom; }
    public void setCodigoCupom(int codigoCupom) { this.codigoCupom = codigoCupom; }
}
