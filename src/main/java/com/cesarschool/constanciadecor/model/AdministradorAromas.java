package com.cesarschool.constanciadecor.model;

import java.time.LocalDate;

public class AdministradorAromas {
    private String cpf;
    private String nome;
    private LocalDate dataNasc;
    private String telefone;
    private String rua;
    private String cep;
    private String bairro;
    private String numero;
    private String cidade;

    public AdministradorAromas() {}

    public AdministradorAromas(String cpf, String nome, LocalDate dataNasc, String telefone, String rua, String cep, String bairro, String numero, String cidade) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNasc = dataNasc;
        this.telefone = telefone;
        this.rua = rua;
        this.cep = cep;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
    }

    // Getters e Setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNasc() { return dataNasc; }
    public void setDataNasc(LocalDate dataNasc) { this.dataNasc = dataNasc; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getRua() { return rua; }
    public void setRua(String rua) { this.rua = rua; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }
}
