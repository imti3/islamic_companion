package com.imtiaj.islamiccompanion.Model;


public class DhikrModel {
    String DhikrArabicName;
    String DhikrEnglishMeaning;
    String DhikrEnglishName;

    String DhikrBanglaMeaning;
    int id;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getDhikrEnglishName() {
        return this.DhikrEnglishName;
    }

    public void setDhikrEnglishName(String str) {
        this.DhikrEnglishName = str;
    }

    public String getDhikrArabicName() {
        return this.DhikrArabicName;
    }

    public void setDhikrArabicName(String str) {
        this.DhikrArabicName = str;
    }

    public String getDhikrEnglishMeaning() {
        return this.DhikrEnglishMeaning;
    }

    public void setDhikrEnglishMeaning(String str) {
        this.DhikrEnglishMeaning = str;
    }


    public String getDhikrBanglaMeaning() {
        return this.DhikrBanglaMeaning;
    }

    public void setDhikrBanglaMeaning(String str) {
        this.DhikrBanglaMeaning = str;
    }
}

