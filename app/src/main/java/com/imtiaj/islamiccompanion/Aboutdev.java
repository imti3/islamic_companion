package com.imtiaj.islamiccompanion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.imtiaj.islamiccompanion.Appcompany.Privacy_Policy_activity;

public class Aboutdev extends AppCompatActivity {


    ImageButton btnwhatsapp,btnfb,btnmail,btncall,btngroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutdev);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnwhatsapp=(ImageButton) findViewById(R.id.wapp);
        btnfb=(ImageButton) findViewById(R.id.fb);
        btnmail=(ImageButton) findViewById(R.id.mail);
        btncall=(ImageButton) findViewById(R.id.call);
        btngroup=(ImageButton) findViewById(R.id.group);






        btnwhatsapp.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startSupportChat ();
            }
        } );


        btnfb.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                goToFacebook("100008183731633");
            }
        } );
        btnmail.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        } );

        btncall.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                dialPhoneNumber("+8801819511469");
            }
        } );


        btngroup.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                getOpenFacebookIntent("1441911099740150");
            }
        } );


    }

    public void goToFacebook(String id) {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent facebookPage = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/"+id));
            startActivity(facebookPage);
        } catch (Exception e) {
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://facebook.com/"+id));
            startActivity(launchBrowser);
        }
    }

    private void startSupportChat() {
        try {
            String headerReceiver = "";// Replace with your message.
            String bodyMessageFormal = "";// Replace with your message.
            String whatsappContain = headerReceiver + bodyMessageFormal;
            String trimToNumner = "+8801819511469"; //10 digit number
            Intent intent = new Intent ( Intent.ACTION_VIEW );
            intent.setData ( Uri.parse ( "https://wa.me/" + trimToNumner + "/?text=" + "" ) );
            startActivity ( intent );
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    private void sendEmail() {
        String emailTo="faar5489@gmail.com";
        String emailSubject="Islamic Companion App";
        String emailMessage="আসসালামুআলাইকুম ";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose Mail App"));
    }
    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void getOpenFacebookIntent(String id) {
        try {
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            // Change the URI to open a Facebook group
            Intent facebookgroup= new Intent(Intent.ACTION_VIEW, Uri.parse("fb://group/"+id));
            startActivity(facebookgroup);
        } catch (Exception e) {
            // Fallback to opening the group in the browser
            Intent browser= new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/"+id));
            startActivity(browser);
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