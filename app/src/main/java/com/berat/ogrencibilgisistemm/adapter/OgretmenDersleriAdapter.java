package com.berat.ogrencibilgisistemm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berat.ogrencibilgisistemm.databinding.DerslerimRowBinding;
import com.berat.ogrencibilgisistemm.modul.OgretmenDersleri;

import java.util.ArrayList;

public class OgretmenDersleriAdapter extends RecyclerView.Adapter<OgretmenDersleriAdapter.DerslerimHolder> {
    private ArrayList<OgretmenDersleri> arrayList;
    public OgretmenDersleriAdapter(ArrayList<OgretmenDersleri> arrayList){
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public DerslerimHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        DerslerimRowBinding derslerimRowBinding=DerslerimRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new DerslerimHolder(derslerimRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DerslerimHolder holder, int position) {
        holder.derslerimRowBinding.dersAdi.setText("Ders Adı: "+arrayList.get(position).dersAdi);
        holder.derslerimRowBinding.dersGunu.setText("Ders Günü: "+arrayList.get(position).dersGunu);
        holder.derslerimRowBinding.basSaat.setText("Başlangıc Saati: "+arrayList.get(position).baslangicSaati);
        holder.derslerimRowBinding.bitSaat.setText("Bitiş Saati: "+arrayList.get(position).bitisSaati);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class DerslerimHolder extends RecyclerView.ViewHolder{
        private DerslerimRowBinding derslerimRowBinding;
        public DerslerimHolder(@NonNull DerslerimRowBinding derslerimRowBinding) {
            super(derslerimRowBinding.getRoot());
            this.derslerimRowBinding=derslerimRowBinding;
        }
    }
}
