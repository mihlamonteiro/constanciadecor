package com.cesarschool.constanciadecor.model;

import java.time.LocalDate;

public class Funcionario {
    private String cpf;
    private String nome;
    private String cargo;
    private LocalDate data_nasc;
    private String telefone;
    private String cep;
    private String bairro;
    private String numero;
    private String rua;
    private String cpf_administrador;
    private boolean ativo;

    public Funcionario() {}

    public Funcionario(String cpf, String nome, String cargo, LocalDate data_nasc, String telefone,
                       String cep, String bairro, String numero, String rua,
                       String cpf_administrador, boolean ativo) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
        this.data_nasc = data_nasc;
        this.telefone = telefone;
        this.cep = cep;
        this.bairro = bairro;
        this.numero = numero;
        this.rua = rua;
        this.cpf_administrador = cpf_administrador;
        this.ativo = ativo;
    }

    // Getters e Setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public LocalDate getData_nasc() { return data_nasc; }
    public void setData_nasc(LocalDate data_nasc) { this.data_nasc = data_nasc; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getCpf_administrador() { return cpf_administrador; }
    public void setCpf_administrador(String cpf_administrador) { this.cpf_administrador = cpf_administrador; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
