package com.berat.ogrencibilgisistemm.yonetim;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.berat.ogrencibilgisistemm.adapter.OgrenciAdapter;
import com.berat.ogrencibilgisistemm.adapter.OgretmenAdapter;
import com.berat.ogrencibilgisistemm.databinding.ActivityOgrenciViewBinding;
import com.berat.ogrencibilgisistemm.modul.Ogrenciler;
import com.berat.ogrencibilgisistemm.modul.Ogretmenler;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;


public class OgrenciViewActivity extends AppCompatActivity {
private ActivityOgrenciViewBinding binding;
private Cursor cursor;
private SQLiteDatabase sqLiteDatabase;
private OgrenciAdapter ogrenciAdapter;
private OgretmenAdapter ogretmenAdapter;
private ArrayList<Ogrenciler> ogrencilerArrayList;
private ArrayList<Ogretmenler> ogretmenlerArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     binding = ActivityOgrenciViewBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());
     sqLiteDatabase=this.openOrCreateDatabase("Sistem",MODE_PRIVATE,null);
        ogrencilerArrayList=new ArrayList<>();
        ogretmenlerArrayList=new ArrayList<>();
        ogrenciData();
        ogretmenData();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Öğrenci");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // listeli verilerin recyclerview'de hangi yöne dogru sıralanacağını belirtir
        binding.ogrRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        // ogrenciAdapter'ı recyclerview bağlama işlemi
        ogrenciAdapter=new OgrenciAdapter(ogrencilerArrayList);
        binding.ogrRecyclerView.setAdapter(ogrenciAdapter);
        // listeli verilerin recyclerview'de hangi yöne dogru sıralanacağını belirtir
        binding.ortRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        // ogretmenAdapter'ı recyclerview bağlama işlemi
        ogretmenAdapter=new OgretmenAdapter(ogretmenlerArrayList);
        binding.ortRecyclerView.setAdapter(ogretmenAdapter);

    }
    //ogretmenler tablosundaki verileri çeker
    public void ogretmenData(){

        try {

            cursor=sqLiteDatabase.rawQuery("SELECT * FROM ogretmenler",null);
            int idIx=cursor.getColumnIndex("ogretmenID");
            int adIx=cursor.getColumnIndex("ogretmenAdi");
            int soyadIx=cursor.getColumnIndex("ogretmenSoyadi");
            int telIx=cursor.getColumnIndex("ogretmenTelefon");
            int postaIx=cursor.getColumnIndex("ogretmenEposta");
            int dogumIx=cursor.getColumnIndex("ogretmenDogumTarihi");
            while (cursor.moveToNext()){
                String ortId=String.valueOf(cursor.getInt(idIx));
                String ortAd=cursor.getString(adIx);
                String ortSoyad=cursor.getString(soyadIx);
                String ortTel=cursor.getString(telIx);
                String ortPosta=cursor.getString(postaIx);
                String ortDogum=cursor.getString(dogumIx);
                // arraylist
                Ogretmenler ogretmenler=new Ogretmenler(ortId,ortAd,ortSoyad,ortTel,ortPosta,ortDogum);
                ogretmenlerArrayList.add(ogretmenler);

            }
            ogretmenAdapter.notifyDataSetChanged();
            cursor.close();


        }catch (Exception e){
            e.fillInStackTrace();
        }
    }
    // öğrenciler tablosundan verileri çeker
    public void ogrenciData(){

        try {

            cursor=sqLiteDatabase.rawQuery("SELECT * FROM ogrenciler",null);
            int idIx=cursor.getColumnIndex("ogrenciID");
            int adIx=cursor.getColumnIndex("ogrenciAdi");
            int soyadIx=cursor.getColumnIndex("ogrenciSoyadi");
            int telIx=cursor.getColumnIndex("ogrenciTelefon");
            int postaIx=cursor.getColumnIndex("ogrenciEposta");
            int dogumIx=cursor.getColumnIndex("ogrenciDogumTarihi");

            while (cursor.moveToNext()){
                String ogrId=String.valueOf(cursor.getInt(idIx));
                String ogrAd=cursor.getString(adIx);
                String ogrSoyad=cursor.getString(soyadIx);
                String ogrTel=cursor.getString(telIx);
                String ogrPosta=cursor.getString(postaIx);
                String ogrDogum=cursor.getString(dogumIx);
                // arraylist
                Ogrenciler ogrenciler=new Ogrenciler(ogrId,ogrAd,ogrSoyad,ogrTel,ogrPosta,ogrDogum);
                ogrencilerArrayList.add(ogrenciler);
            }

            ogrenciAdapter.notifyDataSetChanged();
            cursor.close();



        }catch (Exception e){
            e.fillInStackTrace();
        }


    }


}