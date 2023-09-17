package com.mir.diyalizstok.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.R;

public class Sertifika extends AppCompatActivity {

    ImageView sert1,sert2,ser3;

    private void init() {
        sert1=(ImageView) findViewById(R.id.sert1);
        //sert2=(ImageView) findViewById(R.id.hakkimizda);
        //ser3=(ImageView) findViewById(R.id.bilgilerim);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sertifika);
        init();

    }
}
