package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notesapp.UserData.CurrentDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignUpnNew extends AppCompatActivity {

    ImageView backImage;
    MaterialButton materialButton;
    EditText userName, emailAddress, password, confirmPass;
    FirebaseAuth mAuth;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upn_new);
        userName = findViewById(R.id.userName);
        emailAddress = findViewById(R.id.emailAddress);
        password = findViewById(R.id.Password);
        confirmPass = findViewById(R.id.confirmPassword);

        mAuth = FirebaseAuth.getInstance();
        backImage = (ImageView) findViewById(R.id.backImage);
        materialButton = (MaterialButton) findViewById(R.id.signUpButton);
        backImage.setOnClickListener(view -> {
            startActivity(new Intent(SignUpnNew.this, Login.class));
        });

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpDetail();
            }
        });

    }

    void signUpDetail() {
        String username = userName.getText().toString().trim();
        String email = emailAddress.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cp = confirmPass.getText().toString().trim();

        boolean bool = validDetail( username,email, pass, cp);

        if (bool == false)
            Toast.makeText(this, "please provide all details correctly", Toast.LENGTH_SHORT).show();
        else {
            createDataInFireBase(email, pass);
        }
    }

    void createDataInFireBase(String email, String pass) {

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpnNew.this,"successful account created", Toast.LENGTH_SHORT).show();
//                    String useId = mAuth.getUid().toString();
//                    CurrentDetails cd= new CurrentDetails(mAuth.getUid());
//                    cd.setUserId(useId);

                    mAuth.getCurrentUser().sendEmailVerification();
                    mAuth.signOut();
                    finish();
//                    startActivity(new Intent(SignUpnNew.this, Login.class));
                }
                else{
                    Toast.makeText(SignUpnNew.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    boolean validDetail( String user,String email, String pass, String cpass) {

        CurrentDetails cd = new CurrentDetails(user,email,pass,cpass);
        cd.setUsername(user);
        cd.setEmail(email);
        cd.setPassword(pass);
        cd.setConfirmPassword(cpass);

        if (user.isEmpty() || email.isEmpty() || pass.isEmpty() || cpass.isEmpty()) {
            Toast.makeText(SignUpnNew.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailAddress.setError("fill required email address");
            return false;
        }
        if (pass.length() < 6) {
            password.setError("the length very small");
            return false;
        }
        if (!pass.equals(cpass)) {
            confirmPass.setError("password and confirm password does not matched");
            return false;
        }
        return true;
    }
}