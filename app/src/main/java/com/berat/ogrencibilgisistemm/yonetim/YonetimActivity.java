package com.berat.ogrencibilgisistemm.yonetim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.berat.ogrencibilgisistemm.R;
import com.berat.ogrencibilgisistemm.databinding.ActivityYonetimBinding;

public class YonetimActivity extends AppCompatActivity {
    private ActivityYonetimBinding binding;
    private SQLiteStatement statement;
    public String ad,soyad,telefon,eposta,kullaniciTipi,sifre,dogumtarihi;
    public String kullaniciID;

    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityYonetimBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // veritabanını açar
        sqLiteDatabase=this.openOrCreateDatabase("Sistem",MODE_PRIVATE,null);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Yönetim");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        // ogretmen spinner'a item ekler
        String[] items={"Öğrenci","Öğretmen"};
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,items);
        binding.kulaniciTipi.setAdapter(adapter);



    }

    // kaydet butonu
    public void kullaniciKaydet(View view){
        // kullanicilar tablosundan kullanıcı adı sayısını çekip varlığını kontrol eder
        String kullaniciAdi=binding.ePosta.getText().toString();
        cursor=sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM kullanicilar WHERE kullaniciAdi = ?",new String[]{kullaniciAdi});
        if(cursor.moveToFirst()){
            // kullanıcı sayısı
            int kullaniciSayisi=cursor.getInt(0);
            if (kullaniciSayisi>0){

                Toast.makeText(this,"Kullanıcı Adı Alınmış",Toast.LENGTH_LONG).show();

            }else {

                kullanicilarTb();
                // spinner da seçilen item'a  göre verilerin hangi tabloya aktarılması işlemi
                switch (binding.kulaniciTipi.getSelectedItem().toString()){
                    case "Öğrenci" :
                        ogrencilerTb();
                        break;
                    case "Öğretmen" :
                        ogretmenlerTb();
                        break;
                }
                Toast.makeText(this,"Eklendi",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void kullanicilarTb(){
        ad=binding.ad.getText().toString();
        soyad=binding.soyad.getText().toString();
        telefon=binding.telefon.getText().toString();
        eposta=binding.ePosta.getText().toString();
        sifre=binding.sifre.getText().toString();
        dogumtarihi=binding.dogumTarihi.getText().toString();
        kullaniciTipi=binding.kulaniciTipi.getSelectedItem().toString();

        // kullanici bilgilerini insert etme işlemi
        try {
            String klncSql=("INSERT INTO kullanicilar(kullaniciAdi,kullaniciSifre,kullaniciTipi) VALUES(?,?,?)");
            statement=sqLiteDatabase.compileStatement(klncSql);
            statement.bindString(1,eposta);
            statement.bindString(2,sifre);
            statement.bindString(3,kullaniciTipi);
            statement.execute();

        }catch (Exception e){
            e.fillInStackTrace();
        }



    }

    public void ogrencilerTb(){
        ad=binding.ad.getText().toString();
        soyad=binding.soyad.getText().toString();
        telefon=binding.telefon.getText().toString();
        eposta=binding.ePosta.getText().toString();
        sifre=binding.sifre.getText().toString();
        dogumtarihi=binding.dogumTarihi.getText().toString();
        kullaniciTipi=binding.kulaniciTipi.getSelectedItem().toString();
        try {

            // Öğrenci Tablosunda OkulNo KullaniciID olarak kullanıldığı için kullanıcı tablosundan kullanıcı id çeker
            cursor=sqLiteDatabase.rawQuery("SELECT kullaniciID FROM kullanicilar WHERE kullaniciAdi=?",new String[]{eposta});
            while (cursor.moveToNext()){
                kullaniciID=cursor.getString(0);
            }
            cursor.close();
            // Ogrenci Tablosuna Ekleme
            String ogrSql="INSERT INTO ogrenciler(ogrenciAdi,ogrenciSoyadi,ogrenciTelefon,ogrenciOkulNo,ogrenciEposta,ogrenciDogumTarihi,kullaniciID) VALUES (?,?,?,?,?,?,?)";

            statement=sqLiteDatabase.compileStatement(ogrSql);
            statement.bindString(1,ad);
            statement.bindString(2,soyad);
            statement.bindString(3,telefon);
            statement.bindString(4,String.valueOf(kullaniciID));
            statement.bindString(5,eposta);
            statement.bindString(6,dogumtarihi);
            statement.bindString(7,String.valueOf(kullaniciID));
            statement.execute();

        }catch (Exception e){
            e.fillInStackTrace();
        }
    }

    public void ogretmenlerTb(){
        ad=binding.ad.getText().toString();
        soyad=binding.soyad.getText().toString();
        telefon=binding.telefon.getText().toString();
        eposta=binding.ePosta.getText().toString();
        sifre=binding.sifre.getText().toString();
        dogumtarihi=binding.dogumTarihi.getText().toString();
        kullaniciTipi=binding.kulaniciTipi.getSelectedItem().toString();

        try {
            // ogrenci tablosunda yabancı anahtar için kullanicilar tablosundan kullaniciID çeker
            String id="";
            cursor=sqLiteDatabase.rawQuery("SELECT kullaniciID FROM kullanicilar WHERE kullaniciAdi=?",new String[]{eposta});
            while (cursor.moveToNext()){
                id=cursor.getString(0);
            }

            // Ogretmen Tablosuna Ekleme
            String ogrtSql="INSERT INTO ogretmenler(ogretmenAdi,ogretmenSoyadi,ogretmenTelefon,ogretmenEposta,ogretmenDogumTarihi,kullaniciID)" +
                    "VALUES (?,?,?,?,?,?)";
            statement=sqLiteDatabase.compileStatement(ogrtSql);
            statement.bindString(1,ad);
            statement.bindString(2,soyad);
            statement.bindString(3,telefon);
            statement.bindString(4,eposta);
            statement.bindString(5,dogumtarihi);
            statement.bindString(6,id);

            statement.execute();


        }catch (Exception e){
            e.fillInStackTrace();
        }
    }


    // menu layout activitiye bağlar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.yonetici_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    // menüde tıklanan item'a göre aksiyon oluşturur
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.ytDersEkle_id){

            Intent intent=new Intent(YonetimActivity.this,YtDersEkleActivity.class);
            startActivity(intent);

        }
        if (item.getItemId()==R.id.ytOgrOrt){
            Intent intent=new Intent(YonetimActivity.this,OgrenciViewActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}