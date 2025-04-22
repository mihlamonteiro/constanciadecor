package com.cesarschool.constanciadecor.dto;

public class MediaAvaliacaoMensalDTO {
    private String mes;
    private double mediaNota;

    public MediaAvaliacaoMensalDTO(String mes, double mediaNota) {
        this.mes = mes;
        this.mediaNota = mediaNota;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public double getMediaNota() {
        return mediaNota;
    }

    public void setMediaNota(double mediaNota) {
        this.mediaNota = mediaNota;
    }
}
