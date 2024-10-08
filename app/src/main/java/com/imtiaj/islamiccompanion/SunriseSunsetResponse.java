package com.imtiaj.islamiccompanion;

public class SunriseSunsetResponse {

        private int code;
        private String status;
        private Data data;

        public int getCode() {
            return code;
        }

        public String getStatus() {
            return status;
        }

        public Data getData() {
            return data;
        }

        public static class Data {
            private Timings timings;

            public Timings getTimings() {
                return timings;
            }
        }

        public static class Timings {
            private String Fajr;
            private String Sunrise;
            private String Dhuhr;
            private String Asr;
            private String Sunset;
            private String Maghrib;
            private String Isha;
            private String Imsak;
            private String Midnight;
            private String Firstthird;
            private String Lastthird;

            public String getFajr() {
                return Fajr;
            }

            public String getSunrise() {
                return Sunrise;
            }

            public String getDhuhr() {
                return Dhuhr;
            }

            public String getAsr() {
                return Asr;
            }

            public String getSunset() {
                return Sunset;
            }

            public String getMaghrib() {
                return Maghrib;
            }

            public String getIsha() {
                return Isha;
            }

            public String getImsak() {
                return Imsak;
            }

            public String getMidnight() {
                return Midnight;
            }

            public String getFirstthird() {
                return Firstthird;
            }

            public String getLastthird() {
                return Lastthird;
            }
        }
    }