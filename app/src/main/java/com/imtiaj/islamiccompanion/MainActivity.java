package com.imtiaj.islamiccompanion;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.imtiaj.islamiccompanion.Appcompany.Privacy_Policy_activity;
import com.imtiaj.islamiccompanion.DbHelper.AthkarDatabase;
import com.imtiaj.islamiccompanion.HijriCalender.HijriCalenderActivity;
import com.imtiaj.islamiccompanion.PrayerTimeCal.PrayerTime;
import com.imtiaj.islamiccompanion.PreferenceUtil.PrayerSharedPreference;
import com.imtiaj.islamiccompanion.Service.PrayerTimeService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.chrono.HijrahChronology;
import org.threeten.bp.chrono.HijrahDate;
import org.threeten.bp.temporal.ChronoField;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    public static final int REQUEST_CODE = 1010101;
    private final int REQUEST_CODE1 = 11;
    private SharedPreferences preferences;
    private Handler handler;
    private final String NOTIFICATION_DEFAULT_CHANNEL_ID = "1001";

    private Uri SOUND_URI;
    private String channelId;
    int uniqueId = 0;
    BottomNavigationView bottomNavigationView;

    LinearLayout AlQuran;
    LinearLayout btnAllPrayer;
    LinearLayout btnAthkar;
    LinearLayout btnDua;
    LinearLayout btnDua99AllahName;



    LinearLayout btnnam;

    LinearLayout btnhadis;
    LinearLayout btnsunnot;

TextView tcr;
TextView ten;

TextView set1;
TextView rise1;

TextView tbn;

