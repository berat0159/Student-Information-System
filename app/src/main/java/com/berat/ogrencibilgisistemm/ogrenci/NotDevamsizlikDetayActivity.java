package com.berat.ogrencibilgisistemm.ogrenci;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.berat.ogrencibilgisistemm.adapter.DevamsizlikAdapter;
import com.berat.ogrencibilgisistemm.adapter.SonucAdapter;
import com.berat.ogrencibilgisistemm.databinding.ActivityNotDevamsizlikDetayBinding;
import com.berat.ogrencibilgisistemm.modul.DevamsizlikKayitlari;
import com.berat.ogrencibilgisistemm.modul.SinavSonuclari;

import java.util.ArrayList;

public class NotDevamsizlikDetayActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private ArrayList<SinavSonuclari> sinavSonuclariArrayList;
    private ArrayList<DevamsizlikKayitlari> devamsizlikKayitlariArrayList;
    private SonucAdapter sonucAdapter;
    private DevamsizlikAdapter devamsizlikAdapter;
    private String ogrID;
    private ActivityNotDevamsizlikDetayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotDevamsizlikDetayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sqLiteDatabase=this.openOrCreateDatabase("Sistem",MODE_PRIVATE,null);

        Intent intent=getIntent();
        ogrID=intent.getStringExtra("intentOgrID");

        sinavSonuclariArrayList=new ArrayList<>();
        devamsizlikKayitlariArrayList=new ArrayList<>();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Not Bilgi");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding.recyclerViewNot.setLayoutManager(new LinearLayoutManager(this));
        sonucAdapter=new SonucAdapter(sinavSonuclariArrayList);
        binding.recyclerViewNot.setAdapter(sonucAdapter);

        binding.recyclerViewDevamsizlik.setLayoutManager(new LinearLayoutManager(this));
        devamsizlikAdapter=new DevamsizlikAdapter(devamsizlikKayitlariArrayList);
        binding.recyclerViewDevamsizlik.setAdapter(devamsizlikAdapter);

        sinavBilgileri();
        devamsizlikBilgileri();
    }


    public void sinavBilgileri(){
        String dersAdi;
        String sinavTipi;
        double sinavNotu;
        cursor=sqLiteDatabase.rawQuery("SELECT * FROM sinavsonuclari JOIN dersler ON sinavsonuclari.dersID=dersler.dersID" +
                " WHERE sinavsonuclari.ogrenciID=?",new String[]{ogrID});
        while (cursor.moveToNext()){
            dersAdi=cursor.getString(6);
            sinavTipi=cursor.getString(1);
            sinavNotu=cursor.getDouble(2);
            SinavSonuclari sinavSonuclari=new SinavSonuclari(dersAdi,sinavTipi,sinavNotu);
            sinavSonuclariArrayList.add(sinavSonuclari);
        }
        sonucAdapter.notifyDataSetChanged();
        cursor.close();
    }
    public void devamsizlikBilgileri(){
         String dersAdi;
         String tarih;
         cursor=sqLiteDatabase.rawQuery("SELECT * FROM devamsizlikKayitlari JOIN dersler ON devamsizlikKayitlari.dersID=dersler.dersID" +
                 " WHERE devamsizlikKayitlari.ogrenciID=?",new String[]{ogrID});
         while (cursor.moveToNext()){
             dersAdi=cursor.getString(5);
             tarih=cursor.getString(1);
             DevamsizlikKayitlari devamsizlikKayitlari=new DevamsizlikKayitlari(dersAdi,tarih);
             devamsizlikKayitlariArrayList.add(devamsizlikKayitlari);
         }
         devamsizlikAdapter.notifyDataSetChanged();
         cursor.close();
    }

}