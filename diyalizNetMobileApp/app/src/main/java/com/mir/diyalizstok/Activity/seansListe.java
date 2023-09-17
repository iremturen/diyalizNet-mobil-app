package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.mir.diyalizstok.Adapter.SeansAdapter;
import com.mir.diyalizstok.DiyalizVeritabani;
import com.mir.diyalizstok.Models.Seans;
import com.mir.diyalizstok.R;

public class seansListe extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<Seans> seans;
    RecyclerView recylerView;
    static public SeansMalzemeAktarim seansMalzemeAktarim;
    private SeansAdapter adapter;
    TextView toplam;
    DiyalizVeritabani db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seans_liste);
        recylerView = (RecyclerView) findViewById(R.id.recyleSeans);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        seans = Seans.getData(this); //Verileri doldurduk listeye
        adapter = new SeansAdapter(Seans.getData(this), this);
        toplam = (TextView) findViewById(R.id.toplamSeans);
        toplam.setText(String.valueOf(adapter.getItemCount())); //SeansAdaapter içinde toplam seans sayısını çektik
        recylerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerView.setLayoutManager(manager);
        recylerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SeansAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Seans seans) {
                seansMalzemeAktarim = new SeansMalzemeAktarim(seans.getsHastaAd(), seans.getsHastaSoyad(), seans.getsHastaTc(), seans.getSeansId());
                Intent seansDetayi = new Intent(seansListe.this, SeansMalzemeDetay.class);
                startActivity(seansDetayi);
            }
        });

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (seans == null) {
            seans = Seans.getData(this); //eğer doldurulmamışsa kontrol et ve doldur
        }
        ArrayList<Seans> newSeans = new ArrayList<>();
        for (Seans seans1 : seans) {
            if (seans1.getsHastaTc().toLowerCase().contains(s.toLowerCase()))
                newSeans.add(seans1);
        }
        adapter.setSeansList(newSeans);
        adapter.notifyDataSetChanged();
        return true;
    }
}
