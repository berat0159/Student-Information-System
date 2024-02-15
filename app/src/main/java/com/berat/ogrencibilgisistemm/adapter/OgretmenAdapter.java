package com.berat.ogrencibilgisistemm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berat.ogrencibilgisistemm.databinding.OgrenciRowBinding;
import com.berat.ogrencibilgisistemm.modul.Ogretmenler;

import java.util.ArrayList;

public class OgretmenAdapter extends RecyclerView.Adapter<OgretmenAdapter.OgretmenHolder> {
    private ArrayList<Ogretmenler> arrayList;
    public OgretmenAdapter(ArrayList<Ogretmenler> arrayList){
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public OgretmenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OgrenciRowBinding ogrenciRowBinding=OgrenciRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new OgretmenHolder(ogrenciRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OgretmenHolder holder, int position) {
        holder.ogrenciRowBinding.ogrId.setText("ID: "+arrayList.get(position).ogretmenId);
        holder.ogrenciRowBinding.ogrAd.setText("Adı: "+arrayList.get(position).ogretmenAd);
        holder.ogrenciRowBinding.ogrSoyad.setText("Soyadı: "+arrayList.get(position).ogretmenSoyad);
        holder.ogrenciRowBinding.ogrTel.setText("Telefon: "+arrayList.get(position).ogretmenTel);
        holder.ogrenciRowBinding.ogrEposta.setText("E-posta: "+arrayList.get(position).ogretmenEposta);
        holder.ogrenciRowBinding.ogrDogum.setText("Doğum Tarihi: "+arrayList.get(position).dogumTarihi);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class OgretmenHolder extends RecyclerView.ViewHolder{
        private OgrenciRowBinding ogrenciRowBinding;

        public OgretmenHolder(@NonNull OgrenciRowBinding ogrenciRowBinding) {
            super(ogrenciRowBinding.getRoot());
            this.ogrenciRowBinding=ogrenciRowBinding;

        }
    }
}
