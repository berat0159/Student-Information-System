package com.berat.ogrencibilgisistemm.ogrenci;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.berat.ogrencibilgisistemm.R;
import com.berat.ogrencibilgisistemm.adapter.DersProgramiAdapter;
import com.berat.ogrencibilgisistemm.databinding.ActivityOgrenciBinding;
import com.berat.ogrencibilgisistemm.modul.DersProgrami;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;


public class OgrenciActivity extends AppCompatActivity {

    private ActivityOgrenciBinding binding;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private ArrayList<DersProgrami> dersProgramiArrayList;
    private DersProgramiAdapter dersProgramiAdapter;
    private String intentID;
    private String ogrID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOgrenciBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sqLiteDatabase=this.openOrCreateDatabase("Sistem", MODE_PRIVATE,null);
        dersProgramiArrayList=new ArrayList<>();

        Intent intent=getIntent();
        intentID=intent.getStringExtra("intentOgrID");

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Öğrenci");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        binding.programRecyclerView2.setLayoutManager(new LinearLayoutManager(this));
        dersProgramiAdapter=new DersProgramiAdapter(dersProgramiArrayList);
        binding.programRecyclerView2.setAdapter(dersProgramiAdapter);

        ogrenciBilgileri();
        ogrenciProgrami();
        devamsizlikSayisi();
        donemOrtalamasi();

    }

    public void donemOrtalamasi(){
        double donOrtalama=0.0;
        cursor=sqLiteDatabase.rawQuery("SELECT AVG(sinavsonuclari.sinavNotu) FROM sinavsonuclari WHERE sinavsonuclari.ogrenciID=?",
                new String[]{ogrID});
        while (cursor.moveToNext()){
            donOrtalama=cursor.getDouble(0);
        }
        cursor.close();
        binding.donOrtalama.setText(String.valueOf(donOrtalama));
    }

    public void devamsizlikSayisi(){
        int devSayisi=0;
        cursor=sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM devamsizlikKayitlari WHERE devamsizlikKayitlari.ogrenciID=?",
                new String[]{ogrID});
        while (cursor.moveToNext()){
            devSayisi=cursor.getInt(0);
        }
        cursor.close();
        binding.devSayisi.setText(String.valueOf(devSayisi));

    }
    public void ogrenciBilgileri(){
        cursor=sqLiteDatabase.rawQuery("SELECT * FROM ogrenciler JOIN kullanicilar ON kullanicilar.kullaniciID=ogrenciler.kullaniciID" +
                " WHERE ogrenciler.kullaniciID=?",new String[]{intentID});
        while (cursor.moveToNext()){
            ogrID=cursor.getString(0);
            binding.adOgr3.setText(cursor.getString(1));
            binding.soyadOgr3.setText(cursor.getString(2));
            binding.telOgr3.setText(cursor.getString(3));
            binding.noOgr3.setText(cursor.getString(4));
            binding.epostaOgr3.setText(cursor.getString(5));
            binding.dogumOgr3.setText(cursor.getString(6));
            binding.sifreOgr3.setText(cursor.getString(10));
        }
        dersProgramiAdapter.notifyDataSetChanged();
        cursor.close();
    }

    public void ogrenciProgrami(){
        String ogrenciID;
        String dersID;
        String dersAdi;
        String dersGunu;
        String baslangic;
        String bitis;


        cursor=sqLiteDatabase.rawQuery("SELECT * FROM dersprogrami JOIN dersler ON dersler.dersID=dersprogrami.dersID" +
                " WHERE dersprogrami.ogrenciID=?",new String[]{ogrID});
        while (cursor.moveToNext()){
            dersAdi=cursor.getString(7);
            dersGunu=cursor.getString(1);
            baslangic=cursor.getString(2);
            bitis=cursor.getString(3);
            dersID=cursor.getString(4);
            ogrenciID=cursor.getString(5);
            DersProgrami dersProgrami=new DersProgrami(dersAdi,dersGunu,baslangic,bitis,dersID,ogrenciID);
            dersProgramiArrayList.add(dersProgrami);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.ogrenci_detay_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.ogrDetay_id){
            Intent intent=new Intent(OgrenciActivity.this,NotDevamsizlikDetayActivity.class);
            intent.putExtra("intentOgrID",ogrID);
            startActivity(intent);
        }
        return super.onContextItemSelected(item);
    }
}