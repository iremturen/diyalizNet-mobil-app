package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.R;

public class Ayarlar extends AppCompatActivity {

    TextView btnIletisim,btnHakkimizda,btnBilgi,btnSozlesme;

    private void init() {
        btnIletisim=(TextView) findViewById(R.id.bize_ulasin);
        btnHakkimizda=(TextView) findViewById(R.id.hakkimizda);
        btnBilgi=(TextView) findViewById(R.id.bilgilerim);
        btnSozlesme=(TextView) findViewById(R.id.sozlesme);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayarlar_activity);
        init();

        btnIletisim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(Ayarlar.this, Iletisim.class);
                startActivity(yonlendir);
            }
        });

        btnSozlesme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(Ayarlar.this, Sertifika.class);
                startActivity(yonlendir);
            }
        });

        btnHakkimizda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(Ayarlar.this, Hakkimizda.class);
                startActivity(yonlendir);
            }
        });

        btnBilgi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(Ayarlar.this, Bilgilerim.class);
                startActivity(yonlendir);
            }
        });

    }
}
