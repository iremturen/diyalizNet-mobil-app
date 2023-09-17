package com.mir.diyalizstok.Activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

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

public class MalzemeDeneme extends AppCompatActivity {

    Button btnKaydet;
    DiyalizVeritabani diyalizVeritabani;
    private String malzemeAciklama, tarih;
    private Integer malzemeID, malzemeMiktar, hareketTuru, durum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malzeme_deneme);
        diyalizVeritabani = new DiyalizVeritabani(this);
        Calendar calendar = Calendar.getInstance();
        Date tarih = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(tarih);
        ArrayList<MalzemeData> selectedMalzemeler = getIntent().getParcelableArrayListExtra("selectedMalzemeler");

        LinearLayout container = findViewById(R.id.container);
        btnKaydet = findViewById(R.id.btnKaydetMalzeme);

        for (int i = 0; i < selectedMalzemeler.size(); i++) {
            MalzemeData malzeme = selectedMalzemeler.get(i);

            int malzemeId = Integer.valueOf(malzeme.getId());

            TextView textViewId = new TextView(this);
            textViewId.setText(malzeme.getId());
            textViewId.setTextColor(Color.BLUE);
            textViewId.setVisibility(View.INVISIBLE);
            textViewId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            container.addView(textViewId);

            TextView textViewAd = new TextView(this);
            textViewAd.setText(malzeme.getAd());
            textViewAd.setTextColor(Color.BLUE);
            textViewAd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            container.addView(textViewAd);
            EditText editTextAciklama = new EditText(this);
            int editTextAciklamaId = malzemeId + 1000 + i;
            editTextAciklama.setId(editTextAciklamaId);
            editTextAciklama.setHint("Açıklama girin");
            container.addView(editTextAciklama);

            EditText ediTextMiktar = new EditText(this);
            int editTextMiktarId = malzemeId + 2000 + i;
            ediTextMiktar.setId(editTextMiktarId);
            ediTextMiktar.setHint("Miktar girin");
            ediTextMiktar.setInputType(InputType.TYPE_CLASS_NUMBER);
            container.addView(ediTextMiktar);
        }


        btnKaydet.setOnClickListener(new View.OnClickListener() {
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
            }
        });


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
        db.insertOrThrow("STOKHAREKET", null, datas);

    }

}

