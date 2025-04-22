package com.cesarschool.constanciadecor.model;

import java.time.LocalDate;

public class AdministradorAromas {
    private String cpf;
    private String nome;
    private LocalDate data_nasc;
    private String telefone;
    private String rua;
    private String cep;
    private String bairro;
    private String numero;
    private boolean ativo;

    public AdministradorAromas() {}

    public AdministradorAromas(String cpf, String nome, LocalDate data_nasc, String telefone,
                               String rua, String cep, String bairro, String numero, boolean ativo) {
        this.cpf = cpf;
        this.nome = nome;
        this.data_nasc = data_nasc;
        this.telefone = telefone;
        this.rua = rua;
        this.cep = cep;
        this.bairro = bairro;
        this.numero = numero;
        this.ativo = ativo;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getData_nasc() { return data_nasc; }
    public void setData_nasc(LocalDate data_nasc) { this.data_nasc = data_nasc; }

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

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
