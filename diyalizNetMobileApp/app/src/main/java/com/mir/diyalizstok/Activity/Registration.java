package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.DiyalizVeritabani;
import com.mir.diyalizstok.R;

public class Registration extends AppCompatActivity {

    Button kayıt,signUp_yon;

    EditText editTC,editPassword,editIsim,editSoyisim,repassword;

    DiyalizVeritabani db;

    private void init() {
        editTC=(EditText) findViewById(R.id.editTCNO);
        editPassword=(EditText) findViewById(R.id.editTextPassword);
        editIsim=(EditText) findViewById(R.id.editTextIsım);
        editSoyisim=(EditText) findViewById(R.id.editTextSoyisim);
        repassword=(EditText) findViewById(R.id.editTextRePassword);
        kayıt=(Button) findViewById(R.id.btnUserKayit);
        signUp_yon=(Button) findViewById(R.id.login_yon);
        db=new DiyalizVeritabani(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        init();

        kayıt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tc = editTC.getText().toString();
                String isim = editIsim.getText().toString();
                String soyisim = editSoyisim.getText().toString();
                String pass = editPassword.getText().toString();
                String repass = repassword.getText().toString();

                if (tc.equals("") || pass.equals("") || repass.equals("") || isim.equals("")||isim.equals(""))
                    Toast.makeText(Registration.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = db.checkusername(tc);
                        if (checkuser == false) {
                            Boolean insert = db.insertData(isim,soyisim,tc, pass);
                            if (insert == true) {
                                Toast.makeText(Registration.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Registration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Registration.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Registration.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    signUp_yon.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(Registration.this,Login.class);
            startActivity(intent);
        }
    });
    }

}
