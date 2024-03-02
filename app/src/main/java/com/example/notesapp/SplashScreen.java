package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notesapp.UserData.CurrentDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textApp);
        mAuth = FirebaseAuth.getInstance();

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animationsplash);

        imageView.startAnimation(animation);
        textView.startAnimation(animation);

        new Handler().postDelayed((Runnable) () -> {
            FirebaseUser firebaseUser =mAuth.getCurrentUser();
            if(firebaseUser != null){

                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
            else{
                startActivity(new Intent(SplashScreen.this, Login.class));
            }
            finish();
        },2000);
    }
}