package com.imtiaj.islamiccompanion;

public class timing {
    private String fajr;
    private String dhuhr;
    private String asr;
    private String magrib;
    private String isha;
    private String imsak;
    private String sunset;
    private String sunrise;
    private String date;

    private  String weekday;

    private String hijri;

    private  String navor;

    private  String tahajjut;

    private  String dohrRange;

    private String sunsetRange;
    private boolean isCurrentDate;


    public String getNavor() {
        return navor;
    }

    public String getDohrRange() {
        return dohrRange;
    }

    public String getSunsetRange() {
        return sunsetRange;
    }

    public timing(String fajr, String dhuhr, String asr, String magrib, String isha, String imsak, String sunset, String sunrise, String date, String weekday, String hijri, String tahajjut, String navor, String dohrRange, String sunsetRange, boolean isCurrentDate) {
        this.fajr = fajr;
        this.dhuhr = dhuhr;
        this.asr = asr;
        this.magrib = magrib;
        this.isha = isha;
        this.imsak = imsak;
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.date = date;
        this.weekday=weekday;
        this.tahajjut=tahajjut;
        this.navor=navor;
        this.dohrRange=dohrRange;
        this.sunsetRange=sunsetRange;
        this.hijri=hijri;

        this.isCurrentDate = isCurrentDate;
    }
    public String getTahajjut() {
        return tahajjut;
    }
    public String getHijri() {
        return hijri;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getDhuhr() {
        return dhuhr;
    }

    public void setDhuhr(String dhuhr) {
        this.dhuhr = dhuhr;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }

    public String getImsak() {
        return imsak;
    }

    public void setImsak(String imsak) {
        this.imsak = imsak;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public boolean isCurrentDate() {
        return isCurrentDate;
    }
}


