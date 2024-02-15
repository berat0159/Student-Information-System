package com.berat.ogrencibilgisistemm.ogrenci;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.berat.ogrencibilgisistemm.R;
import com.berat.ogrencibilgisistemm.adapter.DevamsizlikAdapter;
import com.berat.ogrencibilgisistemm.adapter.SonucAdapter;
import com.berat.ogrencibilgisistemm.databinding.ActivityDevamsizlikNotEkleAcitvityBinding;
import com.berat.ogrencibilgisistemm.modul.DevamsizlikKayitlari;
import com.berat.ogrencibilgisistemm.modul.SinavSonuclari;
import com.berat.ogrencibilgisistemm.yonetim.YtDersEkleActivity;

import java.util.ArrayList;

public class DevamsizlikNotEkleAcitvity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    public String ogrID;
    private SQLiteStatement sqLiteStatement;
    private ArrayList<SinavSonuclari> sinavSonuclariArrayList;
    private ArrayList<DevamsizlikKayitlari> devamsizlikKayitlariArrayList;
    private SonucAdapter sonucAdapter;
    private DevamsizlikAdapter devamsizlikAdapter;
    private ActivityDevamsizlikNotEkleAcitvityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDevamsizlikNotEkleAcitvityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sinavSonuclariArrayList=new ArrayList<>();
        devamsizlikKayitlariArrayList=new ArrayList<>();
        sqLiteDatabase=this.openOrCreateDatabase("Sistem", MODE_PRIVATE,null);

        Intent intent=getIntent();
        ogrID=intent.getStringExtra("ogrId");

        Toast.makeText(this,"OGRENCİ İD : " +ogrID,Toast.LENGTH_LONG).show();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Not Ekle");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding.notRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sonucAdapter=new SonucAdapter(sinavSonuclariArrayList);
        binding.notRecyclerView.setAdapter(sonucAdapter);

        binding.devamsizlikRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        devamsizlikAdapter=new DevamsizlikAdapter(devamsizlikKayitlariArrayList);
        binding.devamsizlikRecyclerView.setAdapter(devamsizlikAdapter);

        sinavSonuclari();
        devamsizlik();
        dersSpinner();
        devamsizlikSayisi();
        notOrtalama();
    }

    public void notOrtalama(){

        double notOrtalama=0;
        cursor=sqLiteDatabase.rawQuery("SELECT AVG(sinavsonuclari.sinavNotu) FROM sinavsonuclari WHERE sinavsonuclari.ogrenciID=?",
                new String[]{ogrID});
        while (cursor.moveToNext()){
            notOrtalama=cursor.getDouble(0);
        }
        cursor.close();
        binding.ortalama.setText(String.valueOf(notOrtalama));


    }
    public void devamsizlikSayisi(){
        int devamsizlikSayisi=0;
        try {
            cursor=sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM devamsizlikKayitlari WHERE devamsizlikKayitlari.ogrenciID=?"
                    ,new String[]{ogrID});
            while (cursor.moveToNext()){
                    devamsizlikSayisi=cursor.getInt(0);
            }
            cursor.close();
            binding.devamSayisi.setText(String.valueOf(devamsizlikSayisi));

        }catch (Exception e){
            e.fillInStackTrace();
        }
    }
    public void dersSpinner(){
        cursor=sqLiteDatabase.rawQuery("SELECT dersAdi FROM  dersler",null);
        String[] dersAdlari=new String[cursor.getCount()];
        int i=0;
        while (cursor.moveToNext()){
            dersAdlari[i] =cursor.getString(0);
            i++;

        }
        cursor.close();
        // spinner bağlama işlemi
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(DevamsizlikNotEkleAcitvity.this, android.R.layout.simple_spinner_item,dersAdlari);
        binding.dersSpinner.setAdapter(adapter);
        binding.devamsizlikSpinner.setAdapter(adapter);




    }
    public void sinavSonuclari(){
        String sinavAdi;
        String sinavTipi;
        double sinavNotu;

        try {
            cursor=sqLiteDatabase.rawQuery("SELECT * FROM sinavsonuclari JOIN dersler ON sinavsonuclari.dersID=dersler.dersID" +
                    " WHERE sinavsonuclari.ogrenciID=?",new String[]{ogrID});
            while (cursor.moveToNext()){
                sinavAdi=cursor.getString(6);
                sinavTipi=cursor.getString(1);
                sinavNotu=cursor.getDouble(2);
                SinavSonuclari sinavSonuclari=new SinavSonuclari(sinavAdi,sinavTipi,sinavNotu);
                sinavSonuclariArrayList.add(sinavSonuclari);
            }
            sonucAdapter.notifyDataSetChanged();
            cursor.close();
        }catch (Exception e){
            e.fillInStackTrace();
        }



    }
    public void devamsizlik(){
        String dersAdi;
        String tarih;
       try {
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
       }catch (Exception e){
           e.fillInStackTrace();
       }

    }

    // öğretmen tarafından ilgili öğrencinin not bilgisi kaydedilir
    public void notKaydet(View view){
        int dersID=binding.dersSpinner.getSelectedItemPosition()+1;
        String sinavTipi=binding.sinavTipi.getText().toString();
        double sinavNotu=Double.valueOf(binding.sinavNotu.getText().toString());
        if (binding.dersSpinner.getSelectedItemPosition()>=0 && !TextUtils.isEmpty(sinavTipi)){
          try {
              String sinavSql="INSERT INTO sinavsonuclari(sinavTipi,sinavNotu,dersID,ogrenciID) VALUES(?,?,?,?)";
              sqLiteStatement=sqLiteDatabase.compileStatement(sinavSql);
              sqLiteStatement.bindString(1,sinavTipi);
              sqLiteStatement.bindDouble(2,sinavNotu);
              sqLiteStatement.bindLong(3,dersID);
              sqLiteStatement.bindString(4,ogrID);
              sqLiteStatement.execute();
          }catch (Exception e){
              e.fillInStackTrace();
          }
          Toast.makeText(this,"Not Eklendi",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"DERS SECİN",Toast.LENGTH_LONG).show();
        }

        notOrtalama();


    }

    // öğretmen tarafından ilgili öğrencinin devamsızlık bilgisi kaydedilir
    public void devamsizlikKaydet(View view){
        int dersID=binding.devamsizlikSpinner.getSelectedItemPosition()+1;
        String tarih=binding.devamsizlikTarih.getText().toString();
        if (binding.devamsizlikSpinner.getSelectedItemPosition()>=0 && !TextUtils.isEmpty(tarih)){

           try {
               String devamsizlikSql="INSERT INTO devamsizlikKayitlari(tarih,ogrenciID,dersID) VALUES (?,?,?)";
               sqLiteStatement=sqLiteDatabase.compileStatement(devamsizlikSql);
               sqLiteStatement.bindString(1,tarih);
               sqLiteStatement.bindString(2,ogrID);
               sqLiteStatement.bindLong(3,dersID);
               sqLiteStatement.execute();
           }catch (Exception e){
               e.fillInStackTrace();
           }


        }

    }
}