package com.imtiaj.islamiccompanion;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SunriseSunsetApiClient {
    private static final String BASE_URL = "https://api.aladhan.com/v1/";

    private static final SunriseSunsetService sunriseSunsetService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SunriseSunsetService.class);

    public static SunriseSunsetService getSunriseSunsetService() {
        return sunriseSunsetService;
    }
}
