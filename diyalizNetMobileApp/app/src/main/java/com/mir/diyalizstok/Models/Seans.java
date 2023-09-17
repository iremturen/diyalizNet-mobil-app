package com.mir.diyalizstok.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Seans {
    private String sHastaAd, sHastaSoyad, sHastaTc, sTarih, sSaat;
    private Integer seansId;
    public Seans(){

    }

    public Seans(String sHastaAd, String sHastaSoyad, String sHastaTc, String sTarih, String sSaat, Integer seansId) {
        this.sHastaAd = sHastaAd;
        this.sHastaSoyad = sHastaSoyad;
        this.sHastaTc = sHastaTc;
        this.sTarih = sTarih;
        this.sSaat = sSaat;
        this.seansId = seansId;
    }

    public String getsHastaAd() {
        return sHastaAd;
    }

    public void setsHastaAd(String sHastaAd) {
        this.sHastaAd = sHastaAd;
    }

    public String getsHastaSoyad() {
        return sHastaSoyad;
    }

    public void setsHastaSoyad(String sHastaSoyad) {
        this.sHastaSoyad = sHastaSoyad;
    }

    public String getsHastaTc() {
        return sHastaTc;
    }

    public void setsHastaTc(String sHastaTc) {
        this.sHastaTc = sHastaTc;
    }

    public String getsTarih() {
        return sTarih;
    }

    public void setsTarih(String sTarih) {
        this.sTarih = sTarih;
    }

    public String getsSaat() {
        return sSaat;
    }

    public void setsSaat(String sSaat) {
        this.sSaat = sSaat;
    }

    public Integer getSeansId() {
        return seansId;
    }

    public void setSeansId(Integer seansId) {
        this.seansId = seansId;
    }


    static public ArrayList<Seans> getData(Context context) {
        ArrayList<Seans> seansListe = new ArrayList<>();
        ArrayList<String> sHastaAdList = new ArrayList<>();
        ArrayList<String> sHastaSoyadList = new ArrayList<>();
        ArrayList<String> sHastaTcList = new ArrayList<>();
        ArrayList<Integer> sSeansIdList = new ArrayList<>();
        ArrayList<String> sTarihSeansList = new ArrayList<>();
        ArrayList<String> sSaatSeansList = new ArrayList<>();
        try {
            SQLiteDatabase database = context.openOrCreateDatabase("DiyazlizStok", Context.MODE_PRIVATE, null);
            Cursor cursor = database.rawQuery("SELECT AD,SOYAD,TCNO,SEANSID,STARIH\n" +
                    ",SSAAT FROM HASTA h\n" +
                    "INNER JOIN SEANS s\n" +
                    "on s.HASTAID=h.ID ORDER BY s.STARIH ASC", null);

            int sHastaAdiIndex = cursor.getColumnIndex("AD");
            int sHastaSoyadiIndex = cursor.getColumnIndex("SOYAD");
            int sHastaTcIndex = cursor.getColumnIndex("TCNO");
            int sIDIndex = cursor.getColumnIndex("SEANSID");
            int sTarihIndex = cursor.getColumnIndex("STARIH");
            int sSaatIndex = cursor.getColumnIndex("SSAAT");

            while (cursor.moveToNext()) {
                sHastaAdList.add(cursor.getString(sHastaAdiIndex));
                sHastaSoyadList.add(cursor.getString(sHastaSoyadiIndex));
                sHastaTcList.add(cursor.getString(sHastaTcIndex));
                sSeansIdList.add(cursor.getInt(sIDIndex));
                sTarihSeansList.add(cursor.getString(sTarihIndex));
                sSaatSeansList.add(cursor.getString(sSaatIndex));
            }
            cursor.close();
            for (int i = 0; i < sHastaAdList.size(); i++) {
                Seans seans = new Seans();
                seans.setsHastaAd(sHastaAdList.get(i));
                seans.setsHastaSoyad(sHastaSoyadList.get(i));
                seans.setsHastaTc(sHastaTcList.get(i));
                seans.setSeansId(sSeansIdList.get(i));
                seans.setsTarih(sTarihSeansList.get(i));
                seans.setsSaat(sSaatSeansList.get(i));
                seansListe.add(seans);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return seansListe;
    }
}

