package com.cesarschool.constanciadecor.model;

import java.time.LocalDate;

public class Produto {
    private int codigo;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private LocalDate dataCadastro;
    private String cpfAdministrador;
    private String nomeImagem;

    public Produto() {}

    public Produto(int codigo, String nome, String descricao, double preco, int estoque,
                   LocalDate dataCadastro, String cpfAdministrador) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.dataCadastro = dataCadastro;
        this.cpfAdministrador = cpfAdministrador;
    }

    // Getters e setters
    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }

    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate dataCadastro) { this.dataCadastro = dataCadastro; }

    public String getCpfAdministrador() { return cpfAdministrador; }
    public void setCpfAdministrador(String cpfAdministrador) { this.cpfAdministrador = cpfAdministrador; }

    public String getNomeImagem() { return nomeImagem; }
    public void setNomeImagem(String nomeImagem) { this.nomeImagem = nomeImagem; }
}
