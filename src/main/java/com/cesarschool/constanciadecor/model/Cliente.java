package com.cesarschool.constanciadecor.model;

public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private String numero;

    public Cliente() {}

    public Cliente(String cpf, String nome, String email, String numero) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.numero = numero;
    }

    // Getters e setters
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}
