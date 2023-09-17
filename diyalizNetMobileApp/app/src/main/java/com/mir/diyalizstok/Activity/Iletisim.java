package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.R;

public class Iletisim extends AppCompatActivity {

    ImageView twt,ig,face;

    private void init() {
        twt=(ImageView) findViewById(R.id.twt);
        ig=(ImageView) findViewById(R.id.ig);
        face=(ImageView) findViewById(R.id.face);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iletisim);
        init();

        twt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link= Uri.parse("https://twitter.com/?lang=tr");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);
            }
        });


        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link= Uri.parse("https://www.instagram.com/accounts/suspended/?next=https%3A%2F%2Fwww.instagram.com%2F%3F__coig_ufac%3D1");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link= Uri.parse("https://tr-tr.facebook.com/");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);
            }
        });
    }
}
