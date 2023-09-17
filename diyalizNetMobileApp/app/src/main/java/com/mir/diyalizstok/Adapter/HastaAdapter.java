package com.mir.diyalizstok.Adapter;

import static java.lang.String.valueOf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mir.diyalizstok.Models.Hasta;
import com.mir.diyalizstok.R;

import java.util.ArrayList;

public class HastaAdapter extends RecyclerView.Adapter<HastaAdapter.HastaHolder> {
    public void setHastaList(ArrayList<Hasta> hastaList) {
        this.hastaList = hastaList;
    }
    private ArrayList<Hasta> hastaList;
    private Context context;
    private OnItemClickLİstener listener;

    public HastaAdapter(ArrayList<Hasta> hastaList, Context context) {
        this.hastaList = hastaList;
        this.context = context;
    }

    @NonNull
    @Override
    //hangi layout için oluşturucağı yönünde bu metodu kullanırız
    public HastaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.hasta_item, parent, false);
        return new HastaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HastaHolder holder, int position) {
        Hasta hasta = hastaList.get(position);
        holder.setData(hasta);

    }

    @Override
    public int getItemCount() {
        return hastaList.size();
    }



    //her bir ıtemı tutması için tutucumu oluşturuyorum
    class HastaHolder extends RecyclerView.ViewHolder {
        TextView txtAd, txtSoyad, txtTc, txtDogum, txtId;

        public HastaHolder(@NonNull View itemView) {
            super(itemView);
            txtId = (TextView) itemView.findViewById(R.id.idId);
            txtAd = (TextView) itemView.findViewById(R.id.txtAd);
            txtSoyad = (TextView) itemView.findViewById(R.id.txtSoyad);
            txtTc = (TextView) itemView.findViewById(R.id.txtTcno);
            txtDogum = (TextView) itemView.findViewById(R.id.txtDogum);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(hastaList.get(position));
                }
            });
        }

        public void setData(Hasta hasta) {
            this.txtId.setText(hasta.getID().toString());
            this.txtAd.setText(hasta.getAD());
            this.txtSoyad.setText(hasta.getSOYAD());
            this.txtTc.setText(hasta.getTCNO());
            this.txtDogum.setText(hasta.getDOGUMTARIHI());
        }

    }

    public interface OnItemClickLİstener {
        void onItemClick(Hasta hasta);
    }

    public void setOnItemClickListener(OnItemClickLİstener listener) {
        this.listener = listener;
    }


}
