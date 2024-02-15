package com.berat.ogrencibilgisistemm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berat.ogrencibilgisistemm.databinding.DerslerimRowBinding;
import com.berat.ogrencibilgisistemm.modul.DersProgrami;

import java.util.ArrayList;

public class DersProgramiAdapter extends RecyclerView.Adapter<DersProgramiAdapter.DersProgramiHolder> {
    private ArrayList<DersProgrami> arrayList;
    public DersProgramiAdapter(ArrayList<DersProgrami> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public DersProgramiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DerslerimRowBinding derslerimRowBinding=DerslerimRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DersProgramiHolder(derslerimRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DersProgramiHolder holder, int position) {
        holder.derslerimRowBinding.dersAdi.setText("Ders Adı: "+arrayList.get(position).dersAdi);
        holder.derslerimRowBinding.dersGunu.setText("Ders Günü: "+arrayList.get(position).dersGunu);
        holder.derslerimRowBinding.basSaat.setText("Başlangic Saati: "+arrayList.get(position).baslangicSaat);
        holder.derslerimRowBinding.bitSaat.setText("Bitiş Saati: "+arrayList.get(position).bitisSaat);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class DersProgramiHolder extends RecyclerView.ViewHolder{
        private DerslerimRowBinding derslerimRowBinding;

        public DersProgramiHolder(@NonNull DerslerimRowBinding derslerimRowBinding) {
            super(derslerimRowBinding.getRoot());
            this.derslerimRowBinding=derslerimRowBinding;
        }
    }
}
