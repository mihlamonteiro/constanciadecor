package com.cesarschool.constanciadecor.model;

import java.time.LocalDate;

public class Funcionario {
    private String cpf;
    private String nome;
    private String cargo;
    private LocalDate dataNasc;
    private String telefone;
    private String cep;
    private String bairro;
    private String numero;
    private String rua;
    private String cpfAdministrador;

    public Funcionario() {}

    public Funcionario(String cpf, String nome, String cargo, LocalDate dataNasc, String telefone,
                       String cep, String bairro, String numero, String rua, String cpfAdministrador) {
        this.cpf = cpf;
        this.nome = nome;
        this.cargo = cargo;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.cep = cep;
        this.bairro = bairro;
        this.numero = numero;
        this.rua = rua;
        this.cpfAdministrador = cpfAdministrador;
    }

    // Getters e setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }

    public LocalDate getDataNasc() { return dataNasc; }
    public void setDataNasc(LocalDate dataNasc) { this.dataNasc = dataNasc; }

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

    public String getCpfAdministrador() { return cpfAdministrador; }
    public void setCpfAdministrador(String cpfAdministrador) { this.cpfAdministrador = cpfAdministrador; }
}
