package com.berat.ogrencibilgisistemm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.berat.ogrencibilgisistemm.databinding.ActivityMainBinding;
import com.berat.ogrencibilgisistemm.ogrenci.OgrenciActivity;
import com.berat.ogrencibilgisistemm.ogretmen.OgretmenActivity;
import com.berat.ogrencibilgisistemm.yonetim.YonetimActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Intent intent;
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // veri tabanını açar
        sqLiteDatabase=this.openOrCreateDatabase("Sistem", MODE_PRIVATE,null);

               /*sqLiteDatabase.execSQL("DELETE FROM kullanicilar");
                sqLiteDatabase.execSQL("DELETE FROM ogrenciler");
                sqLiteDatabase.execSQL("DELETE FROM dersler");
                 sqLiteDatabase.execSQL("DELETE FROM dersprogrami");
                sqLiteDatabase.execSQL("DELETE FROM ogretmenler");
                sqLiteDatabase.execSQL("DELETE FROM sinavsonuclari");
                sqLiteDatabase.execSQL("DELETE FROM devamsizlikKayitlari");*/



        // tabloları oluşturur
        try {
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS kullanicilar(kullaniciID INTEGER PRIMARY KEY," +
                    "kullaniciAdi VARCHAR,kullaniciSifre VARCHAR,kullaniciTipi VARCHAR)");

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ogrenciler(ogrenciID INTEGER PRIMARY KEY,ogrenciAdi VARCHAR," +
                    "ogrenciSoyadi VARCHAR,ogrenciTelefon VARCHAR,ogrenciOkulNo VARCHAR,ogrenciEposta VARCHAR,ogrenciDogumTarihi VARCHAR" +
                    ",kullaniciID INTEGER,FOREIGN KEY (kullaniciID) REFERENCES kullanicilar(kullaniciID))");

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS ogretmenler(ogretmenID INTEGER PRIMARY KEY,ogretmenAdi VARCHAR," +
                    "ogretmenSoyadi VARCHAR,ogretmenTelefon VARCHAR,ogretmenEposta VARCHAR,ogretmenDogumTarihi VARCHAR" +
                    ",kullaniciID INTEGER, FOREIGN KEY (kullaniciID) REFERENCES kullanicilar(kullaniciID))");

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS dersler(dersID INTEGER PRIMARY KEY,dersAdi VARCHAR," +
                    "ogretmenID INTEGER,FOREIGN KEY (ogretmenID) REFERENCES ogretmenler(ogretmenID))");

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS dersprogrami(programID INTEGER PRIMARY KEY,dersGunu VARCHAR," +
                    "baslangicSaati VARCHAR,bitisSaati VARCHAR,dersID INTEGER,ogrenciID INTEGER" +
                    ",FOREIGN KEY (ogrenciID) REFERENCES ogrenciler(ogrenciID),FOREIGN KEY (dersID) REFERENCES dersler(dersID))");


            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS sinavsonuclari (sonucID INTEGER PRIMARY KEY ,sinavTipi VARCHAR ," +
                    "sinavNotu DOUBLE,dersID INTEGER,ogrenciID INTEGER,FOREIGN KEY (dersID) REFERENCES dersler(dersID)," +
                    "FOREIGN KEY (ogrenciID) REFERENCES ogrenciler(ogrenciID))");

            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS devamsizlikKayitlari(devamsizlikID INTEGER PRIMARY KEY , tarih VARCHAR" +
                    ",ogrenciID INTEGER,dersID INTEGER,FOREIGN KEY (ogrenciID) REFERENCES ogrenciler(ogrenciID)," +
                    "FOREIGN KEY (dersID) REFERENCES dersler(dersID))");





        }catch (Exception e){
            e.fillInStackTrace();
        }

    }


    public void giris(View view){
        String ad=binding.kullanCAdi.getText().toString();
        String sifre=binding.kullanCSifre.getText().toString();
        String kullaniciAd="";
        String kullaniciSifre="";
        String kullaniciTipi="";
        try {
            // kullanicilar tablosundan kullaniciAdi,kullaniciTipi ve kullaniciSifre çeker
            cursor = sqLiteDatabase.rawQuery("SELECT * FROM kullanicilar WHERE kullaniciAdi=?"
                    , new String[]{ad});
            while (cursor.moveToNext()) {
                kullaniciAd = cursor.getString(1);
                kullaniciSifre = cursor.getString(2);
                kullaniciTipi = cursor.getString(3);
            }
            cursor.close();
            // girilen kullanici adı ve şifre yi kontrol eder
            if (kullaniciAd.equals(ad) && kullaniciSifre.equals(kullaniciSifre) && !kullaniciAd.equals("")) {
                // öğretmen girişi
                if (kullaniciTipi.equals("Öğretmen")) {

                    String ortId="";
                    cursor=sqLiteDatabase.rawQuery("SELECT kullaniciID FROM kullanicilar WHERE kullaniciAdi=? ",
                            new String[]{ad});
                    while (cursor.moveToNext()){
                        ortId=cursor.getString(0);
                    }
                    intent=new Intent(this,OgretmenActivity.class);
                    intent.putExtra("intentOrtID",ortId);
                    startActivity(intent);

                    Toast.makeText(this, "Öğretmen Girisi Yapıldı", Toast.LENGTH_LONG).show();

                }
                // öğrenci girişi
                else if (kullaniciTipi.equals("Öğrenci")) {
                    String ogrId="";
                    cursor=sqLiteDatabase.rawQuery("SELECT kullaniciID FROM kullanicilar WHERE kullaniciAdi=?",new String[]{ad});
                    while (cursor.moveToNext()){
                        ogrId=cursor.getString(0);
                    }
                    cursor.close();
                    intent=new Intent(this, OgrenciActivity.class);
                    intent.putExtra("intentOgrID",ogrId);
                    startActivity(intent);
                    Toast.makeText(this, "Öğrenci Girisi Yapıldı", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(this, "Kullanici Tipi Hatalı", Toast.LENGTH_LONG).show();
                }

            }
            // admin girişi
            else if (ad.equals("admin") && sifre.equals("admin")) {

                intent=new Intent(this,YonetimActivity.class);
                startActivity(intent);
                Toast.makeText(this,"Yönetim Girisi Yapıldı",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(this,"Kullanici/Sifre Hatalı",Toast.LENGTH_LONG).show();
            }
            cursor.close();
        }catch (Exception e){
            e.fillInStackTrace();
        }

    }
}