package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.R;

import java.util.Calendar;


public class hastaKayit extends AppCompatActivity {
    Button btnKaydet;
    EditText editAd, editSoyad, editTc,editDogum;
    TextView tarih;
    ImageView hastaYonlendir;
    private String hastaAd, hastaSoyad, hastaTc, hastaDTarih;

    private void init() {
        btnKaydet = (Button) findViewById(R.id.btnKayitHasta);
        editAd = (EditText) findViewById(R.id.editAd);
        editSoyad = (EditText) findViewById(R.id.editSoyad);
        editTc = (EditText) findViewById(R.id.editTc);
        editDogum= (EditText) findViewById(R.id.editDogumTarih);
        hastaYonlendir=(ImageView) findViewById(R.id.imgHastaList);
    }

    private void nesneleriTemizle() {
        editAd.setText("");
        editSoyad.setText("");
        editTc.setText("");
        tarih.setText("");

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hastakayit);
        init();
        editDogum.addTextChangedListener(new TextWatcher() {

            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                {
                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]", "");
                        String cleanC = current.replaceAll("[^\\d.]", "");

                        int cl = clean.length();
                        int sel = cl;
                        for (int z = 2; z <= cl && z < 6; z += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 8) {
                            clean = clean + ddmmyyyy.substring(clean.length());
                        } else {
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int day = Integer.parseInt(clean.substring(0, 2));
                            int mon = Integer.parseInt(clean.substring(2, 4));
                            int year = Integer.parseInt(clean.substring(4, 8));

                            if (mon > 12) mon = 12;
                            cal.set(Calendar.MONTH, mon - 1);

                            year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                            clean = String.format("%02d%02d%02d", day, mon, year);
                        }

                        clean = String.format("%s/%s/%s", clean.substring(0, 2),
                                clean.substring(2, 4),
                                clean.substring(4, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        editDogum.setText(current);
                        editDogum.setSelection(sel < current.length() ? sel : current.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        hastaYonlendir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(hastaKayit.this, listeArayuz.class);
                startActivity(yonlendir);
            }


        });
    }

    private void showToast(String mesaj) {
        Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();
    }

    public void hastaKayit(View view) {

        ////////////////////////////////////////////
        hastaAd = editAd.getText().toString();
        hastaSoyad = editSoyad.getText().toString();
        hastaTc = editTc.getText().toString();
        hastaDTarih = editDogum.getText().toString();
        if (!TextUtils.isEmpty(hastaAd)) {
            if (!TextUtils.isEmpty(hastaSoyad)) {
                if (!TextUtils.isEmpty(hastaTc)) {
                    if (!TextUtils.isEmpty(hastaDTarih)) {
                        try {
                            SQLiteDatabase db = this.openOrCreateDatabase("DiyazlizStok", MODE_PRIVATE, null);
                            db.execSQL("create table if not exists HASTA(ID INTEGER PRIMARY KEY,AD VARCHAR,SOYAD VARCHAR,TCNO VARCHAR,DOGUMTARIHI VARCHAR)");
                            //kayıt işlemi içim
                            String sqlQuery = "INSERT INTO HASTA(AD,SOYAD,TCNO,DOGUMTARIHI) VALUES(?,?,?,?)";
                            SQLiteStatement sqLiteStatement = db.compileStatement(sqlQuery);

                            sqLiteStatement.bindString(1, hastaAd);
                            sqLiteStatement.bindString(2, hastaSoyad);
                            sqLiteStatement.bindString(3, hastaTc);
                            sqLiteStatement.bindString(4, hastaDTarih);
                            sqLiteStatement.execute();
                            showToast("Hasta kaydı gerçekleşti.");
                            nesneleriTemizle();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else showToast("Hastanın doğum tarihini giriniz.");
                } else showToast("Hastanın tc giriniz.");
            } else showToast("Hastanın soyadını giriniz.");
        } else showToast("Hastanın adını giriniz");
    }




}
