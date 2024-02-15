package com.berat.ogrencibilgisistemm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berat.ogrencibilgisistemm.databinding.ActivityOgrenciViewBinding;
import com.berat.ogrencibilgisistemm.databinding.OgrenciRowBinding;
import com.berat.ogrencibilgisistemm.modul.Ogrenciler;

import java.util.ArrayList;

public class OgrenciAdapter extends RecyclerView.Adapter<OgrenciAdapter.OgrenciHolder> {
    private ArrayList<Ogrenciler> arrayList;
    public OgrenciAdapter(ArrayList<Ogrenciler> arrayList){
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public OgrenciHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OgrenciRowBinding ogrenciRowBinding=OgrenciRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new  OgrenciHolder(ogrenciRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OgrenciHolder holder, int position) {
        holder.ogrenciRowBinding.ogrId.setText("ID: "+arrayList.get(position).ogrenciId);
        holder.ogrenciRowBinding.ogrAd.setText("Adı: "+arrayList.get(position).ogrenciAd);
        holder.ogrenciRowBinding.ogrSoyad.setText("Soyadı: "+arrayList.get(position).ogrenciSoyad);
        holder.ogrenciRowBinding.ogrTel.setText("Telefon: "+arrayList.get(position).ogrenciTel);
        holder.ogrenciRowBinding.ogrEposta.setText("Eposta: "+arrayList.get(position).ogrenciEposta);
        holder.ogrenciRowBinding.ogrDogum.setText("Doğum Tarihi:"+arrayList.get(position).dogumTarihi);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class OgrenciHolder extends RecyclerView.ViewHolder{
        OgrenciRowBinding ogrenciRowBinding;
        public OgrenciHolder(OgrenciRowBinding ogrenciRowBinding) {

            super(ogrenciRowBinding.getRoot());
            this.ogrenciRowBinding=ogrenciRowBinding;



        }
    }
}
