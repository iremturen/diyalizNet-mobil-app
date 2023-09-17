package com.mir.diyalizstok.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.mir.diyalizstok.Adapter.HastaAdapter;
import com.mir.diyalizstok.Models.Hasta;
import com.mir.diyalizstok.R;


public class listeArayuz extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<Hasta> hastas;
    RecyclerView recylerView;
    TextView toplam;
    private HastaAdapter adapter;
    static public SeansAktarim seansAktarim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hasta_liste);
        recylerView = findViewById(R.id.recyclist);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        hastas = Hasta.getData(this); // Hasta verilerini doldur
        adapter = new HastaAdapter(hastas, this);
        toplam=(TextView) findViewById(R.id.toplamHasta);
        toplam.setText(String.valueOf(adapter.getItemCount()));
        recylerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerView.setLayoutManager(manager);
        recylerView.setAdapter(adapter);


        //Listeden bir veriye tıkladığımızda sayfalar arası veri aktarımı için kullanılır.
        try {
            adapter.setOnItemClickListener(new HastaAdapter.OnItemClickLİstener() {
                @Override
                public void onItemClick(Hasta hasta) {
                    seansAktarim = new SeansAktarim(hasta.getAD(), hasta.getSOYAD(), hasta.getTCNO(), hasta.getID());
                    Intent seansIntent = new Intent(listeArayuz.this, seansKayit.class);
                    startActivity(seansIntent);
                }
            });
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
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (hastas == null) {
            hastas = Hasta.getData(this); // Hasta verilerini doldur (eğer henüz doldurulmamışsa)
        }

        ArrayList<Hasta> newHasta = new ArrayList<>();
        for (Hasta hasta : hastas) {
            if (hasta.getTCNO().toLowerCase().contains(s.toLowerCase()))
                newHasta.add(hasta);
        }
        adapter.setHastaList(newHasta);
        adapter.notifyDataSetChanged();
        return true;
    }
}

