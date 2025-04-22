package com.cesarschool.constanciadecor.model;

public class Indica {
    private String cpfIndicador;
    private String cpfIndicado;

    public Indica() {}

    public Indica(String cpfIndicador, String cpfIndicado) {
        this.cpfIndicador = cpfIndicador;
        this.cpfIndicado = cpfIndicado;
    }

    public String getCpfIndicador() { return cpfIndicador; }
    public void setCpfIndicador(String cpfIndicador) { this.cpfIndicador = cpfIndicador; }

    public String getCpfIndicado() { return cpfIndicado; }
    public void setCpfIndicado(String cpfIndicado) { this.cpfIndicado = cpfIndicado; }
}
