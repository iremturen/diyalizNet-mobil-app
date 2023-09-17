package com.mir.diyalizstok.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mir.diyalizstok.R;
import com.mir.diyalizstok.Models.Seans;

import java.util.ArrayList;

public class SeansAdapter extends RecyclerView.Adapter<SeansAdapter.SeansHolder> {
    private ArrayList<Seans> seansList;
    private Context context;
    private OnItemClickListener listener;

    public void setSeansList(ArrayList<Seans> seansList) {
        this.seansList = seansList;
    }

    public SeansAdapter(ArrayList<Seans> seansList, Context context) {
        this.seansList = seansList;
        this.context = context;
    }

    @NonNull
    @Override
    public SeansHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.seans_item, parent, false);
        return new SeansHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeansHolder holder, int position) {
        Seans seans = seansList.get(position);
        holder.setData(seans);

    }

    @Override
    public int getItemCount() {
        return seansList.size();
    }

    public void setOnItemClickListener(HastaAdapter.OnItemClickLİstener onItemClickLİstener) {
    }

    class SeansHolder extends RecyclerView.ViewHolder {
        TextView txtAd, txtSoyad, txtTc, txtTarih, txtSaat;

        public SeansHolder(@NonNull View itemView) {
            super(itemView);
            txtAd = (TextView) itemView.findViewById(R.id.txtAd);
            txtSoyad = (TextView) itemView.findViewById(R.id.txtSoyad);
            txtSaat = (TextView) itemView.findViewById(R.id.txtSaatSeans);
            txtTarih = (TextView) itemView.findViewById(R.id.txtTarihSeans);
            txtTc = (TextView) itemView.findViewById(R.id.txtTcSeans);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(seansList.get(position));
                }
            });
        }

        public void setData(Seans seans) {
            this.txtAd.setText(seans.getsHastaAd());
            this.txtSoyad.setText(seans.getsHastaSoyad());
            this.txtTc.setText(seans.getsHastaTc());
            this.txtTarih.setText(seans.getsTarih());
            this.txtSaat.setText(seans.getsSaat());
        }
    }


    public interface OnItemClickListener {
        void onItemClick(Seans seans);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

