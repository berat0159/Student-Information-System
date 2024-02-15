package com.berat.ogrencibilgisistemm.yonetim;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.berat.ogrencibilgisistemm.R;
import com.berat.ogrencibilgisistemm.databinding.ActivityYtDersEkleBinding;

import java.util.ArrayList;

public class YtDersEkleActivity extends AppCompatActivity {
    private ActivityYtDersEkleBinding binding;
    private String dersAdi,dersGunu,baslangicSaati,bitisSaati;
    private SQLiteDatabase sqLiteDatabase;
    int dersidIx,dersid;
    private Cursor cursor;

    private SQLiteStatement statement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityYtDersEkleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // veritabanını açar
        sqLiteDatabase=this.openOrCreateDatabase("Sistem",MODE_PRIVATE,null);

        // günler spinner'a item ekleme
        String[] gunler={"Pazartesi","Salı","Çarşamba","Perşembe","Cuma"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,gunler);
        binding.dersGunuSpinner.setAdapter(arrayAdapter);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Ders Ekle");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        spinnerOrt();
        spinnerOgr();



    }
    public void dersEkle(View view){
        dersAdi=binding.dersAdi.getText().toString();
        int dersSayisi=0;
        // ders adı ders günü ve öğretmen spinner boş değilse şartı
        if (!TextUtils.isEmpty(binding.dersAdi.getText()) && binding.dersGunuSpinner.getSelectedItemPosition()>=0 && binding.ogretmenSpinner.getSelectedItemPosition()>=0){
            try {
                // dersler tablosundan dersAdi'na göre sayısını çekip varlığını kontrol eder
                cursor=sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM dersler WHERE dersAdi=?",new String[]{dersAdi});
                while (cursor.moveToFirst()){
                    dersSayisi=cursor.getInt(0);
                    if (dersSayisi>0){
                        Toast.makeText(YtDersEkleActivity.this, "Bu İsimle Zaten Bir Ders Var", Toast.LENGTH_SHORT).show();
                    }else {
                        dersler();
                        dersProgrami();
                        Toast.makeText(this, "Eklendi", Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }catch (Exception e){
                e.fillInStackTrace();
            }
        }else {
            Toast.makeText(this, "Tüm Paremetreleri Seçiniz", Toast.LENGTH_SHORT).show();
        }
    }

    // ogrenciler tablosundan tüm ogrenci adlarını spinner'a ekler
    public void spinnerOgr(){
        cursor=sqLiteDatabase.rawQuery("SELECT ogrenciAdi FROM ogrenciler",null);
        String[] ogrenciAdlari=new String[cursor.getCount()];
        int i=0;
        while (cursor.moveToNext()){
            ogrenciAdlari[i] = cursor.getString(0);
            i++;
        }
        cursor.close();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(YtDersEkleActivity.this, android.R.layout.simple_spinner_item,ogrenciAdlari);
        binding.ogrenciSpinner.setAdapter(adapter);
    }

    // öğretmenler tablosundan öğretmen adlarını çekip spinner'a ekler
    public void spinnerOrt(){
       try {
           cursor=sqLiteDatabase.rawQuery("SELECT ogretmenAdi FROM  ogretmenler",null);
           String[] ogretmenAdlari=new String[cursor.getCount()];
           int i=0;
           while (cursor.moveToNext()){
               ogretmenAdlari[i] =cursor.getString(0);
               i++;
           }
            cursor.close();
           // spinner bağlama işlemi
           ArrayAdapter<String> adapter=new ArrayAdapter<String>(YtDersEkleActivity.this, android.R.layout.simple_spinner_item,ogretmenAdlari);
           binding.ogretmenSpinner.setAdapter(adapter);
       }catch (Exception e){
           e.fillInStackTrace();
       }


    }

    // dersler tablosuna insert işlemi
    public void dersler(){
        dersAdi=binding.dersAdi.getText().toString();
        // öğretmenId yabancı anahtar için spinnerda seçilen item sıralama değerini alarak dersler ile ogretmenler tablosunu ilişkilendirme işlemi
        int ogretmenID=binding.ogretmenSpinner.getSelectedItemPosition()+1;

        try {
            String derslerSql="INSERT INTO dersler(dersAdi,ogretmenID) VALUES (?,?)";
            statement=sqLiteDatabase.compileStatement(derslerSql);
            statement.bindString(1,dersAdi);
            statement.bindLong(2,ogretmenID);
            statement.execute();
        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    // dersprogrami tablosuna insert işlemi
    public void dersProgrami(){
        dersGunu=binding.dersGunuSpinner.getSelectedItem().toString();
        baslangicSaati=binding.dersBaslangC.getText().toString();
        bitisSaati=binding.dersBitis.getText().toString();

        String ogrenciId="";

        try {
            // dersprogrami tablosunda dersID yabancı anahtar oluşturarak dersler tablosu ile ilişkilendirme işlemi
            cursor=sqLiteDatabase.rawQuery("SELECT dersID FROM dersler WHERE dersAdi=?",new String[]{dersAdi});
            while (cursor.moveToNext()){
                dersidIx=cursor.getColumnIndex("dersID");
                dersid=cursor.getInt(dersidIx);
            }
            cursor.close();

            int ogrenciID=binding.ogrenciSpinner.getSelectedItemPosition()+1;
            String programSql="INSERT INTO dersprogrami(dersGunu,baslangicSaati,bitisSaati,dersID,ogrenciID) VALUES (?,?,?,?,?)";
            statement=sqLiteDatabase.compileStatement(programSql);
            statement.bindString(1,dersGunu);
            statement.bindString(2,baslangicSaati);;
            statement.bindString(3,bitisSaati);
            statement.bindLong(4,dersid);
            statement.bindLong(5,ogrenciID);
            statement.execute();

        }catch (Exception e){
            e.fillInStackTrace();
        }

    }
}