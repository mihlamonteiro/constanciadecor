package com.cesarschool.constanciadecor.dto;

import java.time.LocalDate;

public class CompraDetalhadaDTO {
    private int numero;
    private LocalDate data;
    private double valorTotal;
    private String avaliacao;
    private int nota;

    private String nomeProduto;
    private String descricaoProduto;
    private double precoProduto;

    private String descricaoCupom;
    private double valorDesconto;

    public CompraDetalhadaDTO() {}

    public CompraDetalhadaDTO(int numero, LocalDate data, double valorTotal, String avaliacao, int nota,
                              String nomeProduto, String descricaoProduto, double precoProduto,
                              String descricaoCupom, double valorDesconto) {
        this.numero = numero;
        this.data = data;
        this.valorTotal = valorTotal;
        this.avaliacao = avaliacao;
        this.nota = nota;
        this.nomeProduto = nomeProduto;
        this.descricaoProduto = descricaoProduto;
        this.precoProduto = precoProduto;
        this.descricaoCupom = descricaoCupom;
        this.valorDesconto = valorDesconto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(double precoProduto) {
        this.precoProduto = precoProduto;
    }

    public String getDescricaoCupom() {
        return descricaoCupom;
    }

    public void setDescricaoCupom(String descricaoCupom) {
        this.descricaoCupom = descricaoCupom;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
}
