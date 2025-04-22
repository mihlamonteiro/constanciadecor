package com.cesarschool.constanciadecor.model;

import java.time.LocalDate;

public class Cupom {
    private int codigo;
    private LocalDate dataInicio;
    private LocalDate dataVencimento;
    private double valorDesconto;
    private String descricao;
    private String condicoesUso;

    public Cupom() {}

    public Cupom(int codigo, LocalDate dataInicio, LocalDate dataVencimento, double valorDesconto, String descricao, String condicoesUso) {
        this.codigo = codigo;
        this.dataInicio = dataInicio;
        this.dataVencimento = dataVencimento;
        this.valorDesconto = valorDesconto;
        this.descricao = descricao;
        this.condicoesUso = condicoesUso;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    public double getValorDesconto() { return valorDesconto; }
    public void setValorDesconto(double valorDesconto) { this.valorDesconto = valorDesconto; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCondicoesUso() { return condicoesUso; }
    public void setCondicoesUso(String condicoesUso) { this.condicoesUso = condicoesUso; }
}
