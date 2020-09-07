package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser currentUser=firebaseAuth.getCurrentUser();

        Handler handler=new Handler();

        //current user object=It is currently logged in user, if not logged in ,this is null

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(currentUser!=null){

                    //user is already logged in
                    Intent homeIntent=new Intent(SplashActivity.this,HomeActivity.class);
                    startActivity(homeIntent);
                }
                else
                {
                    //user is not logged in,then show login activity
                    Intent loginIntent=new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(loginIntent);

                }


            }
        },1500);  //1.5sec

    }
}
