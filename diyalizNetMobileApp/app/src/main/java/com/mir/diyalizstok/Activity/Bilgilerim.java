package com.mir.diyalizstok.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.R;

public class Bilgilerim extends AppCompatActivity {

    EditText editTextAd,editTextSoyad,editTextTc,editTextTel,editTextAdres;


    private void init() {
        editTextAd=(EditText) findViewById(R.id.editIsimBilg);
        editTextSoyad=(EditText) findViewById(R.id.editSoyisimBilg);
        editTextTc=(EditText) findViewById(R.id.editTcBilgi);
        editTextTel=(EditText) findViewById(R.id.editTelBilg);
        editTextAdres=(EditText) findViewById(R.id.editAdresBilg);


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilgilerim);
        init();
    }
}
