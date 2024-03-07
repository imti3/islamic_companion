package com.imtiaj.islamiccompanion;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SunriseSunsetService {
    @GET("timings/{date}")
    Call<SunriseSunsetResponse> getSunriseSunset(
            @Path("date") String date,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude
            //@Query("method") int calculationMethod
    );
}

