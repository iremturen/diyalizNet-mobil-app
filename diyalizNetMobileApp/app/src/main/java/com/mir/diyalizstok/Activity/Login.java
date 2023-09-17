package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mir.diyalizstok.DiyalizVeritabani;
import com.mir.diyalizstok.R;

public class Login extends AppCompatActivity {

    Button login,signUp_yon;

    EditText editTc,editPassword;

    private String username, password;

    DiyalizVeritabani db;
    private void init() {
        editTc=(EditText) findViewById(R.id.editTextTextTc);
        editPassword=(EditText) findViewById(R.id.editTextTextPassword);
        login=(Button) findViewById(R.id.buttonLogin);
        signUp_yon=(Button) findViewById(R.id.signUp_yon);
        db = new DiyalizVeritabani(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        init();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_tc = editTc.getText().toString();
                String pass = editPassword.getText().toString();

                if (user_tc.equals("") || pass.equals(""))
                    Toast.makeText(Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = db.checkusernamepassword(user_tc, pass);
                    if (checkuserpass == true) {
                        Toast.makeText(Login.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), homeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        signUp_yon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });
    }


}