TextView tvsehri;
TextView tviftar,tvloca;
    LinearLayout btnboyan1;
    LinearLayout btnHajj;
    LinearLayout btnHijriCalender;
    RelativeLayout btnNotificationSetting;

    LinearLayout btnlocate;
    RelativeLayout btnQiblaCompass;
    LinearLayout btnTabish;
    LinearLayout btnmosq;
    RelativeLayout btnZakat;


    Integer[] dataTime = {0, 1, 2, 3, 5, 6};
    boolean isChangeConfigClick = true;
    RelativeLayout llLoadingData;
    LinearLayout llPrayerTime;
    PrayerSharedPreference prayerSharedPreference;
    
    TextView tvAfterPrayer;
    TextView tvNextPrayer;

    String loc,fajr,dhuhr,asr,magrib,isha,sleep="21:40";



    public String getPrayerName(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 5 ? i != 6 ? "Unknown" : "Isha" : "Maghrib" : "Asr" : "Duhur" : "Sunrise" : "Fajr";
    }

    @Override 
    protected void onCreate(Bundle bundle) {
        getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onCreate(bundle);
        preferences = getSharedPreferences("prayer_times", MODE_PRIVATE);

        // Initialize handler on the main thread
        handler = new Handler(Looper.getMainLooper());
        AndroidThreeTen.init(this);
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(Color.parseColor("#E5DDD0"));
        setContentView(R.layout.activity_main);
        bottomNavigationView=findViewById(R.id.bottom_menu);
tcr=(TextView) findViewById(R.id.timecr);
ten=(TextView)findViewById(R.id.timeen);
tbn=(TextView)findViewById(R.id.timebn);
rise1=(TextView)findViewById(R.id.rise);
set1=(TextView)findViewById(R.id.set);
tvsehri=(TextView) findViewById(R.id.sehri);
tviftar=(TextView) findViewById(R.id.ifter);
tvloca=(TextView) findViewById(R.id.tvloc111);



            try {
                createNotificationChannel();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        //startPrayerTimeChecker();













//sunrise


        //sunsetrise

        PrayerSharedPreference prayerSharedPreference = new PrayerSharedPreference(getApplicationContext());
        loc=prayerSharedPreference.getLocation();
if(loc.isEmpty()){
    tvloca.setText("Feni, Bangladesh");

}
else{

        tvloca.setText(loc);
}


// Use a while loop to continuously check for latitude and longitude data
        while (true) {


            String latitudeString = prayerSharedPreference.getLatitude();
            String longitudeString = prayerSharedPreference.getLongitude();


            // Check if latitude and longitude data are available
            if (latitudeString != null && longitudeString != null && loc !=null) {
                // Latitude and longitude data are available, proceed with making the API request

                // Parse the latitude and longitude to double


                double latitude = Double.parseDouble(latitudeString);
                double longitude = Double.parseDouble(longitudeString);

                //int method = 1;
                Calendar calendarapi = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                String date = dateFormat.format(calendarapi.getTime());

                // Make the API request to retrieve sunset data
                SunriseSunsetApiClient.getSunriseSunsetService()
                        .getSunriseSunset(date, latitude, longitude)
                        .enqueue(new Callback<SunriseSunsetResponse>() {
                            @Override
                            public void onResponse(Call<SunriseSunsetResponse> call, Response<SunriseSunsetResponse> response) {
                                if (response.isSuccessful()) {
                                    SunriseSunsetResponse.Data data = response.body().getData();
                                    SunriseSunsetResponse.Timings results = data.getTimings();
                                    Log.d("API Response", "Response Body: " + response.body().toString());
                                    String sunrise = results.getSunrise();
                                    String sunset = results.getSunset();
                                    String ifter = results.getSunset();
                                    String sehri = results.getImsak();
                                    fajr=results.getFajr();
                                    dhuhr=results.getDhuhr();
                                    asr=results.getAsr();
                                    magrib=results.getMaghrib();
                                    isha=results.getIsha();



                                    System.out.println("Imtiaj:" + sunset);

                                    // Parse the times
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                                    dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
                                    Date sunriseDate = null;
                                    Date sunsetDate = null;
                                    Date ifterDate = null;
                                    Date sehriDate = null;
                                    try {
                                        sunriseDate = dateFormat.parse(sunrise);
                                        sunsetDate = dateFormat.parse(sunset);
                                        ifterDate = dateFormat.parse(sunset);
                                        sehriDate = dateFormat.parse(sehri);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (sehriDate != null) {
                                        Calendar sehriCalendar = Calendar.getInstance();
                                        sehriCalendar.setTime(sehriDate);
                                        sehriCalendar.add(Calendar.MINUTE, -1);
                                        sehriDate = sehriCalendar.getTime();
                                    }
                                    // Convert back to strings in Bangla locale without seconds
                                    dateFormat = new SimpleDateFormat("hh:mm", new Locale("bn"));
                                    if (sunriseDate != null && sunsetDate != null && ifterDate != null
                                            && sehriDate != null) {
                                        sunrise = dateFormat.format(sunriseDate);
                                        sunset = dateFormat.format(sunsetDate);
                                        ifter = dateFormat.format(ifterDate);
                                        sehri = dateFormat.format(sehriDate);


                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("sunrise", sunrise);
                                        editor.putString("sunset", sunset);
                                        editor.putString("ifter", ifter);
                                        editor.putString("sehri", sehri);
                                        editor.putString("fajr",fajr);
                                        editor.putString("dhuhr",dhuhr);
                                        editor.putString("asr",asr);
                                        editor.putString("magrib",magrib);
                                        editor.putString("isha",isha);
                                        editor.apply();

                                        // Update the TextViews
                                        rise1.setText("সুর্যোদয়ঃ " + sunrise);
                                        set1.setText("সুর্যাস্তঃ " + sunset);
                                        tviftar.setText("ইফতারঃ " + ifter);
                                        tvsehri.setText("সেহরিঃ " + sehri);


                                    } else {
                                        Log.d("Error", "Response Body: " + response.errorBody().toString());
                                        // Handle the error
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<SunriseSunsetResponse> call, Throwable t) {
                                // Handle failure
                                String sunrise = preferences.getString("sunrise", "৬ঃ৩০");
                                String sunset = preferences.getString("sunset", "৫ঃ৩০");
                                String ifter = preferences.getString("ifter", "৫ঃ৩০");
                                String sehri = preferences.getString("sehri", "৫ঃ০০");

                                if (sunrise != null && sunset != null && ifter != null && sehri != null) {
                                    // Update UI with saved times
                                    rise1.setText("সুর্যোদয়ঃ " + sunrise);
                                    set1.setText("সুর্যাস্তঃ " + sunset);
                                    tviftar.setText("ইফতারঃ " + ifter);
                                    tvsehri.setText("সেহরিঃ " + sehri);
                                } else {
                                    // Handle the case where there's no saved data
                                }
                            }
                        });

                // Exit the loop once the API call is made
                break;
            }

            // Sleep for a short duration before checking again
            try {
                Thread.sleep(1000); // Sleep for 1 second (adjust as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }




        //notify





















        //In-App Udpates Implementation (1st part)------------------------------------------------------
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        & result.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.FLEXIBLE,MainActivity.this,REQUEST_CODE1);
                    } catch (IntentSender.SendIntentException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

//bttom menu

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

switch (item.getItemId()){

    case R.id.homeb:
        break;


    case R.id.namajb:
        Intent intent = new Intent(MainActivity.this.getApplicationContext(), TimingC.class);
        startActivity(intent);
        break;

    case R.id.duab:
        Intent intent1 = new Intent(MainActivity.this.getApplicationContext(), DuaCategoryActivity.class);
        startActivity(intent1);
        break;

    case R.id.tasibhb:
        Intent intent2 = new Intent(MainActivity.this.getApplicationContext(), TasbihCounterActivity.class);
        startActivity(intent2);
        break;

}








                return true;
            }
        });
















//Enlish Date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy");
        String datet = simpleDateFormat.format(calendar.getTime());

// Map of English numerals to Bangla numerals
        Map<Character, Character> numeralMap1 = new HashMap<>();
        numeralMap1.put('0', '০');
        numeralMap1.put('1', '১');
        numeralMap1.put('2', '২');
        numeralMap1.put('3', '৩');
        numeralMap1.put('4', '৪');
        numeralMap1.put('5', '৫');
        numeralMap1.put('6', '৬');
        numeralMap1.put('7', '৭');
        numeralMap1.put('8', '৮');
        numeralMap1.put('9', '৯');

// Map of English month names to Bangla month names
        Map<String, String> monthMap = new HashMap<>();
        monthMap.put("January", "জানুয়ারি");
        monthMap.put("February", "ফেব্রুয়ারি");
        monthMap.put("March", "মার্চ");
        monthMap.put("April", "এপ্রিল");
        monthMap.put("May", "মে");
        monthMap.put("June", "জুন");
        monthMap.put("July", "জুলাই");
        monthMap.put("August", "আগস্ট");
        monthMap.put("September", "সেপ্টেম্বর");
        monthMap.put("October", "অক্টোবর");
        monthMap.put("November", "নভেম্বর");
        monthMap.put("December", "ডিসেম্বর");

// Map of English weekday names to Bangla weekday names
        Map<String, String> weekdayMap = new HashMap<>();
        weekdayMap.put("Sunday", "রবিবার");
        weekdayMap.put("Monday", "সোমবার");
        weekdayMap.put("Tuesday", "মঙ্গলবার");
        weekdayMap.put("Wednesday", "বুধবার");
        weekdayMap.put("Thursday", "বৃহস্পতিবার");
        weekdayMap.put("Friday", "শুক্রবার");
        weekdayMap.put("Saturday", "শনিবার");

// Convert English numerals, month names, and weekday names to Bangla
        StringBuilder banglaDateen = new StringBuilder("আজ ");
        boolean isFirstWord = true;
        for (String word : datet.split(" ")) {
            if (monthMap.containsKey(word)) {
                banglaDateen.append(monthMap.get(word));
            } else if (weekdayMap.containsKey(word)) {
                banglaDateen.append(weekdayMap.get(word));
                if (isFirstWord) {
                    banglaDateen.append("\n");
                    isFirstWord = false;
                }
            } else {
                for (char c : word.toCharArray()) {
                    if (numeralMap1.containsKey(c)) {
                        banglaDateen.append(numeralMap1.get(c));
                    } else {
                        banglaDateen.append(c);
                    }
                }
            }
            banglaDateen.append(" ");
        }

// Set the text in your TextView
        ten.setText(banglaDateen.toString().trim());






//hijri date
        HijrahDate hijriDate = HijrahChronology.INSTANCE.dateNow();

// Subtract 2 from the day
        int day = hijriDate.get(ChronoField.DAY_OF_MONTH) - 1;
        int monthNumber = hijriDate.get(ChronoField.MONTH_OF_YEAR);
        int year = hijriDate.get(ChronoField.YEAR);

// If day is less than 1, adjust the month and year
        if (day < 1) {
            monthNumber -= 1;
            if (monthNumber < 1) {
                monthNumber = 12;
                year -= 1;
            }
            if (monthNumber == 1 || monthNumber == 3 || monthNumber == 5 || monthNumber == 7 || monthNumber == 8 || monthNumber == 10 || monthNumber == 12) {
                day += 30;
            } else {
                day += 29;
            }
        }

// Custom array of month names in Hijri calendar
        String[] hijriMonthNames = {
                "মহরম", "সফর", "রবিউল আউয়াল", "রবিউস সানি",
                "জমাদিউল আউয়াল", "জমাদিউস সানি", "রজব", "শাবান",
                "রমজান", "শওয়াল", "জিলক্বদ", "জিলহজ্জ"
        };

// Replace the numeric month with the corresponding name
        String banglaMonth = hijriMonthNames[monthNumber - 1];

// Convert the Hijri day and year to Bangla numerals
        String banglaDay = TimingC.convertToBanglaNumbers(String.valueOf(day));
        String banglaYear = TimingC.convertToBanglaNumbers(String.valueOf(year));

// Construct the Bangla date string
        String banglaDate = banglaDay + " " + banglaMonth + " " + banglaYear;

// Add "হিজরি" after the converted Hijri date
        banglaDate += " হিজরি";

// Set the text in your Hijri date TextView
        tcr.setText(banglaDate);



        //Bangla


        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
        String georgianDate = simpleDateFormat2.format(calendar2.getTime());

// Adjust for Bengali year


// Map of English numerals to Bangla numerals
        Map<Character, Character> numeralMap2 = new HashMap<>();
        numeralMap2.put('0', '০');
        numeralMap2.put('1', '১');
        numeralMap2.put('2', '২');
        numeralMap2.put('3', '৩');
        numeralMap2.put('4', '৪');
        numeralMap2.put('5', '৫');
        numeralMap2.put('6', '৬');
        numeralMap2.put('7', '৭');
        numeralMap2.put('8', '৮');
        numeralMap2.put('9', '৯');

// Map of English month names to Bangla month names
        Map<String, Integer> monthMap2 = new HashMap<>();
        monthMap2.put("April", 0);
        monthMap2.put("May", 1);
        monthMap2.put("June", 2);
        monthMap2.put("July", 3);
        monthMap2.put("August", 4);
        monthMap2.put("September", 5);
        monthMap2.put("October", 6);
        monthMap2.put("November", 7);
        monthMap2.put("December", 8);
        monthMap2.put("January", 9);
        monthMap2.put("February", 10);
        monthMap2.put("March", 11);

        String currentMonthName = new SimpleDateFormat("MMMM", Locale.ENGLISH).format(calendar2.getTime());
        int currentMonth = monthMap2.get(currentMonthName);
        int currentDayy = calendar2.get(Calendar.DAY_OF_MONTH);
        int bengaliYearAdjustment = 0; // Initialize adjustment to 0
        if (currentMonth <= 3 && currentDayy >= 14) { // April to July (first 4 months)
            bengaliYearAdjustment = 593; // Add 593 for remaining months of previous year
        } else {
            bengaliYearAdjustment = 594; // Subtract 593 for months within current Bengali year
        }
// Array of starting dates for each Bengali month
        int[] startingDates = {14, 15, 15, 16, 16, 16, 17, 16, 14, 14, 14, 15};

// Convert English numerals and month names to Bangla
        StringBuilder banglaDatenew = new StringBuilder();

// Append the Bengali month name
        int currentDaym = calendar2.get(Calendar.DAY_OF_MONTH);
        String[] bengaliMonths = {"বৈশাখ", "জ্যৈষ্ঠ", "আষাঢ়", "শ্রাবণ", "ভাদ্র", "আশ্বিন", "কার্তিক", "অগ্রহায়ণ", "পৌষ", "মাঘ", "ফাল্গুন", "চৈত্র"};
        if (currentDaym < startingDates[currentMonth]) {
            currentMonth = (currentMonth + 11) % 12; // Move to the previous Bengali month
        }

        banglaDatenew.append(bengaliMonths[currentMonth]);

// Append the day of the month
        // Append the day of the month
        int currentDay = calendar2.get(Calendar.DAY_OF_MONTH);
        int dayInMonth;
        if (currentDay >= startingDates[currentMonth]) {
            dayInMonth = currentDay - startingDates[currentMonth] + 1;
        } else {
            dayInMonth = currentDay + (30 - startingDates[(currentMonth + 11) % 12]) + 1;
        }
        banglaDatenew.append(" " + numeralMap2.get(Character.forDigit(dayInMonth / 10, 10)) + numeralMap2.get(Character.forDigit(dayInMonth % 10, 10)));


// Append the Bengali year (adjusted)


        int currentYear = calendar2.get(Calendar.YEAR) - bengaliYearAdjustment;



        banglaDatenew.append(", " + numeralMap2.get((char) ((currentYear / 1000) + '0'))
                + numeralMap2.get((char) (((currentYear / 100) % 10) + '0'))
                + numeralMap2.get((char) (((currentYear / 10) % 10) + '0'))
                + numeralMap2.get((char) ((currentYear % 10) + '0'))+ " বঙ্গাব্দ");

// Set the text in your Bangla date TextView
        tbn.setText(banglaDatenew.toString());


//Log.d("durmia",fajr);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        this.prayerSharedPreference = new PrayerSharedPreference(this);
        this.btnQiblaCompass = (RelativeLayout) findViewById(R.id.btnQiblaCompass);
        this.btnDua = (LinearLayout) findViewById(R.id.btnDua);
        this.btnDua99AllahName = (LinearLayout) findViewById(R.id.btnDua99AllahName);
        this.btnTabish = (LinearLayout) findViewById(R.id.btnTabish);
        this.AlQuran = (LinearLayout) findViewById(R.id.AlQuran);
        this.btnHijriCalender = (LinearLayout) findViewById(R.id.btnHijriCalender);
        this.btnAllPrayer = (LinearLayout) findViewById(R.id.btnAllPrayer);
        this.btnAthkar = (LinearLayout) findViewById(R.id.btnAthkar);
        this.btnZakat = (RelativeLayout) findViewById(R.id.btnZakat);
        this.btnHajj = (LinearLayout) findViewById(R.id.btnHajj);

        this.btnnam=(LinearLayout)findViewById(R.id.btnname);
        this.btnboyan1=(LinearLayout)findViewById(R.id.btnboyan);
        this.btnhadis=(LinearLayout)findViewById(R.id.btnhadith);
        this.btnsunnot=(LinearLayout)findViewById(R.id.btnsunnah);
        this.btnmosq=(LinearLayout)findViewById(R.id.btnmosque);
        this.btnlocate=(LinearLayout)findViewById(R.id.btnlocate);

        this.btnNotificationSetting = (RelativeLayout) findViewById(R.id.btnNotificationSetting);
        this.llLoadingData = (RelativeLayout) findViewById(R.id.llLoadingData);
        this.llPrayerTime = (LinearLayout) findViewById(R.id.llPrayerTime);
        this.tvNextPrayer = (TextView) findViewById(R.id.tvNextPrayer);
        this.tvAfterPrayer = (TextView) findViewById(R.id.tvAfterPrayer);
        new AthkarDatabase(this).createDb();



        this.btnlocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), PrayerTimeConfigureActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });


        this.btnQiblaCompass.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), CompassActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnDua.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), DuaCategoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnDua99AllahName.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), AllahNameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });





        this.btnnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), islamic_name.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });


        this.btnboyan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), boyan.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });



        this.btnmosq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), TimingC.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });

        this.btnhadis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), hadith.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });

        this.btnsunnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), sunnah.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });




        this.AlQuran.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), QuranCategoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnHijriCalender.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), HijriCalenderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnTabish.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), TasbihCounterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnAllPrayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!MainActivity.this.tvNextPrayer.getText().toString().equals("--:-- --")) {
                    Intent intent = new Intent(MainActivity.this.getApplicationContext(), AllPrayerActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    MainActivity.this.startActivity(intent);
                    return;
                }
                Toast.makeText(MainActivity.this, "Not yet prayer time", Toast.LENGTH_SHORT).show();
            }
        });
        this.btnAthkar.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), AthkarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnZakat.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), ZakatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnHajj.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), HajjJourneyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
        this.btnNotificationSetting.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), NotificationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(intent);
            }
        });




        if (!isMyServiceRunning(PrayerTimeService.class, this)) {
            Intent intent = new Intent(this, PrayerTimeService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                showDialogPermission();
            } else if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
            } else if (isLocationEnabled(this)) {
                if (this.prayerSharedPreference.getIsFirstTimeRunPref()) {
                    this.prayerSharedPreference.setIsFirstTimeRunPref(false);
                    this.isChangeConfigClick = true;
                    startActivity(new Intent(getApplicationContext(), PrayerTimeConfigureActivity.class));
                }
            } else {
                showDialog();
            }
        }
    }








    //Notification
    private void startPrayerTimeChecker() {
        String fajrt=preferences.getString("fajr",null);
        String dhuhrt=preferences.getString("dhuhr",null);
        String asrt=preferences.getString("asr",null);
        String magribt=preferences.getString("magrib",null);
        String ishat=preferences.getString("isha",null);
        //Log.d("durmia",dhuhrt);

        String[] prayerTimes = {fajrt, dhuhrt, asrt, magribt, ishat,sleep};
        // Assuming you have all prayer times including sleep

        String[] prayerTitles = {
                "এখন ফজরের ওয়াক্ত শুরু!  " + fajrt,
                "এখন যোহরের ওয়াক্ত শুরু! " + dhuhrt,
                "এখন আসরের ওয়াক্ত শুরু! " + asrt,
                "এখন মাগরিবের ওয়াক্ত শুরু! " + magribt,
                "এখন ইশার ওয়াক্ত শুরু!  " + ishat,
                "ঘুমানোর আগের দোয়া!  " + sleep};
        String[] prayerContents = {
                "‘যারা রাতের আঁধারে মসজিদের দিকে হেঁটে যায়, তাদেরকে কেয়ামতের দিন পরিপূর্ণ নূর প্রাপ্তির সুসংবাদ দাও। ’ (আবু দাউদ)",
                "যে ব্যক্তি নিয়মিত জোহরের নামাজের পূর্বে চার রাকাত ও পরে চার রাকাত (দুই রাকাত নফলসহ) আদায় করবে, আল্লাহ তায়ালা তার ওপর জাহান্নামের আগুন হারাম করে দেবেন।’ (তিরমিজি : ৪২৯)",
                "‘যে ব্যক্তি আসরের নামাজ ছেড়ে দেয় তার আমল বিনষ্ট হয়ে যায়।’ (বুখারি, হাদিস : ৫৫৩)",
                "আমার উম্মত ততদিন কল্যাণের মধ্যে থাকবে অথবা মূল অবস্থায় থাকবে, যতদিন তারা মাগরিবের নামাজ আদায়ে তারকা উজ্জ্বল হওয়া পর্যন্ত বিলম্ব না করবে।’ (আবু দাউদ, হাদিস : ৪১৮)",
                "‘মুনাফিকদের জন্য ফজর ও এশার নামাজের চেয়ে অধিক ভারী কোনো নামাজ নেই। এ দুই নামাজের ফজিলত যদি তারা জানত, তাহলে হামাগুড়ি দিয়ে হলেও তারা উপস্থিত হতো।’ (বুখারি, হাদিস : ৬৫৭)",
                "আল্লাহুম্মা বিসমিকা আমুতু ওয়া আহইয়া।\n" +
                        "অর্থ : ‘হে আল্লাহ! আপনারই নামে মরে যাই আবার আপনারই নামে জীবন লাভ করি।’"};




        for (int i = 0; i < prayerTimes.length; i++) {
            setAlarmAndNotificationForPrayerTime(this, prayerTimes[i], prayerTitles[i], prayerContents[i]);
        }
    }

    private void setAlarmAndNotificationForPrayerTime(Context context, String prayerTime, String title, String content) {
        // Convert prayer time string to hours and minutes
        String[] timeParts = prayerTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Get current time
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        // Set alarm only if the prayer time is in the future
        if (currentHour < hour || (currentHour == hour && currentMinute < minute)) {
            // Convert prayer time to milliseconds
            long alarmTimeInMillis = calculateAlarmTime(hour, minute);

            //Log.d("durmia", String.valueOf(alarmTimeInMillis));

            // Set the alarm
            setAlarm(context, alarmTimeInMillis, title, content);

        }
    }

    private long calculateAlarmTime(int hour, int minute) {
        Calendar alarmTime = Calendar.getInstance();
        alarmTime.set(Calendar.HOUR_OF_DAY, hour);
        alarmTime.set(Calendar.MINUTE, minute);
        alarmTime.set(Calendar.SECOND, 0);
        return alarmTime.getTimeInMillis();
    }

    private void setAlarm(Context context, long alarmTimeInMillis, String title, String content) {
        // Create an Intent for the com.imtiaj.islamiccompanion.AlarmReceiver
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        alarmIntent.putExtra("title", title);
        alarmIntent.putExtra("content", content);

        // Create a PendingIntent for the alarm
        PendingIntent pendingIntent ;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(context, uniqueId++, alarmIntent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, uniqueId++, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }




        // Get an instance of AlarmManager
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set the alarm
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (!alarmManager.canScheduleExactAlarms()) {
                    Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                    context.startActivity(intent);
                }

                 else{

                // Use setExactAndAllowWhileIdle for Android 14 and above
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
                Log.d("AlarmManager", "Alarm set for Android 14+");}
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Use setExact for Android 6 to 13
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
                Log.d("AlarmManager", "Alarm set for Android 6-13");
            } else {
                // Use set for older Android versions
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeInMillis, pendingIntent);
                Log.d("AlarmManager", "Alarm set for older Android versions");
            }

            // Show a Toast message indicating that the alarm is set
           // Toast.makeText(context, "Alarm set successfully", Toast.LENGTH_SHORT).show();
        } else {
            // Show a Toast message indicating that there was an error setting the alarm
            //Toast.makeText(context, "Failed to set alarm", Toast.LENGTH_SHORT).show();
        }
    }




    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "imtiaj";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("imtiaj",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


    }













    public void showDialogPermission() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.show_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ((RelativeLayout) dialog.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                dialog.dismiss();
                if (Build.VERSION.SDK_INT >= 23) {
                    MainActivity.this.startActivityForResult(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + MainActivity.this.getApplicationContext().getPackageName())), MainActivity.REQUEST_CODE);
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);





        // Handle in-app update result
        if (requestCode == REQUEST_CODE1) {
            Toast.makeText(MainActivity.this, "Start Download", Toast.LENGTH_SHORT).show();
            if (resultCode != RESULT_OK) {
                Log.d("mmm", "Update Fail" + resultCode);
            }
        }

        // Handle other onActivityResult logic
        Log.e("App", "OnActivity Result.");
        if (requestCode == 1001) {
            if (isLocationEnabled(this)) {
                if (this.prayerSharedPreference.getIsFirstTimeRunPref()) {
                    this.prayerSharedPreference.setIsFirstTimeRunPref(false);
                    this.isChangeConfigClick = true;
                    startActivity(new Intent(getApplicationContext(), PrayerTimeConfigureActivity.class));
                }
            } else {
                showDialog();
            }
        }
        if (requestCode == 1010101 && Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                showDialogPermission();
            } else if (ContextCompat.checkSelfPermission(getApplicationContext(), "android.permission.ACCESS_FINE_LOCATION") != 0) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 1);
            } else if (isLocationEnabled(this)) {
                if (this.prayerSharedPreference.getIsFirstTimeRunPref()) {
                    this.prayerSharedPreference.setIsFirstTimeRunPref(false);
                    this.isChangeConfigClick = true;
                    startActivity(new Intent(getApplicationContext(), PrayerTimeConfigureActivity.class));
                }
            } else {
                showDialog();
            }
        }


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.SCHEDULE_EXACT_ALARM }, requestCode);





        }


    }


    public boolean isMyServiceRunning(Class<?> cls, Context context) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.prayerSharedPreference.getIsFirstTimeRunPref() || !this.prayerSharedPreference.isPrayerLoadData()) {
            return;
        }
        this.tvNextPrayer.setText("--:-- --");
        this.tvAfterPrayer.setText("--:-- --");


        loc=prayerSharedPreference.getLocation();
        this.tvloca.setText(loc);
        this.llLoadingData.setVisibility(View.VISIBLE);
        this.llPrayerTime.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.isChangeConfigClick = false;
                MainActivity.this.getPrayerData();
            }
        }, 1000L);

        if(preferences.getString("fajr",null)!=null){


            try{

                startPrayerTimeChecker();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }



    }

    public void getPrayerData() {
        this.llLoadingData.setVisibility(View.GONE);
        this.llPrayerTime.setVisibility(View.VISIBLE);
        double parseDouble = Double.parseDouble(this.prayerSharedPreference.getLatitude());
        double parseDouble2 = Double.parseDouble(this.prayerSharedPreference.getLongitude());

        double timeZone = getTimeZone();

        PrayerTime prayerTime = new PrayerTime();
        prayerTime.setTimeFormat(this.prayerSharedPreference.getPrayerTimePref());
        prayerTime.setCalcMethod(this.prayerSharedPreference.getCalMethodPref());
        prayerTime.setAsrJuristic(this.prayerSharedPreference.getAsarMethodPref());
        prayerTime.setAdjustHighLats(0);
        prayerTime.setOffsets(new int[]{0, 0, 0, 0, 0, 0, 0});
        ArrayList<String> prayerTimes = prayerTime.getPrayerTimes(Calendar.getInstance(), parseDouble, parseDouble2, timeZone);
        System.out.println(prayerTimes.get(0));
        System.out.println(prayerTimes.get(1));
        System.out.println(prayerTimes.get(2));
        System.out.println(prayerTimes.get(3));
        System.out.println(prayerTimes.get(5));
        System.out.println(prayerTimes.get(6));
        selectUpcomingTime(prayerTimes);
    }

    public double getTimeZone() {
        return TimeZone.getDefault().getOffset(new Date().getTime()) / 3600000.0d;
    }

    public void selectUpcomingTime(ArrayList<String> arrayList) {
        int i = 0;
        while (true) {
            Integer[] numArr = this.dataTime;
            if (i >= numArr.length) {
                return;
            }
            if (getUpcomingTime(arrayList.get(numArr[i].intValue()))) {
                TextView textView = this.tvNextPrayer;
                textView.setText(getPrayerName(this.dataTime[i].intValue()) + " " + arrayList.get(this.dataTime[i].intValue()));
                Integer[] numArr2 = this.dataTime;
                if (numArr2.length - 1 != i) {
                    if (numArr2[i].intValue() == 3) {
                        TextView textView2 = this.tvAfterPrayer;
                        textView2.setText(getPrayerName(this.dataTime[i].intValue() + 2) + " " + arrayList.get(this.dataTime[i].intValue() + 2));
                        return;
                    }
                    TextView textView3 = this.tvAfterPrayer;
                    textView3.setText(getPrayerName(this.dataTime[i].intValue() + 1) + " " + arrayList.get(this.dataTime[i].intValue() + 1));
                    return;
                }
                this.tvAfterPrayer.setText("No upcoming prayer");
                return;
            }
            i++;
        }
    }

    public boolean getUpcomingTime(String str) {
        if (this.prayerSharedPreference.getPrayerTimePref() == 1) {
            try {
                str = new SimpleDateFormat("HH:mm").format(new SimpleDateFormat("hh:mm a").parse(str));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            simpleDateFormat.format(date);
            System.out.println(simpleDateFormat.format(date));
            if (simpleDateFormat.parse(simpleDateFormat.format(date)).after(simpleDateFormat.parse(str))) {
                PrintStream printStream = System.out;
                printStream.println("Current time is greater than " + str);
                return false;
            }
            PrintStream printStream2 = System.out;
            printStream2.println("Current time is less than " + str);
            return true;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.location_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ((RelativeLayout) dialog.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                MainActivity.this.startActivityForResult(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"), 1001);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override 
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 1 || iArr.length <= 0) {
            return;
        }
        if (iArr[0] == 0) {
            if (isLocationEnabled(this)) {
                if (this.prayerSharedPreference.getIsFirstTimeRunPref()) {
                    this.prayerSharedPreference.setIsFirstTimeRunPref(false);
                    this.isChangeConfigClick = true;
                    startActivity(new Intent(getApplicationContext(), PrayerTimeConfigureActivity.class));
                    return;
                }
                return;
            }
            showDialog();
            return;
        }
        btnAllPrayer.setVisibility(View.GONE);
        btnlocate.setVisibility(View.GONE);
        Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show();

    }

    public static boolean isLocationEnabled(Context context) {
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "location_mode") != 0;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
                Intent intent5 = new Intent(getApplicationContext(), Aboutdev.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent5);
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
                    Intent intent6 = new Intent("android.intent.action.SEND");
                    intent6.setType("text/plain");
                    intent6.putExtra("android.intent.extra.TEXT", "Hi! I'm using a great Islamic application. Check it out:http://play.google.com/store/apps/details?id=" + getPackageName());
                    intent6.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(Intent.createChooser(intent6, "Share with Friends"));
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


    //In-App Udpates Implementation (2nd part)------------------------------------------------------




}
