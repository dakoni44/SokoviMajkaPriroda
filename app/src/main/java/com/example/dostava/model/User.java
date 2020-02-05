package com.example.dostava.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class User {
    //public static SimpleDateFormat dtf= new SimpleDateFormat("dd.MM.yyyy.");

    private String ime;
    private String prezime;
    private String adresa;
    private String grad;
    private String datum;
    private String telefon;
    private String sokovi;
    private String cena;
    private String dobio;

    public User(String ime, String prezime, String adresa, String grad, String datum, String telefon, String sokovi, String cena, String dobio) {
        this.ime = ime;
        this.prezime = prezime;
        this.adresa = adresa;
        this.grad = grad;
        this.datum = datum;
        this.telefon = telefon;
        this.sokovi = sokovi;
        this.cena = cena;
        this.dobio = dobio;
    }


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getSokovi() {
        return sokovi;
    }

    public void setSokovi(String sokovi) {
        this.sokovi = sokovi;
    }

    public String getCena() {
        return cena;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public String getDobio() {
        return dobio;
    }

    public void setDobio(String dobio) {
        this.dobio = dobio;
    }
}
