package com.imtiaj.islamiccompanion;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.imtiaj.islamiccompanion.Appcompany.Privacy_Policy_activity;
import com.imtiaj.islamiccompanion.PreferenceUtil.AlarmReceiver;
import com.imtiaj.islamiccompanion.PreferenceUtil.NotificationPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NotificationActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    LinearLayout btnSetTime;
    String format = "";
    RelativeLayout layout;
    NotificationPref notificationPref;
    TimePicker timePiker;
    TextView tvTime;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_notification);


        bottomNavigationView=findViewById(R.id.bottom_menu);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.tvTime = (TextView) findViewById(R.id.tvTime);
        this.timePiker = (TimePicker) findViewById(R.id.timePiker);
        this.btnSetTime = (LinearLayout) findViewById(R.id.btnSetTime);
        NotificationPref notificationPref = new NotificationPref(this);
        this.notificationPref = notificationPref;
        setTime(notificationPref.getTime());








        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.homeb:
                        Intent intent4 = new Intent(NotificationActivity.this.getApplicationContext(), MainActivity.class);
                        startActivity(intent4);
                        break;


                    case R.id.namajb:
                        Intent intent = new Intent(NotificationActivity.this.getApplicationContext(), TimingC.class);
                        startActivity(intent);
                        break;

                    case R.id.duab:
                        Intent intent1 = new Intent(NotificationActivity.this.getApplicationContext(), DuaCategoryActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.tasibhb:
                        Intent intent2 = new Intent(NotificationActivity.this.getApplicationContext(), TasbihCounterActivity.class);
                        startActivity(intent2);
                        break;

                }








                return true;
            }
        });















        this.btnSetTime.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                int intValue = NotificationActivity.this.timePiker.getCurrentHour().intValue();
                int intValue2 = NotificationActivity.this.timePiker.getCurrentMinute().intValue();
                NotificationActivity.this.showTime(intValue, intValue2);
                long selectedTime = NotificationActivity.this.hmToLong(intValue, intValue2);
                NotificationActivity.this.notificationPref.setTime(NotificationActivity.this.hmToLong(intValue, intValue2));
                Toast.makeText(NotificationActivity.this, "Set time successfully", Toast.LENGTH_SHORT).show();
                scheduleAlarm(selectedTime);
            }
        });
    }

    public void setTime(long j) {
        Date date = new Date(j);
        showTime(date.getHours(), date.getMinutes());
        this.timePiker.setCurrentHour(Integer.valueOf(date.getHours()));
        this.timePiker.setCurrentMinute(Integer.valueOf(date.getMinutes()));
    }

    public long hmToLong(int i, int i2) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("H:mm");
        try {
            return simpleDateFormat.parse(i + ":" + i2).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public void showTime(int i, int i2) {
        if (i == 0) {
            i += 12;
            this.format = "AM";
        } else if (i == 12) {
            this.format = "PM";
        } else if (i > 12) {
            i -= 12;
            this.format = "PM";
        } else {
            this.format = "AM";
        }
        String valueOf = String.valueOf(i);
        String valueOf2 = String.valueOf(i2);
        if (i < 10) {
            valueOf = "0" + i;
        }
        if (i2 < 10) {
            valueOf2 = "0" + i2;
        }
        TextView textView = this.tvTime;
        StringBuilder sb = new StringBuilder();
        sb.append(valueOf);
        sb.append(" : ");
        sb.append(valueOf2);
        sb.append(" ");
        sb.append(this.format);
        textView.setText(sb);
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        finish();
     
    }

    private void scheduleAlarm(long alarmTime) {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmTime, AlarmManager.INTERVAL_DAY, pendingIntent);
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
