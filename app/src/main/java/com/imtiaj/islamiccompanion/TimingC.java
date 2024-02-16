package com.imtiaj.islamiccompanion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.imtiaj.islamiccompanion.Appcompany.Privacy_Policy_activity;
import com.imtiaj.islamiccompanion.PreferenceUtil.PrayerSharedPreference;
import com.imtiaj.islamiccompanion.databinding.ActivityTimingCBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TimingC extends AppCompatActivity {

    private timingadapter Timingadapter;

    String month,year;
    String fajr;

    String date1;

    String dohr;

    String asr;

    String magrib;

    String isha;

    String imsak;

    String sunset;

    String sunrise;

    String date;

    String weekday;

    String hijri;

    String tahajjut;

    String navor;

    String dohrRange;
    Spinner spm,spy;
    boolean monthSelected = false;
    boolean yearSelected = false;

String sunsetRange;
    BottomNavigationView bottomNavigationView;
    ActivityTimingCBinding binding;

    @Override
    protected void onCreate(Bundle bundle) {

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        String bengaliCurrentYear = convertToBanglaNumber(String.valueOf(currentYear));
        String bengaliCurrentMonth = convertToBanglaMonth(currentMonth);
        super.onCreate(bundle);
        if(Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

        binding = ActivityTimingCBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List asList = Arrays.asList(getResources().getStringArray(R.array.months));
        List asList1 = Arrays.asList(getResources().getStringArray(R.array.year));
        int position = asList1.indexOf(String.valueOf(bengaliCurrentYear));
        spm=findViewById(R.id.spinnerm);
        spy=findViewById(R.id.spinnery);
        bottomNavigationView=findViewById(R.id.bottom_menu);

        ArrayAdapter<String>adapter1=new ArrayAdapter<String>(TimingC.this, android.R.layout.simple_spinner_dropdown_item,asList1);
        List<String> bengaliMonthList = new ArrayList<>();
        List<String> englishMonthList = new ArrayList<>(); // New list for English representation

        for (int i = 1; i <= 12; i++) {
            String bengaliMonth = convertToBanglaMonth(i);
            bengaliMonthList.add(bengaliMonth);

            // Convert to English representation and add to the list
            String englishMonth = String.valueOf(i);
            englishMonthList.add(englishMonth);
        }

// Set adapter for spm using bengaliMonthList
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TimingC.this, android.R.layout.simple_spinner_dropdown_item, bengaliMonthList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spm.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        List<String> bengaliYearList = convertToBanglaNumbers(asList1);
        ArrayAdapter<String> bengaliAdapter1 = new ArrayAdapter<>(TimingC.this, android.R.layout.simple_spinner_dropdown_item, bengaliYearList);
        bengaliAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spy.setAdapter(bengaliAdapter1);

        int position1 = bengaliMonthList.indexOf(bengaliCurrentMonth);


        Timingadapter =new timingadapter(this);
        binding.rc.setAdapter(Timingadapter);
        binding.rc.setLayoutManager(new LinearLayoutManager(this));
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        spm.setSelection(position1);

        spm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                month = englishMonthList.get(i);
                Log.d("Month",month);
                monthSelected = true;
                if (yearSelected) {
                    updateData();
                }


                // Your implementation for item selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle case when nothing is selected, if needed
            }
        });

