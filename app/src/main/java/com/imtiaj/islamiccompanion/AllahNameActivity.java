package com.imtiaj.islamiccompanion;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.imtiaj.islamiccompanion.Adapter.Allah99NameAdapter;
import com.imtiaj.islamiccompanion.Appcompany.Privacy_Policy_activity;
import com.imtiaj.islamiccompanion.DatabaseHelper.DatabaseAccess;
import com.imtiaj.islamiccompanion.Model.AllahNameModel;
import com.imtiaj.islamiccompanion.OnItemClickListener.OnItemClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;


public class AllahNameActivity extends AppCompatActivity implements OnItemClickListener {
    Allah99NameAdapter allah99NameAdapter;

    BottomNavigationView bottomNavigationView;
    List<AllahNameModel> allahNameModelList = new ArrayList();
    DatabaseAccess databaseAccess;
    RelativeLayout layout;
    RecyclerView rv99AllahName;

    @Override 
    public void OnClick(View view, int i) {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        setContentView(R.layout.activity_99_allah_name);

        bottomNavigationView=findViewById(R.id.bottom_menu);



        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.databaseAccess = DatabaseAccess.getInstance(this);
        this.rv99AllahName = (RecyclerView) findViewById(R.id.rv99AllahName);
        this.databaseAccess.open();
        this.allahNameModelList = this.databaseAccess.getDua99AllhaName();
        this.databaseAccess.close();
        this.rv99AllahName.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.rv99AllahName.setItemAnimator(new DefaultItemAnimator());
        Allah99NameAdapter allah99NameAdapter = new Allah99NameAdapter(this, this.allahNameModelList, this);
        this.allah99NameAdapter = allah99NameAdapter;
        this.rv99AllahName.setAdapter(allah99NameAdapter);




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.homeb:
                            Intent intent4 = new Intent(AllahNameActivity.this.getApplicationContext(), MainActivity.class);
                        startActivity(intent4);
                        break;


                    case R.id.namajb:
                        Intent intent = new Intent(AllahNameActivity.this.getApplicationContext(), TimingC.class);
                        startActivity(intent);
                        break;

                    case R.id.duab:
                        Intent intent1 = new Intent(AllahNameActivity.this.getApplicationContext(), DuaCategoryActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.tasibhb:
                        Intent intent2 = new Intent(AllahNameActivity.this.getApplicationContext(), TasbihCounterActivity.class);
                        startActivity(intent2);
                        break;

                }








                return true;
            }
        });






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
                    intent5.putExtra("android.intent.extra.TEXT", "Hi! I'm using a great Islamic application.. Check it out:http://play.google.com/store/apps/details?id=" + getPackageName());
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
