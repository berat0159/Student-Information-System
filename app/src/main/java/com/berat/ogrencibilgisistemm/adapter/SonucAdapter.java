package com.berat.ogrencibilgisistemm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berat.ogrencibilgisistemm.databinding.SonucRowBinding;
import com.berat.ogrencibilgisistemm.modul.SinavSonuclari;

import java.util.ArrayList;

public class SonucAdapter extends RecyclerView.Adapter<SonucAdapter.SonucHolder> {
    private ArrayList<SinavSonuclari> arrayList;
    public SonucAdapter(ArrayList<SinavSonuclari> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public SonucHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SonucRowBinding sonucRowBinding=SonucRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SonucHolder(sonucRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SonucHolder holder, int position) {
            holder.sonucRowBinding.dersAdi.setText("Ders Adı: "+arrayList.get(position).dersAdi);
            holder.sonucRowBinding.sinavTipi.setText("Sınav Tipi: "+arrayList.get(position).sinavTipi);
            holder.sonucRowBinding.sinavNotu.setText("Sınav Notu: "+(arrayList.get(position).sinavNotu));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class SonucHolder extends RecyclerView.ViewHolder{
        private SonucRowBinding sonucRowBinding;

        public SonucHolder(@NonNull SonucRowBinding sonucRowBinding) {
            super(sonucRowBinding.getRoot());
            this.sonucRowBinding=sonucRowBinding;
        }
    }
}
