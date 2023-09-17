package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mir.diyalizstok.DiyalizVeritabani;
import com.mir.diyalizstok.Models.MalzemeData;
import com.mir.diyalizstok.R;

import java.util.ArrayList;

public class malzemeCheckbox extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> selectedMalzemeler;
    DiyalizVeritabani diyalizVeritabani;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.malzeme_checkbox);
        Toolbar toolbar = findViewById(R.id.toolbar4);
        listView = findViewById(R.id.malzeme_data);
        setSupportActionBar(toolbar);

        selectedMalzemeler = new ArrayList<>();
        //vERİTABNIMI ÇAĞIRDIM
        diyalizVeritabani = new DiyalizVeritabani(this);

        //bir classda olutşruduğum sorgumu listemin içine attık
        ArrayList<MalzemeData> malzemeler = diyalizVeritabani.tumMalzemeleriGetir();
        ArrayAdapter<MalzemeData> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, malzemeler);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_tik) {
            String itemSelected = "Seçilen İlaç \n";
            ArrayList<MalzemeData> selectedMalzemeler = new ArrayList<>();
            SparseBooleanArray checkedPositions = listView.getCheckedItemPositions();

            // Kullanıcının seçtiği pozisyonları alıp, onları selectedMalzemeler listesine ekleyelim
            for (int i = 0; i < checkedPositions.size(); i++) {
                int position = checkedPositions.keyAt(i);
                if (checkedPositions.valueAt(i)) {
                    MalzemeData malzeme = (MalzemeData) listView.getItemAtPosition(position);
                    selectedMalzemeler.add(malzeme);
                }
            }

            Intent intent = new Intent(malzemeCheckbox.this, SeansMalzemeDetay.class);
            intent.putParcelableArrayListExtra("selectedMalzemeler", selectedMalzemeler);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

}
