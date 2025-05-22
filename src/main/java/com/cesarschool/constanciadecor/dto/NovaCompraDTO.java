package com.cesarschool.constanciadecor.dto;

import java.util.Date;
import java.util.List;

public class NovaCompraDTO {
    private int numero;
    private Date data_compra;
    private double valor_total;
    private String cpf_cliente;
    private Integer codigo_cupom; // pode ser nulo
    private List<ItemCompraDTO> itens;

    // Getters e Setters
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

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
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

    public List<ItemCompraDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemCompraDTO> itens) {
        this.itens = itens;
    }
}
