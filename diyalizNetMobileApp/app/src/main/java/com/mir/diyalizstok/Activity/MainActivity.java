package com.mir.diyalizstok.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mir.diyalizstok.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    Button btnHasta, btnSeans, btnMalzeme, btnStok;

    public void btnAnaSayfaYonlendir(View view) {
        btnHasta = findViewById(R.id.btnHasta);
        btnHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yonlendir = new Intent(MainActivity.this, hastaKayit.class);
                startActivity(yonlendir);
            }
        });
    }
}