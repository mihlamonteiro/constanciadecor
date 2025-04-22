package com.cesarschool.constanciadecor.dto;

public class ComprasPorMesDTO {
    private String mes;
    private double media;

    public ComprasPorMesDTO(String mes, double media) {
        this.mes = mes;
        this.media = media;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public double getMedia() {
        return media;
    }

    public void setMedia(double media) {
        this.media = media;
    }
}
