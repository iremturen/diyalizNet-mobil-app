package com.mir.diyalizstok.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.DiyalizVeritabani;
import com.mir.diyalizstok.Models.MalzemeData;
import com.mir.diyalizstok.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SeansMalzemeDetay extends AppCompatActivity {
    private String malzemeAciklama, tarih;
    private Integer malzemeID, malzemeMiktar, hareketTuru, durum;
    DiyalizVeritabani diyalizVeritabani;

    TextView txtAd, txtSoyad, txtTc, txtSeansId, deneme;
    String hAd, hSoyad, hTc;
    ImageView imgMalzemeSeans;
    Button btnMalzemeKayit;
    Integer sId;
    ArrayList<Long> insertedRecordIds = new ArrayList<>();

    private void init() {
        txtAd = (TextView) findViewById(R.id.txtAdS);
        txtSoyad = (TextView) findViewById(R.id.txtSoyadS);
        txtTc = (TextView) findViewById(R.id.txtTcS);
        txtSeansId = (TextView) findViewById(R.id.txtIdS);
        imgMalzemeSeans = (ImageView) findViewById(R.id.imgMalzemeSeans);
        deneme = (TextView) findViewById(R.id.txtDeneme);
        btnMalzemeKayit = (Button) findViewById(R.id.btnMalzemeKullan);

        hAd = seansListe.seansMalzemeAktarim.getHastaAdi();
        hSoyad = seansListe.seansMalzemeAktarim.getHastaSoyadi();
        hTc = seansListe.seansMalzemeAktarim.getTcNo();
        sId = seansListe.seansMalzemeAktarim.getSeansId();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.seans_malzeme);
        init();
        diyalizVeritabani = new DiyalizVeritabani(this);
        ///tarih formatını almak için
        Calendar calendar = Calendar.getInstance();
        Date tarih = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(tarih);
        ///
        Intent intent = getIntent();
        ArrayList<MalzemeData> selectedMalzemeler = intent.getParcelableArrayListExtra("selectedMalzemeler");
        LinearLayout container = findViewById(R.id.container);

        //Kaydedilen stok hareket ıd sinin tutmak için
        ////


        if (selectedMalzemeler != null && selectedMalzemeler.size() > 0) {
            for (int i = 0; i < selectedMalzemeler.size(); i++) {
                MalzemeData malzeme = selectedMalzemeler.get(i);
                int malzemeId = Integer.valueOf(malzeme.getId());
                TextView textViewId = new TextView(this);
                textViewId.setText(malzeme.getId());
                textViewId.setTextColor(Color.rgb(48,61,98));
                textViewId.setVisibility(View.INVISIBLE);
                textViewId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                container.addView(textViewId);

                TextView textViewAd = new TextView(this);
                textViewAd.setText(malzeme.getAd());
                textViewAd.setTextColor(Color.rgb(48,61,98));
                textViewAd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                container.addView(textViewAd);
                EditText editTextAciklama = new EditText(this);
                int editTextAciklamaId = malzemeId + 1000 + i;
                editTextAciklama.setId(editTextAciklamaId);
                editTextAciklama.setHint("Açıklama girin");
                container.addView(editTextAciklama);

                EditText editTextMiktar = new EditText(this);
                int editTextMiktarId = malzemeId + 2000 + i;
                editTextMiktar.setId(editTextMiktarId);
                editTextMiktar.setHint("Miktar girin");
                editTextMiktar.setInputType(InputType.TYPE_CLASS_NUMBER);
                container.addView(editTextMiktar);

                deneme.setText("Malzemelerinizi mikarlarına 0 veya 0'dan küçük bir sayı veremezsiniz.");
                deneme.setTextColor(Color.rgb(111,117,144));
            }
        } else {
            deneme.setText("Seçili malzeme bulunmamaktadır.");
        }
        btnMalzemeKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int durum = 0;
                for (int i = 0; i < selectedMalzemeler.size(); i++) {
                    MalzemeData malzeme = selectedMalzemeler.get(i);
                    int malzemeId = Integer.valueOf(malzeme.getId());
                    EditText editTextAciklama = findViewById(malzemeId + 1000 + i);
                    EditText ediTextMiktar = findViewById(malzemeId + 2000 + i);
                    int malzemeMiktar = Integer.valueOf(ediTextMiktar.getText().toString());
                    int hareketTuru = 2;
                    String aciklama = editTextAciklama.getText().toString();
                    malzemeEkle(malzemeId, malzemeMiktar, hareketTuru, aciklama, formattedDate, String.valueOf(durum));
                }
                for (long id : insertedRecordIds) {
                    seansMalzemeEkle(sId, (int) id, 0);
                }
            }
        });

        if (!TextUtils.isEmpty(hAd) && !TextUtils.isEmpty(hSoyad) && !TextUtils.isEmpty(hTc) && !TextUtils.isEmpty(sId.toString())) {
            txtAd.setText(hAd);
            txtSoyad.setText(hSoyad);
            txtTc.setText(hTc);
            txtSeansId.setText(sId.toString());
        }

        imgMalzemeSeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlen = new Intent(SeansMalzemeDetay.this, malzemeCheckbox.class);
                startActivity(yonlen);
            }
        });
    }
    private void showToast(String mesaj) {
        Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();
    }

    private void malzemeEkle(Integer malzemeId, Integer malzemeMiktar, Integer hareketTuru, String aciklama, String tarih, String durum) {
        SQLiteDatabase db = diyalizVeritabani.getWritableDatabase();
        ContentValues datas = new ContentValues();
        datas.put("MALZEMEID", malzemeId);
        datas.put("MIKTAR", malzemeMiktar);
        datas.put("HAREKETTURU", hareketTuru);
        datas.put("ACIKLAMA", aciklama);
        datas.put("TARIH", tarih);
        datas.put("DURUM", durum);
        long kayitID = db.insertOrThrow("STOKHAREKET", null, datas);
        insertedRecordIds.add(kayitID);
        db.close();

    }

    private void seansMalzemeEkle(Integer seansID, Integer stokhareketID, Integer durum) {
        SQLiteDatabase db = diyalizVeritabani.getWritableDatabase();
        ContentValues datas = new ContentValues();
        datas.put("SEANSID", seansID);
        datas.put("STOKHAREKETID", stokhareketID);
        datas.put("DURUM", durum);
        db.insertOrThrow("SEANSMALZEME", null, datas);
        db.close();
    }

}
