package com.berat.ogrencibilgisistemm.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berat.ogrencibilgisistemm.databinding.OgrenciRowBinding;
import com.berat.ogrencibilgisistemm.modul.Ogrenciler;
import com.berat.ogrencibilgisistemm.ogrenci.OgrenciDetayActivity;

import java.util.ArrayList;

public class OgrenciDetayAdapter extends RecyclerView.Adapter<OgrenciDetayAdapter.DetayHolder> {
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private ArrayList<Ogrenciler> arrayList;
    public OgrenciDetayAdapter(ArrayList<Ogrenciler> arrayList){
        this.arrayList=arrayList;

    }
    @NonNull
    @Override
    public DetayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OgrenciRowBinding ogrenciRowBinding=OgrenciRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DetayHolder(ogrenciRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetayHolder holder, int position) {
        holder.ogrenciRowBinding.ogrId.setText("ID: "+arrayList.get(position).ogrenciId);
        holder.ogrenciRowBinding.ogrAd.setText("Adı: "+arrayList.get(position).ogrenciAd);
        holder.ogrenciRowBinding.ogrSoyad.setText("Soyadı: "+arrayList.get(position).ogrenciSoyad);
        holder.ogrenciRowBinding.ogrTel.setText("Telefon: "+arrayList.get(position).ogrenciTel);
        holder.ogrenciRowBinding.ogrEposta.setText("E-posta: "+arrayList.get(position).ogrenciEposta);
        holder.ogrenciRowBinding.ogrDogum.setText("Doğum Tarihi: "+arrayList.get(position).dogumTarihi);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(holder.itemView.getContext(), OgrenciDetayActivity.class);
                intent.putExtra("detayID",arrayList.get(holder.getAdapterPosition()).ogrenciId);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class DetayHolder extends RecyclerView.ViewHolder{
        private OgrenciRowBinding ogrenciRowBinding;
        public DetayHolder(@NonNull OgrenciRowBinding ogrenciRowBinding) {
            super(ogrenciRowBinding.getRoot());
            this.ogrenciRowBinding=ogrenciRowBinding;
        }
    }
}
