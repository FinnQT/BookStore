package com.example.do_an_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Browser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }

        }, 500);
    }

    private void nextActivity() {
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent= new Intent(this,login.class);
            startActivity(intent);
        }else {
            Intent intent= new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}