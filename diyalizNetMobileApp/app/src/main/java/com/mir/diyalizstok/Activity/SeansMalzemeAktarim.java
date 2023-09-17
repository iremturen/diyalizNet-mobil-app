package com.mir.diyalizstok.Activity;

public class SeansMalzemeAktarim {
    String  hastaAdi, hastaSoyadi,tcNo;
    Integer seansId;

    public SeansMalzemeAktarim(String hastaAdi, String hastaSoyadi, String tcNo, Integer seansId) {
        this.hastaAdi = hastaAdi;
        this.hastaSoyadi = hastaSoyadi;
        this.tcNo = tcNo;
        this.seansId = seansId;
}

    public String getHastaAdi() {
        return hastaAdi;
    }

    public String getHastaSoyadi() {
        return hastaSoyadi;
    }

    public String getTcNo() {
        return tcNo;
    }

    public Integer getSeansId() {
        return seansId;
    }
}
