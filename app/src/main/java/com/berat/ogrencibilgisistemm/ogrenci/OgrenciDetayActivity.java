package com.berat.ogrencibilgisistemm.ogrenci;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.berat.ogrencibilgisistemm.R;
import com.berat.ogrencibilgisistemm.adapter.DersProgramiAdapter;
import com.berat.ogrencibilgisistemm.databinding.ActivityOgrenciDetayBinding;
import com.berat.ogrencibilgisistemm.modul.DersProgrami;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;


public class OgrenciDetayActivity extends AppCompatActivity {
    private SQLiteDatabase sqLiteDatabase;
    private ArrayList<DersProgrami> dersProgramiArrayList;
    private DersProgramiAdapter dersProgramiAdapter;
    private Cursor cursor;
    private ActivityOgrenciDetayBinding binding;
    private String intentID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOgrenciDetayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sqLiteDatabase=this.openOrCreateDatabase("Sistem",MODE_PRIVATE,null);

        Intent intent=getIntent();
        intentID=intent.getStringExtra("detayID");

        dersProgramiArrayList=new ArrayList<>();
        binding.programRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dersProgramiAdapter=new DersProgramiAdapter(dersProgramiArrayList);
        binding.programRecyclerView.setAdapter(dersProgramiAdapter);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Detay");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Toast.makeText(this,"Intent Degeri : "+intentID,Toast.LENGTH_LONG).show();
        ogrenciBilgileri();
        dersProgramiBilgileri();
    }
    public void guncelleOgr(View view){
        String ad=binding.adOgr.getText().toString();
        String soyad=binding.soyadOgr.getText().toString();
        String sifre=binding.sifreOgr.getText().toString();
        String no=binding.noOgr.getText().toString();
        String eposta=binding.epostaOgr.getText().toString();
        String tel=binding.telOgr.getText().toString();
        String dogum=binding.dogumOgr.getText().toString();

        String[] ogrenci={ad,soyad,tel,no,eposta,dogum};
        String[] kullanici={eposta,sifre};
        sqLiteDatabase.execSQL("UPDATE ogrenciler SET ogrenciAdi=?,ogrenciSoyadi=?,ogrenciTelefon=?,ogrenciOkulNo=?,ogrenciEposta=?,ogrenciDogum=?"
        ,ogrenci);
        sqLiteDatabase.execSQL("UPDATE kullanicilar SET kullaniciAdi=?,kullaniciSifre=?",kullanici);
    }
    public void ogrenciBilgileri(){

        cursor=sqLiteDatabase.rawQuery("SELECT * FROM ogrenciler JOIN kullanicilar ON ogrenciler.kullaniciID=kullanicilar.kullaniciID" +
                " WHERE ogrenciler.ogrenciID=?",new String[]{intentID});
        while (cursor.moveToNext()){
            binding.adOgr.setText(cursor.getString(1));
            binding.soyadOgr.setText(cursor.getString(2));
            binding.telOgr.setText(cursor.getString(3));
            binding.noOgr.setText(cursor.getString(7));
            binding.epostaOgr.setText(cursor.getString(5));
            binding.dogumOgr.setText(cursor.getString(6));
            binding.sifreOgr.setText(cursor.getString(9));
        }
        cursor.close();

    }

    public void dersProgramiBilgileri(){
        String dersID;
        String dersAd;
        String dersGun;
        String dersBas;
        String dersBit;

        cursor=sqLiteDatabase.rawQuery("SELECT * FROM dersler JOIN dersprogrami ON dersprogrami.dersID=dersler.dersID" +
                " WHERE dersprogrami.ogrenciID=?",new String[]{intentID});
        while (cursor.moveToNext()){
            dersID=cursor.getString(0);
            dersAd=cursor.getString(1);
            dersGun=cursor.getString(4);
            dersBas=cursor.getString(5);
            dersBit=cursor.getString(6);
            DersProgrami dersProgrami=new DersProgrami(dersAd,dersGun,dersBas,dersBit,dersID,intentID);
            dersProgramiArrayList.add(dersProgrami);
        }
        dersProgramiAdapter.notifyDataSetChanged();
        cursor.close();

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.ogrEkle_id){
            Intent intent=new Intent(OgrenciDetayActivity.this,DevamsizlikNotEkleAcitvity.class);
            intent.putExtra("ogrId",intentID);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.ogrenci_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


}