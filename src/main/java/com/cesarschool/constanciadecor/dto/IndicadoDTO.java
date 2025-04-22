package com.cesarschool.constanciadecor.dto;

public class IndicadoDTO {
    private String cpfIndicado;

    public IndicadoDTO() {}

    public IndicadoDTO(String cpfIndicado) {
        this.cpfIndicado = cpfIndicado;
    }

    public String getCpfIndicado() {
        return cpfIndicado;
    }

    public void setCpfIndicado(String cpfIndicado) {
        this.cpfIndicado = cpfIndicado;
    }
}
