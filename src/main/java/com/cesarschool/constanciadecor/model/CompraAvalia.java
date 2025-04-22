package com.cesarschool.constanciadecor.model;

import java.sql.Date;

public class CompraAvalia {
    private int numero;
    private Date data_compra;
    private Date data_avaliacao;
    private double valor_total;
    private String avaliacao;
    private int nota;
    private String cpf_cliente;
    private Integer codigo_cupom; // Pode ser null

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getData_compra() {
        return data_compra;
    }

    public void setData_compra(Date data_compra) {
        this.data_compra = data_compra;
    }

    public Date getData_avaliacao() {
        return data_avaliacao;
    }

    public void setData_avaliacao(Date data_avaliacao) {
        this.data_avaliacao = data_avaliacao;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
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

    public String getCpf_cliente() {
        return cpf_cliente;
    }

    public void setCpf_cliente(String cpf_cliente) {
        this.cpf_cliente = cpf_cliente;
    }

    public Integer getCodigo_cupom() {
        return codigo_cupom;
    }

    public void setCodigo_cupom(Integer codigo_cupom) {
        this.codigo_cupom = codigo_cupom;
    }
}
