package com.berat.ogrencibilgisistemm.ogretmen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.berat.ogrencibilgisistemm.adapter.OgrenciAdapter;
import com.berat.ogrencibilgisistemm.adapter.OgrenciDetayAdapter;
import com.berat.ogrencibilgisistemm.adapter.OgretmenDersleriAdapter;
import com.berat.ogrencibilgisistemm.databinding.ActivityOgretmenBinding;
import com.berat.ogrencibilgisistemm.modul.Ogrenciler;
import com.berat.ogrencibilgisistemm.modul.OgretmenDersleri;

import java.util.ArrayList;

public class OgretmenActivity extends AppCompatActivity {
    private ActivityOgretmenBinding binding;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private Intent intent;
    OgrenciDetayAdapter ogrenciDetayAdapter;
    OgretmenDersleriAdapter ogretmenDersleriAdapter;
    private ArrayList<Ogrenciler> ogrencilerArrayList;
    private ArrayList<OgretmenDersleri> ogretmenDersleriArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOgretmenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sqLiteDatabase=this.openOrCreateDatabase("Sistem",MODE_PRIVATE,null);

        ActionBar actionbar=getSupportActionBar();
        actionbar.setTitle("öğretmen");
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        ogrencilerArrayList=new ArrayList<>();
        ogretmenDersleriArrayList=new ArrayList<>();

        // recyclerview bağlama işlemleri
        binding.ogrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ogrenciDetayAdapter=new OgrenciDetayAdapter(ogrencilerArrayList);
        binding.ogrRecyclerView.setAdapter(ogrenciDetayAdapter);

        binding.derslerimRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ogretmenDersleriAdapter=new OgretmenDersleriAdapter(ogretmenDersleriArrayList);
        binding.derslerimRecyclerView.setAdapter(ogretmenDersleriAdapter);
        ogretmenBilgileri();
        ogrenciBilgileri();
        ogretmenDersleri();

    }
    // öğretmen bilgilerini günceller
    public void guncelle(View view){
        String ad=binding.ogAd.getText().toString();
        String soyad=binding.ogSoyad.getText().toString();
        String telefon=binding.ogTel.getText().toString();
        String eposta=binding.ogEposta.getText().toString();
        String sifre=binding.ogSifre.getText().toString();
        String dogum=binding.ogDogum.getText().toString();

        String[] ogretmenData={ad,soyad,telefon,eposta,dogum,eposta};
        String[] kullaniciData={sifre,eposta};
        sqLiteDatabase.execSQL("UPDATE ogretmenler SET ogretmenAdi=?,ogretmenSoyadi=?,ogretmenTelefon=?," +
                "ogretmenEposta=?,ogretmenDogumTarihi=? WHERE ogretmenEposta=?",ogretmenData);
        sqLiteDatabase.execSQL("UPDATE kullanicilar SET kullaniciSifre=? WHERE kullaniciAdi=?",kullaniciData);
    }

    // öğretmene ait dersleri çeker
    public void ogretmenDersleri(){
        String ogretmenid="";
        String dersAdi;
        String dersGunu;
        String baslangıcSaati;
        String bitisSaati;
        cursor=sqLiteDatabase.rawQuery("SELECT ogretmenID FROM dersler",null);
        while (cursor.moveToNext()){
            ogretmenid=cursor.getString(0);
        }
        cursor.close();
        cursor=sqLiteDatabase.rawQuery("SELECT * FROM dersler JOIN dersprogrami ON dersler.dersID=dersprogrami.dersID " +
                "WHERE dersler.ogretmenID=?",new String[]{ogretmenid});
        while (cursor.moveToNext()){
            dersAdi=cursor.getString(1);
            dersGunu=cursor.getString(4);
            baslangıcSaati=cursor.getString(5);
            bitisSaati=cursor.getString(6);
            OgretmenDersleri ogretmenDersleri=new OgretmenDersleri(dersAdi,dersGunu,baslangıcSaati,bitisSaati);
            ogretmenDersleriArrayList.add(ogretmenDersleri);
        }
        ogretmenDersleriAdapter.notifyDataSetChanged();
        cursor.close();

    }
    // öğrenci bilgilerini çeker
    public void ogrenciBilgileri() {
        String id;
        String ad;
        String soyad;
        String telefon;
        String okulNo;
        String eposta;
        String dogum;
        cursor=sqLiteDatabase.rawQuery("SELECT * FROM ogrenciler ",null);
        while (cursor.moveToNext()){
            id=cursor.getString(0);
            ad=cursor.getString(1);
            soyad=cursor.getString(2);
            telefon=cursor.getString(3);
            okulNo=cursor.getString(4);
            eposta=cursor.getString(5);
            dogum=cursor.getString(6);
            // arraylist
            Ogrenciler ogrenciler=new Ogrenciler(id,ad,soyad,telefon,eposta,dogum);
            ogrencilerArrayList.add(ogrenciler);
        }
        ogrenciDetayAdapter.notifyDataSetChanged();
        cursor.close();

    }
    // öğretmen bilgilerini çeker
    public void ogretmenBilgileri(){


        intent=getIntent();
        // mainactivity den gelen intent değerini alır
        String id=intent.getStringExtra("intentOrtID");
        Toast.makeText(this,"ogretmenİd : "+id,Toast.LENGTH_LONG).show();
        try {
            cursor=sqLiteDatabase.rawQuery("SELECT *" +
                    " FROM ogretmenler JOIN kullanicilar ON ogretmenler.kullaniciID=kullanicilar.kullaniciID " +
                    "WHERE kullanicilar.kullaniciID=?",new String[]{id});
            while (cursor.moveToNext()){
                binding.ogAd.setText(cursor.getString(1));
                binding.ogSoyad.setText(cursor.getString(2));
                binding.ogTel.setText(cursor.getString(3));
                binding.ogEposta.setText(cursor.getString(4));
                binding.ogDogum.setText(cursor.getString(5));
                binding.ogSifre.setText(cursor.getString(9));
            }
            cursor.close();
        }catch (Exception e){
            e.fillInStackTrace();
        }




    }
}