package com.mir.diyalizstok.Activity;

public class SeansAktarim {
    private String hastaAdi,hastaSoyadi,hastaTc;
    private Integer hastaId;

    public SeansAktarim(String hastaAdi, String hastaSoyadi, String hastaTc, Integer hastaId) {
        this.hastaAdi = hastaAdi;
        this.hastaSoyadi = hastaSoyadi;
        this.hastaTc = hastaTc;
        this.hastaId = hastaId;
    }

    public String getHastaAdi() {
        return hastaAdi;
    }

    public String getHastaSoyadi() {
        return hastaSoyadi;
    }

    public String getHastaTc() {
        return hastaTc;
    }

    public Integer getHastaId() {
        return hastaId;
    }
}
