package com.cesarschool.constanciadecor.dto;

public class ComprasPorMesDTO {
    private String mes;
    private int total;

    public ComprasPorMesDTO() {}

    public ComprasPorMesDTO(String mes, int total) {
        this.mes = mes;
        this.total = total;
    }

    public String getMes() { return mes; }
    public void setMes(String mes) { this.mes = mes; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}
