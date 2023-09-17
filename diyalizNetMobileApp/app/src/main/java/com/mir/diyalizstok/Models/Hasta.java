package com.mir.diyalizstok.Models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Hasta {
    private String AD, SOYAD, TCNO, DOGUMTARIHI;
    private Integer ID;

    public Hasta() {

    }

    public Hasta(String AD, String SOYAD, String TCNO, String DOGUMTARIHI, Integer ID) {
        this.AD = AD;
        this.SOYAD = SOYAD;
        this.TCNO = TCNO;
        this.DOGUMTARIHI = DOGUMTARIHI;
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getAD() {
        return AD;
    }

    public void setAD(String AD) {
        this.AD = AD;
    }

    public String getSOYAD() {
        return SOYAD;
    }

    public void setSOYAD(String SOYAD) {
        this.SOYAD = SOYAD;
    }

    public String getTCNO() {
        return TCNO;
    }

    public void setTCNO(String TCNO) {
        this.TCNO = TCNO;
    }

    public String getDOGUMTARIHI() {
        return DOGUMTARIHI;
    }

    public void setDOGUMTARIHI(String DOGUMTARIHI) {
        this.DOGUMTARIHI = DOGUMTARIHI;
    }

    static public ArrayList<Hasta> getData(Context context) {
        ArrayList<Hasta> hastaList = new ArrayList<>();
        ArrayList<Integer> hastaIdList = new ArrayList<>();
        ArrayList<String> hastaAdList = new ArrayList<>();
        ArrayList<String> hastaSoyadList = new ArrayList<>();
        ArrayList<String> hastaTcList = new ArrayList<>();
        ArrayList<String> hastaDogumList = new ArrayList<>();

        try {
            SQLiteDatabase db = context.openOrCreateDatabase("DiyazlizStok", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("select * from HASTA", null);
            int hastaIdIndex=cursor.getColumnIndex("ID");
            int hastaAdIndex = cursor.getColumnIndex("AD");
            int hastaSoyadIndex = cursor.getColumnIndex("SOYAD");
            int hastaTcIndex = cursor.getColumnIndex("TCNO");
            int hastaDogumIndex = cursor.getColumnIndex("DOGUMTARIHI");

            while (cursor.moveToNext()) {
                hastaIdList.add(cursor.getInt(hastaIdIndex));
                hastaAdList.add(cursor.getString(hastaAdIndex));
                hastaSoyadList.add(cursor.getString(hastaSoyadIndex));
                hastaTcList.add(cursor.getString(hastaTcIndex));
                hastaDogumList.add(cursor.getString(hastaDogumIndex));
            }

            cursor.close();

            for (int i = 0; i < hastaAdList.size(); i++) {
                Hasta h = new Hasta();
                h.setID(hastaIdList.get(i));
                h.setAD(hastaAdList.get(i));
                h.setSOYAD(hastaSoyadList.get(i));
                h.setTCNO(hastaTcList.get(i));
                h.setDOGUMTARIHI(hastaDogumList.get(i));
                hastaList.add(h);
            }

        }
        catch (Exception e) {

        }
        return hastaList;
    }
}
