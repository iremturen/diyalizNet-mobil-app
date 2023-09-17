package com.mir.diyalizstok;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mir.diyalizstok.Models.MalzemeData;

import java.util.ArrayList;

public class DiyalizVeritabani extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASE_NAME = "DiyazlizStok";
    private static final int VERSION = 1;

    public DiyalizVeritabani(Context c) {
        super(c, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS SEANS(SEANSID INTEGER PRIMARY KEY,HASTAID INTEGER,STARIH TEXT,SSAAT TEXT,DURUM NUMERIC);");
        db.execSQL("CREATE TABLE IF NOT EXISTS MALZEME(ID INTEGER PRIMARY KEY,AD VARCHAR,ACIKLAMA TEXT,MIKTAR INTEGER,MALZEMETURU INTEGER,DURUM TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS MALZEME (ID INTEGER PRIMARY KEY, AD VARCHAR, ACIKLAMA TEXT, MIKTAR INTEGER, MALZEMETURU INTEGER, DURUM TEXT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS MALZEMETURU(MTID INTEGER PRIMARY KEY,AD VARCHAR,DURUM NUMERIC);");
        db.execSQL("CREATE TABLE IF NOT EXISTS STOKHAREKET(SHID INTEGER PRIMARY KEY,MALZEMEID INTEGER,MIKTAR INTEGER,HAREKETTURU NUMERIC,ACIKLAMA VARCHAR,TARIH TEXT,DURUM NUMERIC);");
        db.execSQL("CREATE TABLE IF NOT EXISTS SEANSMALZEME(ID INTEGER PRIMARY KEY,SEANSID INTEGER,STOKHAREKETID INTEGER,DURUM NUMERIC);");
        db.execSQL("create Table  IF NOT EXISTS USERS(ID INTEGER PRIMARY KEY,TCKN VARCHAR UNIQUE, PASSWORD VARCHAR, ISIM VARCHAR,SOYISIM VARCHAR,ADRES VARCHAR,TEL_NO VARCHAR,KAYIT_TARIHI TEXT);");
        // Varsayılan malzeme türlerini eklemek için metod
        db.execSQL("CREATE TRIGGER IF NOT EXISTS trg_stokhareket_update " +
                "AFTER INSERT ON STOKHAREKET " +
                "FOR EACH ROW " +
                "BEGIN " +
                " UPDATE MALZEME " +
                "    SET MIKTAR = MIKTAR - NEW.MIKTAR " +
                "    WHERE ID = NEW.MALZEMEID; " +
                "END;");
        varsayilanMalzemeTurleriniEkle(db);



    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists USERS");

    }

    private void varsayilanMalzemeTurleriniEkle(SQLiteDatabase db) {
        // Malzeme türlerini ID'leri 1, 2 ve 3 olacak şekilde eklendi.
        db.execSQL("INSERT INTO MALZEMETURU ( AD, DURUM) VALUES ( 'Tür 1', 0);");
        db.execSQL("INSERT INTO MALZEMETURU ( AD, DURUM) VALUES ( 'Tür 2', 0);");
        db.execSQL("INSERT INTO MALZEMETURU ( AD, DURUM) VALUES ( 'Tür 3', 0);");
    }

    public ArrayList<MalzemeData> tumMalzemeleriGetir() {
        ArrayList<MalzemeData> malzemeler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID,AD FROM MALZEME ", null);
        int columnIndexId = cursor.getColumnIndex("ID");
        int columnIndexAd = cursor.getColumnIndex("AD");
        if (columnIndexId == -1 || columnIndexAd == -1) {
            cursor.close();
            db.close();
            return malzemeler;
        }
        if (cursor.moveToFirst()) {
            do {
                String seansId = cursor.getString(columnIndexId);
                String hastaId = cursor.getString(columnIndexAd);
                MalzemeData malzeme = new MalzemeData(seansId, hastaId);
                malzemeler.add(malzeme);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return malzemeler;
    }

    public ArrayList<Integer> getMalzemeTuruIDs() {
        ArrayList<Integer> malzemeTuruIDs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MTID FROM MALZEMETURU", null);
        if (cursor != null && cursor.moveToFirst()) { // Cursor boş olabilir, bu nedenle kontrol ediyoruz.
            do {
                int columnIndex = cursor.getColumnIndex("MTID");
                if (columnIndex != -1) {
                    int malzemeTuruID = cursor.getInt(columnIndex);
                    malzemeTuruIDs.add(malzemeTuruID);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return malzemeTuruIDs;
    }

    public Boolean insertData(String isim,String soyisim,String tckn, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("TCKN", tckn);
        contentValues.put("PASSWORD", password);
        contentValues.put("SOYISIM", soyisim);
        contentValues.put("ISIM", isim);
        long result = MyDB.insert("USERS", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String tckn) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USERS where TCKN = ?", new String[]{tckn});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String tckn, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from USERS where TCKN = ? and PASSWORD = ?", new String[] {tckn,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
