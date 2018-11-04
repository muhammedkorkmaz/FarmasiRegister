package com.mobilonetech.farmasiuyeol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Start home activity
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        if (getIntent().getExtras() != null) {
            String url = getIntent().getStringExtra("URL");
            if (url != null && !url.equals("")) {
                String title = getIntent().getStringExtra("title");
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                startActivity(intent);
            } else {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        // close splash activity
        finish();
    }
}
