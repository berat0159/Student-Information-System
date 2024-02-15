package com.berat.ogrencibilgisistemm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berat.ogrencibilgisistemm.databinding.DevamsizlikRowBinding;
import com.berat.ogrencibilgisistemm.modul.DevamsizlikKayitlari;

import java.util.ArrayList;

public class DevamsizlikAdapter extends RecyclerView.Adapter<DevamsizlikAdapter.DevamsizlikHolder> {
    private ArrayList<DevamsizlikKayitlari> arrayList;
    public DevamsizlikAdapter(ArrayList<DevamsizlikKayitlari> arrayList){
        this.arrayList=arrayList;

    }
    @NonNull
    @Override
    public DevamsizlikHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DevamsizlikRowBinding devamsizlikRowBinding=DevamsizlikRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DevamsizlikHolder(devamsizlikRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DevamsizlikHolder holder, int position) {
        holder.devamsizlikRowBinding.dersAdi.setText("Ders Adı: "+arrayList.get(position).dersAdi);
        holder.devamsizlikRowBinding.devamsizlikTarih.setText("Tarihi: "+arrayList.get(position).tarih);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class DevamsizlikHolder extends RecyclerView.ViewHolder{
        private DevamsizlikRowBinding devamsizlikRowBinding;
        public DevamsizlikHolder(@NonNull DevamsizlikRowBinding devamsizlikRowBinding) {
            super(devamsizlikRowBinding.getRoot());
            this.devamsizlikRowBinding=devamsizlikRowBinding;
        }
    }
}
