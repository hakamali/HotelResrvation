package com.app.reservation.hotel.hotelreservationproject;

import android.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE = 234;
    TextView tvAppNameSplash, tvDescription;
    Animation btntotop, btntotopwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        tvAppNameSplash = (TextView) findViewById(R.id.tvAppNameSplash);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        btntotop = AnimationUtils.loadAnimation(this, R.anim.btntotop);
        btntotopwo = AnimationUtils.loadAnimation(this, R.anim.btntotopwo);

        tvAppNameSplash.startAnimation(btntotop);
        tvDescription.startAnimation(btntotopwo);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, MY_PERMISSION_REQUEST_CODE);
        }

        Thread t = new Thread() {
            @Override
            public void run() {

                Intent start = null;

                try {

                    sleep(2500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    SharedPreferences preferences = getSharedPreferences("userdata", MODE_PRIVATE);
                    String email = preferences.getString("email" , "n/a");


                    if( email.equals("n/a"))
                    {
                        start = new Intent(SplashScreen.this, LoginActivity.class);

                    }
                    else {
                        start = new Intent(SplashScreen.this, MainPageActivity.class);
                    }

                    startActivity(start);
                    finish();
                }
            }
        };

        t.start();


    }
}
