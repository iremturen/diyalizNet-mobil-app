package com.mir.diyalizstok.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Malzeme {
    private int malzemeID;
    private String adi;
    private String aciklama;
    private int miktar;
    private String malzemeTuru;
    private String malzemeDurum;

    public Malzeme() {
    }

    public Malzeme(int malzemeID, String adi, String aciklama, int miktar, String malzemeTuru, String malzemeDurum) {
        this.malzemeID = malzemeID;
        this.adi = adi;
        this.aciklama = aciklama;
        this.miktar = miktar;
        this.malzemeTuru = malzemeTuru;
        this.malzemeDurum = malzemeDurum;
    }

    public int getMalzemeID() {
        return malzemeID;
    }

    public void setMalzemeID(int malzemeID) {
        this.malzemeID = malzemeID;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public String getMalzemeTuru() {
        return malzemeTuru;
    }

    public void setMalzemeTuru(String malzemeTuru) {
        this.malzemeTuru = malzemeTuru;
    }

    public String getMalzemeDurum() {
        return malzemeDurum;
    }

    public void setMalzemeDurum(String malzemeDurum) {
        this.malzemeDurum = malzemeDurum;
    }

    static public ArrayList<Malzeme> getData(Context context) {
        ArrayList<Malzeme> malzemeList = new ArrayList<>();
        ArrayList<Integer> malzemeIDList = new ArrayList<>();
        ArrayList<String> adiList = new ArrayList<>();
        ArrayList<String> aciklamaList = new ArrayList<>();
        ArrayList<Integer> miktarList = new ArrayList<>();
        ArrayList<String> malzemeTuruList = new ArrayList<>();
        ArrayList<String> malzemeDurumList = new ArrayList<>();

        try {
            SQLiteDatabase db = context.openOrCreateDatabase("DiyazlizStok", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM MALZEME", null);
            int malzemeIDIndex = cursor.getColumnIndex("ID");
            int adiIndex = cursor.getColumnIndex("AD");
            int aciklamaIndex = cursor.getColumnIndex("ACIKLAMA");
            int miktarIndex = cursor.getColumnIndex("MIKTAR");
            int malzemeTuruIndex = cursor.getColumnIndex("MALZEMETURU");
            int malzemeDurumIndex = cursor.getColumnIndex("DURUM");

            while (cursor.moveToNext()) {
                malzemeIDList.add(cursor.getInt(malzemeIDIndex));
                adiList.add(cursor.getString(adiIndex));
                aciklamaList.add(cursor.getString(aciklamaIndex));
                miktarList.add(cursor.getInt(miktarIndex));
                malzemeTuruList.add(cursor.getString(malzemeTuruIndex));
                malzemeDurumList.add(cursor.getString(malzemeDurumIndex));
            }

            cursor.close();

            for (int i = 0; i < adiList.size(); i++) {
                Malzeme malzeme = new Malzeme();
                malzeme.setMalzemeID(malzemeIDList.get(i));
                malzeme.setAdi(adiList.get(i));
                malzeme.setAciklama(aciklamaList.get(i));
                malzeme.setMiktar(miktarList.get(i));
                malzeme.setMalzemeTuru(malzemeTuruList.get(i));
                malzeme.setMalzemeDurum(malzemeDurumList.get(i));
                malzemeList.add(malzeme);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return malzemeList;
    }

}