this.spy.setSelection(position);

        spy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                year = asList1.get(i).toString();
                Log.d("Year",year);

                yearSelected = true;
                if (monthSelected) {
                    updateData();
                }


                // Your implementation for item selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                // Handle case when nothing is selected, if needed
            }
        });






        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.homeb:
                        Intent intent = new Intent(TimingC.this.getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;


                    case R.id.namajb:

                        break;

                    case R.id.duab:
                        Intent intent1 = new Intent(TimingC.this.getApplicationContext(), DuaCategoryActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.tasibhb:
                        Intent intent2 = new Intent(TimingC.this.getApplicationContext(), TasbihCounterActivity.class);
                        startActivity(intent2);
                        break;

                }








                return true;
            }
        });




    }


    private String convertToBanglaMonth(int month) {
        String[] banglaMonths = {"জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"};
        if (month >= 1 && month <= banglaMonths.length) {
            return banglaMonths[month - 1]; // Adjusting month index to start from 0
        } else {
            return "অজানা"; // If month is out of range, return a placeholder
        }
    }





    private List<String> convertToBanglaNumbers(List<String> yearList) {
        List<String> bengaliYearList = new ArrayList<>();
        for (String year : yearList) {
            // Assuming year is a number represented as a string
            bengaliYearList.add(convertToBanglaNumber(year));
        }
        return bengaliYearList;
    }



    public String convertToBanglaNumber(String number) {
        StringBuilder banglaNumber = new StringBuilder();
        for (char digit : number.toCharArray()) {
            switch (digit) {
                case '0':
                    banglaNumber.append('০');
                    break;
                case '1':
                    banglaNumber.append('১');
                    break;
                case '2':
                    banglaNumber.append('২');
                    break;
                case '3':
                    banglaNumber.append('৩');
                    break;
                case '4':
                    banglaNumber.append('৪');
                    break;
                case '5':
                    banglaNumber.append('৫');
                    break;
                case '6':
                    banglaNumber.append('৬');
                    break;
                case '7':
                    banglaNumber.append('৭');
                    break;
                case '8':
                    banglaNumber.append('৮');
                    break;
                case '9':
                    banglaNumber.append('৯');
                    break;
                default:
                    banglaNumber.append(digit);
            }
        }
        return banglaNumber.toString();
    }


    private boolean isCurrentDate(String dateString) {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        String currentDate = dateFormat.format(calendar.getTime());
        return currentDate.equals(dateString);
    }



    static String convertToBanglaNumbers(String input) {
        // Define mapping for English to Bangla numbers
        String[] englishNumbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String[] banglaNumbers = {"০", "১", "২", "৩", "৪", "৫", "৬", "৭", "৮", "৯"};

        // Replace English numbers with Bangla numbers
        for (int i = 0; i < englishNumbers.length; i++) {
            input = input.replace(englishNumbers[i], banglaNumbers[i]);
        }

        // Replace English months with Bangla months
        input = input.replace("Jan", "জানুয়ারি");
        input = input.replace("Feb", "ফেব্রুয়ারি");
        input = input.replace("Mar", "মার্চ");
        input = input.replace("Apr", "এপ্রিল");
        input = input.replace("May", "মে");
        input = input.replace("Jun", "জুন");
        input = input.replace("Jul", "জুলাই");
        input = input.replace("Aug", "অগাস্ট");
        input = input.replace("Sep", "সেপ্টেম্বর");
        input = input.replace("Oct", "অক্টোবর");
        input = input.replace("Nov", "নভেম্বর");
        input = input.replace("Dec", "ডিসেম্বর");

        return input;

    }

    private String convertWeekdayToBangla(String weekday) {
        switch (weekday) {
            case "Saturday":
                return "শনিবার";
            case "Sunday":
                return "রবিবার";
            case "Monday":
                return "সোমবার";
            case "Tuesday":
                return "মঙ্গলবার";
            case "Wednesday":
                return "বুধবার";
            case "Thursday":
                return "বৃহস্পতিবার";
            case "Friday":
                return "শুক্রবার";
            default:
                return ""; // Return empty string if weekday is not found
        }
    }

    private String convertHijriToBangla(String hijriDate) {
        // Split the Hijri date into day, month, and year
        String[] parts = hijriDate.split("-");
        int day = Integer.parseInt(parts[0]);
        int monthNumber = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        day -= 1;
        if (day < 1) {
            monthNumber -= 1;
            if (monthNumber < 1) {
                monthNumber = 12;
                year -= 1;
            }
            if (monthNumber == 1 || monthNumber == 2 || monthNumber == 4 || monthNumber == 6 || monthNumber == 8 || monthNumber == 10 || monthNumber == 12) {
                day += 30;
            } else {
                day += 29;
            }
        }

        // Map the Hijri month number to its Bangla equivalent
        String[] banglaMonths = {
                "", "মুহররম", "সাফর", "রবিউল আউয়াল", "রবিউস সানি", "জমাদিউল আউয়াল", "জমাদিউস সানি",
                "রজব", "শাবান", "রমজান", "শাওয়াল", "জ্বিলকদ", "জ্বিলহজ্জ"
        };
        String banglaMonth = banglaMonths[monthNumber];

        // Convert the Hijri day and year to Bangla numerals
        String banglaDay = convertToBanglaNumbers(String.valueOf(day));
        String banglaYear = convertToBanglaNumbers(String.valueOf(year));

        // Construct the Bangla date string
        return banglaDay + " " + banglaMonth + " " + banglaYear;
    }



    private String removeTimeZone(String input) {
        // Remove the timezone part if present (e.g., "05:15 (+06)")
        return input.replaceAll("\\s\\([^\\)]+\\)", "");
    }




    private void updateData() {

        PrayerSharedPreference prayerSharedPreference = new PrayerSharedPreference(getApplicationContext());

// Retrieve latitude and longitude from shared preferences
        String latitudeString = prayerSharedPreference.getLatitude();
        String longitudeString = prayerSharedPreference.getLongitude();

// Parse the latitude and longitude to double
        double latitude = Double.parseDouble(latitudeString);
        double longitude = Double.parseDouble(longitudeString);
        Calendar calendarapi = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat dateFormat1=new SimpleDateFormat("M",Locale.ENGLISH);





        String url="https://api.aladhan.com/v1/calendar/"+year+"/"+month+"?latitude="+latitude+"&longitude="+longitude+"&method=1";
        Log.d("url ",url);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Timingadapter.clear();
                    String status = response.getString("status");
                    if (status.equals("OK")) {

                        JSONArray dataArray= response.getJSONArray(("data"));

                        for(int i=0;i<dataArray.length();i++){
                            JSONObject timingObject=dataArray.getJSONObject(i).getJSONObject("timings");
                            JSONObject dateObject=dataArray.getJSONObject(i).getJSONObject("date");
                            JSONObject gregorianObject = dateObject.getJSONObject("gregorian");
                            JSONObject weekdayObject = gregorianObject.getJSONObject("weekday");

                            fajr = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Fajr")));
                            dohr = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Dhuhr")));
                            asr = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Asr")));
                            magrib = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Maghrib")));
                            isha = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Isha")));
                            imsak = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Imsak")));
                            sunset = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Sunset")));
                            sunrise = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Sunrise")));
                            date = convertToBanglaNumbers(dateObject.getString("readable"));
                            date1 = dateObject.getString("readable");
                            weekday = convertWeekdayToBangla(weekdayObject.getString("en"));
                            JSONObject hijriObject = dateObject.getJSONObject("hijri");
                            hijri = convertHijriToBangla(hijriObject.getString("date"));




//




                            // Assuming sunset is in the format "hh:mm"
// Assuming sunset is in the format "hh:mm"
                            // Assuming sunset is in the format "hh:mm"
                            String[] sunsetParts = sunset.split(":");
                            int sunsetHour = Integer.parseInt(sunsetParts[0]);
                            int sunsetMinute = Integer.parseInt(sunsetParts[1]);
                            int sunsetHoure = Integer.parseInt(sunsetParts[0]);
                            int sunsetMinutee = Integer.parseInt(sunsetParts[1]);

// Subtract 16 minutes from the sunset time to get the start time
                            sunsetMinute -= 16;
                            if (sunsetMinute < 0) {
                                sunsetHour -= 1;
                                sunsetMinute += 60;
                            }

// Format the start time in "hh:mm" format
                            String startHour = String.format(Locale.ENGLISH, "%02d", sunsetHour);
                            String startMinute = String.format(Locale.ENGLISH, "%02d", sunsetMinute);
                            String startTime = startHour + ":" + startMinute;

// Subtract 1 minute from the sunset time to get the end time
                            sunsetMinutee -= 1;
                            if (sunsetMinutee < 0) {
                                sunsetHoure -= 1;
                                sunsetMinutee += 60;
                            }

// Format the end time in "hh:mm" format
                            String endHour = String.format(Locale.ENGLISH, "%02d", sunsetHoure);
                            String endMinute = String.format(Locale.ENGLISH, "%02d", sunsetMinutee);
                            String endTime = endHour + ":" + endMinute;

// Combine start time and end time to create the final string
                            sunsetRange = startTime + " - " + endTime;

// Optionally, convert the result to Bangla numbers if needed
                            sunsetRange = convertToBanglaNumbers(sunsetRange);




                            // Assuming dohr is in the format "hh:mm"
                            String[] dohrParts = dohr.split(":");
                            int dohrHour = Integer.parseInt(dohrParts[0]);
                            int dohrMinute = Integer.parseInt(dohrParts[1]);
                            int dohrHoure = Integer.parseInt(dohrParts[0]);
                            int dohrMinutee = Integer.parseInt(dohrParts[1]);

// Subtract 10 minutes from the dohr time
                            dohrMinute -= 10;
                            if (dohrMinute < 0) {
                                dohrHour -= 1;
                                dohrMinute += 60;
                            }

// Format the start time in "hh:mm" format
                            String startHour1 = String.format(Locale.ENGLISH, "%02d", dohrHour);
                            String startMinute1 = String.format(Locale.ENGLISH, "%02d", dohrMinute);
                            String startTime1 = startHour1 + ":" + startMinute1;

// Calculate the end time by subtracting 1 minute from the dohr time
                            dohrMinutee -= 1;
                            if (dohrMinutee < 0) {
                                dohrHoure -= 1;
                                dohrMinutee += 60;
                            }

// Format the end time in "hh:mm" format
                            String endHour1 = String.format(Locale.ENGLISH, "%02d", dohrHoure);
                            String endMinute1 = String.format(Locale.ENGLISH, "%02d", dohrMinutee);
                            String endTime1 = endHour1 + ":" + endMinute1;

// Combine start time and end time to create the final string
                            dohrRange = startTime1 + " - " + endTime1;

// Optionally, convert the result to Bangla numbers if needed
                            dohrRange = convertToBanglaNumbers(dohrRange);





                            String tahajjutTime = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Isha")));

// Add 4 hours to the Tahajjut time
                            String[] tahajjutTimeParts = tahajjutTime.split(":");
                            int hours = Integer.parseInt(tahajjutTimeParts[0]) + 4;
                            if (hours >= 24) {
                                hours -= 24; // If the result is more than 24, subtract 24 to get the correct hour
                            }
                            String tahajjutHour = String.format(Locale.ENGLISH, "%02d", hours);
                            tahajjutHour = convertToBanglaNumbers(tahajjutHour);
                            String tahajjutMinutes = tahajjutTimeParts[1];
                            tahajjut = tahajjutHour + ":" + tahajjutMinutes;

///


                            String[] sunriseParts = sunrise.split(":");
                            int sunriseHour = Integer.parseInt(sunriseParts[0]);
                            int sunriseMinute = Integer.parseInt(sunriseParts[1]);

// Calculate the end time by adding 15 minutes to the sunrise time
                            sunriseMinute += 15;
                            if (sunriseMinute >= 60) {
                                sunriseHour += 1;
                                sunriseMinute -= 60;
                            }

// Format the end time in "hh:mm" format
                            String navorHour = String.format(Locale.ENGLISH, "%02d", sunriseHour);
                            String navorMinute = String.format(Locale.ENGLISH, "%02d", sunriseMinute);
                            navor = sunrise + " - " + navorHour + ":" + navorMinute;

// Optionally, convert the result to Bangla numbers if needed
                            navor = convertToBanglaNumbers(navor);


                            boolean isCurrent = isCurrentDate(date1);
                            timing Timing= new timing(fajr,dohr,asr,magrib,isha,imsak,sunset,sunrise,date,weekday,hijri,tahajjut,navor,dohrRange,sunsetRange,isCurrent);
                            Timingadapter.add(Timing);

                        }
                        scrollToCurrentDate();


                        SharedPreferences preferences = getSharedPreferences("prayer_timings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        // ... add parsed data to editor (replace with your parsed data structure) ...
                        editor.apply();
                        editor.putString("Fajr", fajr);
                        editor.putString("dohr", dohr);
                        editor.putString("asr", asr);
                        editor.putString("magrib", magrib);
                        editor.putString("isha", isha);
                        editor.putString("imsak", imsak);
                        editor.putString("sunset", sunset);
                        editor.putString("sunrise", sunrise);
                        editor.putString("date", date);
                        editor.putString("hijri", hijri);
                        editor.putString("tahajjut", tahajjut);
                        editor.putString("weekday", weekday);
                        editor.putString("navor", navor);
                        editor.putString("dohrran", dohrRange);
                        editor.putString("sunsetran", sunsetRange);
                        editor.putString("date1",date1);

                        editor.apply();

                    }
                }

                catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {

                    SharedPreferences preferences = getSharedPreferences("prayer_timings", MODE_PRIVATE);
                    String fajr = preferences.getString("Fajr", "");
                    String dohr = preferences.getString("dohr", "");
                    String asr = preferences.getString("asr", "");
                    String magrib = preferences.getString("magrib", "");
                    String isha = preferences.getString("isha", "");
                    String imsak = preferences.getString("imsak", "");
                    String sunset = preferences.getString("sunset", "");
                    String sunrise = preferences.getString("sunrise", "");
                    String date = preferences.getString("date", "");
                    String hijri = preferences.getString("hijri", "");
                    String tahajjut = preferences.getString("tahajjut", "");
                    String weekday = preferences.getString("weekday", "");
                    String navor = preferences.getString("navor", "");
                    String dohrRange = preferences.getString("dohrran", "");
                    String sunsetRange = preferences.getString("sunsetran", "");
                    String date1 = preferences.getString("date1", "");

                    boolean isCurrent = isCurrentDate(date1);
                    timing Timing = new timing(fajr, dohr, asr, magrib, isha, imsak, sunset, sunrise, date, weekday, hijri, tahajjut, navor, dohrRange, sunsetRange, isCurrent);

                    if (sunrise != null && sunset != null && fajr != null && dohr != null && asr != null && magrib != null &&
                            isha != null && imsak != null && date != null && hijri != null && tahajjut != null && weekday != null &&
                            navor != null && dohrRange != null && sunsetRange != null && date1 != null) {
                        Timingadapter.add(Timing);
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);

                }


            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
















    private void loadData() {

        PrayerSharedPreference prayerSharedPreference = new PrayerSharedPreference(getApplicationContext());

// Retrieve latitude and longitude from shared preferences
        String latitudeString = prayerSharedPreference.getLatitude();
        String longitudeString = prayerSharedPreference.getLongitude();

// Parse the latitude and longitude to double
        double latitude = Double.parseDouble(latitudeString);
        double longitude = Double.parseDouble(longitudeString);
        Calendar calendarapi = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        SimpleDateFormat dateFormat1=new SimpleDateFormat("M",Locale.ENGLISH);
        year = dateFormat.format(calendarapi.getTime());
        month=dateFormat1.format(calendarapi.getTime());



        String url="https://api.aladhan.com/v1/calendar/"+year+"/"+month+"?latitude="+latitude+"&longitude="+longitude+"&method=1";
        Log.d("url ",url);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    Timingadapter.clear();
                    String status = response.getString("status");
                    if (status.equals("OK")) {

                        JSONArray dataArray= response.getJSONArray(("data"));

                        for(int i=0;i<dataArray.length();i++){
                            JSONObject timingObject=dataArray.getJSONObject(i).getJSONObject("timings");
                            JSONObject dateObject=dataArray.getJSONObject(i).getJSONObject("date");
                            JSONObject gregorianObject = dateObject.getJSONObject("gregorian");
                            JSONObject weekdayObject = gregorianObject.getJSONObject("weekday");

                            fajr = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Fajr")));
                           dohr = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Dhuhr")));
                         asr = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Asr")));
                            magrib = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Maghrib")));
                             isha = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Isha")));
                            imsak = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Imsak")));
                           sunset = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Sunset")));
                            sunrise = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Sunrise")));
                             date = convertToBanglaNumbers(dateObject.getString("readable"));
                            date1 = dateObject.getString("readable");
                            weekday = convertWeekdayToBangla(weekdayObject.getString("en"));
                            JSONObject hijriObject = dateObject.getJSONObject("hijri");
                             hijri = convertHijriToBangla(hijriObject.getString("date"));




//




                            // Assuming sunset is in the format "hh:mm"
// Assuming sunset is in the format "hh:mm"
                            // Assuming sunset is in the format "hh:mm"
                            String[] sunsetParts = sunset.split(":");
                            int sunsetHour = Integer.parseInt(sunsetParts[0]);
                            int sunsetMinute = Integer.parseInt(sunsetParts[1]);
                            int sunsetHoure = Integer.parseInt(sunsetParts[0]);
                            int sunsetMinutee = Integer.parseInt(sunsetParts[1]);

// Subtract 16 minutes from the sunset time to get the start time
                            sunsetMinute -= 16;
                            if (sunsetMinute < 0) {
                                sunsetHour -= 1;
                                sunsetMinute += 60;
                            }

// Format the start time in "hh:mm" format
                            String startHour = String.format(Locale.ENGLISH, "%02d", sunsetHour);
                            String startMinute = String.format(Locale.ENGLISH, "%02d", sunsetMinute);
                            String startTime = startHour + ":" + startMinute;

// Subtract 1 minute from the sunset time to get the end time
                            sunsetMinutee -= 1;
                            if (sunsetMinutee < 0) {
                                sunsetHoure -= 1;
                                sunsetMinutee += 60;
                            }

// Format the end time in "hh:mm" format
                            String endHour = String.format(Locale.ENGLISH, "%02d", sunsetHoure);
                            String endMinute = String.format(Locale.ENGLISH, "%02d", sunsetMinutee);
                            String endTime = endHour + ":" + endMinute;

// Combine start time and end time to create the final string
                            sunsetRange = startTime + " - " + endTime;

// Optionally, convert the result to Bangla numbers if needed
                            sunsetRange = convertToBanglaNumbers(sunsetRange);




                            // Assuming dohr is in the format "hh:mm"
                            String[] dohrParts = dohr.split(":");
                            int dohrHour = Integer.parseInt(dohrParts[0]);
                            int dohrMinute = Integer.parseInt(dohrParts[1]);
                            int dohrHoure = Integer.parseInt(dohrParts[0]);
                            int dohrMinutee = Integer.parseInt(dohrParts[1]);

// Subtract 10 minutes from the dohr time
                            dohrMinute -= 10;
                            if (dohrMinute < 0) {
                                dohrHour -= 1;
                                dohrMinute += 60;
                            }

// Format the start time in "hh:mm" format
                            String startHour1 = String.format(Locale.ENGLISH, "%02d", dohrHour);
                            String startMinute1 = String.format(Locale.ENGLISH, "%02d", dohrMinute);
                            String startTime1 = startHour1 + ":" + startMinute1;

// Calculate the end time by subtracting 1 minute from the dohr time
                            dohrMinutee -= 1;
                            if (dohrMinutee < 0) {
                                dohrHoure -= 1;
                                dohrMinutee += 60;
                            }

// Format the end time in "hh:mm" format
                            String endHour1 = String.format(Locale.ENGLISH, "%02d", dohrHoure);
                            String endMinute1 = String.format(Locale.ENGLISH, "%02d", dohrMinutee);
                            String endTime1 = endHour1 + ":" + endMinute1;

// Combine start time and end time to create the final string
                        dohrRange = startTime1 + " - " + endTime1;

// Optionally, convert the result to Bangla numbers if needed
                            dohrRange = convertToBanglaNumbers(dohrRange);





                            String tahajjutTime = convertToBanglaNumbers(removeTimeZone(timingObject.getString("Isha")));

// Add 4 hours to the Tahajjut time
                            String[] tahajjutTimeParts = tahajjutTime.split(":");
                            int hours = Integer.parseInt(tahajjutTimeParts[0]) + 4;
                            if (hours >= 24) {
                                hours -= 24; // If the result is more than 24, subtract 24 to get the correct hour
                            }
                            String tahajjutHour = String.format(Locale.ENGLISH, "%02d", hours);
                            tahajjutHour = convertToBanglaNumbers(tahajjutHour);
                            String tahajjutMinutes = tahajjutTimeParts[1];
                            tahajjut = tahajjutHour + ":" + tahajjutMinutes;

///


                            String[] sunriseParts = sunrise.split(":");
                            int sunriseHour = Integer.parseInt(sunriseParts[0]);
                            int sunriseMinute = Integer.parseInt(sunriseParts[1]);

// Calculate the end time by adding 15 minutes to the sunrise time
                            sunriseMinute += 15;
                            if (sunriseMinute >= 60) {
                                sunriseHour += 1;
                                sunriseMinute -= 60;
                            }

// Format the end time in "hh:mm" format
                            String navorHour = String.format(Locale.ENGLISH, "%02d", sunriseHour);
                            String navorMinute = String.format(Locale.ENGLISH, "%02d", sunriseMinute);
                             navor = sunrise + " - " + navorHour + ":" + navorMinute;

// Optionally, convert the result to Bangla numbers if needed
                            navor = convertToBanglaNumbers(navor);


                            boolean isCurrent = isCurrentDate(date1);
                            timing Timing= new timing(fajr,dohr,asr,magrib,isha,imsak,sunset,sunrise,date,weekday,hijri,tahajjut,navor,dohrRange,sunsetRange,isCurrent);
                            Timingadapter.add(Timing);

                        }
                        scrollToCurrentDate();


                        SharedPreferences preferences = getSharedPreferences("prayer_timings", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        // ... add parsed data to editor (replace with your parsed data structure) ...
                        editor.apply();
                        editor.putString("Fajr", fajr);
                        editor.putString("dohr", dohr);
                        editor.putString("asr", asr);
                        editor.putString("magrib", magrib);
                        editor.putString("isha", isha);
                        editor.putString("imsak", imsak);
                        editor.putString("sunset", sunset);
                        editor.putString("sunrise", sunrise);
                        editor.putString("date", date);
                        editor.putString("hijri", hijri);
                        editor.putString("tahajjut", tahajjut);
                        editor.putString("weekday", weekday);
                        editor.putString("navor", navor);
                        editor.putString("dohrran", dohrRange);
                        editor.putString("sunsetran", sunsetRange);
                        editor.putString("date1",date1);

                        editor.apply();

                    }
                }

                catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(TimingC.this, "Failed to update data", Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                SharedPreferences preferences = getSharedPreferences("prayer_timings", MODE_PRIVATE);
                String fajr = preferences.getString("Fajr", "");
                String dohr = preferences.getString("dohr", "");
                String asr = preferences.getString("asr", "");
                String magrib = preferences.getString("magrib", "");
                String isha = preferences.getString("isha", "");
                String imsak = preferences.getString("imsak", "");
                String sunset = preferences.getString("sunset", "");
                String sunrise = preferences.getString("sunrise", "");
                String date = preferences.getString("date", "");
                String hijri = preferences.getString("hijri", "");
                String tahajjut = preferences.getString("tahajjut", "");
                String weekday = preferences.getString("weekday", "");
                String navor = preferences.getString("navor", "");
                String dohrRange = preferences.getString("dohrran", "");
                String sunsetRange = preferences.getString("sunsetran", "");
                String date1 = preferences.getString("date1", "");

                boolean isCurrent = isCurrentDate(date1);
                timing Timing= new timing(fajr,dohr,asr,magrib,isha,imsak,sunset,sunrise,date,weekday,hijri,tahajjut,navor,dohrRange,sunsetRange,isCurrent);

                if (sunrise != null && sunset != null && fajr != null && dohr != null && asr != null && magrib!= null &&
                isha!= null &&imsak!= null &&date!= null &&hijri!= null &&tahajjut!= null &&weekday!= null &&
                navor!= null &&dohrRange!= null &&sunsetRange!= null &&date1!= null) {
                    Timingadapter.add(Timing);
                }

                else{
                    Toast.makeText(TimingC.this, "Failed to update data", Toast.LENGTH_LONG).show();

                }

            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    private void scrollToCurrentDate() {
        binding.rc.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for (int i = 0; i < Timingadapter.getItemCount(); i++) {
                    timing Timing = Timingadapter.getItem(i);
                    if (Timing.isCurrentDate()) {
                        binding.rc.smoothScrollToPosition(i); // Scroll to the position of the current date
                        break; // Stop looping once the current date is found
                    }
                }
                // Remove the listener to avoid multiple calls
                binding.rc.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;

            case R.id.home1:
                Intent intent10 = new Intent(getApplicationContext(), Aboutdev.class);
                startActivity(intent10);
                return true;

            case R.id.privacy :
                Intent intent3 = new Intent(getApplicationContext(), Privacy_Policy_activity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                return true;
            case R.id.rate :
                if (isOnline()) {
                    Intent intent4 = new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()));
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent4);
                } else {
                    Toast makeText = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
                    makeText.setGravity(17, 0, 0);
                    makeText.show();
                }
                return true;
            case R.id.share :
                if (isOnline()) {
                    Intent intent5 = new Intent("android.intent.action.SEND");
                    intent5.setType("text/plain");
                    intent5.putExtra("android.intent.extra.TEXT", "Hi! I'm using a great Islamic application. Check it out:http://play.google.com/store/apps/details?id=" + getPackageName());
                    intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(Intent.createChooser(intent5, "Share with Friends"));
                } else {
                    Toast makeText2 = Toast.makeText(getApplicationContext(), "No Internet Connection..", Toast.LENGTH_SHORT);
                    makeText2.setGravity(17, 0, 0);
                    makeText2.show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }



}