package com.mir.diyalizstok.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.DiyalizVeritabani;
import com.mir.diyalizstok.R;

import java.util.ArrayList;

public class MalzemeKayit extends AppCompatActivity {

    DiyalizVeritabani diyalizVeritabani;
    Button btnKaydet;
    EditText editMalzemeAdi, editMalzemeAciklama, editMalzemeMiktari;
    Spinner spinnerMalzemeTuru;
    private String malzemeAdi, malzemeAciklama, malzemeMiktari, malzemeTuru;

    private void initSpinner() {
        DiyalizVeritabani veritabani = new DiyalizVeritabani(this);
        ArrayList<Integer> spinnerValues = veritabani.getMalzemeTuruIDs();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerValues);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMalzemeTuru.setAdapter(adapter);
    }

    private void init() {
        btnKaydet = findViewById(R.id.btnKayitMalzeme);
        editMalzemeAdi = findViewById(R.id.editMalzemeAdi);
        editMalzemeAciklama = findViewById(R.id.editMalzemeAciklama);
        editMalzemeMiktari = findViewById(R.id.editMalzemeMiktari);
        spinnerMalzemeTuru = findViewById(R.id.spinnerMalzemeTuru);
    }

    private void nesneleriTemizle() {
        editMalzemeAdi.setText("");
        editMalzemeAciklama.setText("");
        editMalzemeMiktari.setText("");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malzeme_kayit);
        diyalizVeritabani = new DiyalizVeritabani(this); //Veritabanımı başlatıım
        init();
        initSpinner();
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!TextUtils.isEmpty(editMalzemeAdi.getText().toString())) {
                        if (!TextUtils.isEmpty(editMalzemeMiktari.getText().toString())) {
                            int durum = 0;
                            if (Integer.parseInt(editMalzemeMiktari.getText().toString()) <= 0) {
                                showToast("Lütfen girdiğiniz miktarı kontrol ediniz,eksi bir değer giremezsiniz!");
                            } else {
                                malzemeEkle(editMalzemeAdi.getText().toString(), editMalzemeAciklama.getText().toString(), Integer.parseInt(editMalzemeMiktari.getText().toString()), spinnerMalzemeTuru.getSelectedItem().toString(), String.valueOf(durum));
                                showToast("Malzeme kaydı gerçekleşmiştir.");
                                nesneleriTemizle();
                            }
                        } else showToast("Saat alanı boş geçilemez");
                    } else showToast("Tarih alanı boş geçilemez");
                } finally {
                    diyalizVeritabani.close();
                }
            }
        });
    }


    private void malzemeEkle(String adi, String aciklama, int miktar, String malzemeTuru, String malzemeDurum) {
        SQLiteDatabase db = diyalizVeritabani.getWritableDatabase();
        ContentValues datas = new ContentValues();
        datas.put("AD", adi);
        datas.put("ACIKLAMA", aciklama);
        datas.put("MIKTAR", miktar);
        datas.put("MALZEMETURU", malzemeTuru);
        datas.put("DURUM", malzemeDurum);
        db.insert("MALZEME", null, datas);

    }

    private void showToast(String mesaj) {
        Toast.makeText(getApplicationContext(), mesaj, Toast.LENGTH_SHORT).show();
    }
}
