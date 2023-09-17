package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.R;

public class homeActivity extends AppCompatActivity {
    ImageView imgHasta, imgMalzeme, imgStok, imgListe, imgSeans, imgAyarlar;
    ImageView logout;

    private void init() {
        imgHasta = (ImageView) findViewById(R.id.imgClickHasta);
        imgSeans = (ImageView) findViewById(R.id.imgClickSeans);
        imgListe = (ImageView) findViewById(R.id.imgClickListe);
        imgMalzeme = (ImageView) findViewById(R.id.imgClickMalzeme);
        imgAyarlar=(ImageView) findViewById(R.id.imgClickAyar);
        logout=(ImageView) findViewById(R.id.logout);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        init();
        imgHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(homeActivity.this, hastaKayit.class);
                startActivity(yonlendir);
            }
        });

        imgSeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yo = new Intent(homeActivity.this, seansListe.class);
                startActivity(yo);
            }
        });

        imgListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(homeActivity.this, malzemeCheckbox.class);
                startActivity(yonlendir);
            }
        });

        imgMalzeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(homeActivity.this, MalzemeKayit.class);
                startActivity(yonlendir);
            }
        });

        imgAyarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(homeActivity.this, Ayarlar.class);
                startActivity(yonlendir);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yon = new Intent(homeActivity.this,Login.class);
                startActivity(yon);
            }
        });
    }


}
