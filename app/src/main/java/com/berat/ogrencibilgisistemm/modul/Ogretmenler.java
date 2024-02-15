package com.berat.ogrencibilgisistemm.modul;

public class Ogretmenler {
    public String ogretmenId;
    public String ogretmenAd;
    public String ogretmenSoyad;
    public String ogretmenTel;
    public String ogretmenEposta;
    public String dogumTarihi;
    public Ogretmenler(String ogretmenId,String ogretmenAd,String ogretmenSoyad,String ogretmenTel,String ogretmenEposta,String dogumTarihi){
        this.ogretmenId=ogretmenId;
        this.ogretmenAd=ogretmenAd;
        this.ogretmenSoyad=ogretmenSoyad;
        this.ogretmenTel=ogretmenTel;
        this.ogretmenEposta=ogretmenEposta;
        this.dogumTarihi=dogumTarihi;
    }
}
