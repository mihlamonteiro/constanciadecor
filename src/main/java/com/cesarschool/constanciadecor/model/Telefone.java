package com.cesarschool.constanciadecor.model;

public class Telefone {
    private String cpfCliente;
    private String telefone;

    public Telefone() {}

    public Telefone(String cpfCliente, String telefone) {
        this.cpfCliente = cpfCliente;
        this.telefone = telefone;
    }

    // Getters e Setters
    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
