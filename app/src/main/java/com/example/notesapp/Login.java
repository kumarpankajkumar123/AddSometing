package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.Util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText loginEml,loginPwd;
    MaterialButton loginButton;
    TextView signUpText,resetPassword;

    String mail,newPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        loginUn = findViewById(R.id.loginUn);
        loginEml = findViewById(R.id.loginEml);
        loginPwd = findViewById(R.id.loginPwd);
        loginButton = findViewById(R.id.loginButton);
        signUpText = findViewById(R.id.signUpText);
        resetPassword = findViewById(R.id.resetPassword);
        mAuth = FirebaseAuth.getInstance();

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,SignUpnNew.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpLogin();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail = loginEml.getText().toString().trim();
                createNewPassword(mail);
//                newPassword = loginPwd.getText().toString().trim();
//                setNewPassword(newPassword);
            }
        });


    }

//    void setNewPassword(String newPassword){
//        mAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    // Password updated successfully
//                    Toast.makeText(Login.this, "Password updated successfully.", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Handle the error
//                    Toast.makeText(Login.this, "Failed to update password.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }
    void createNewPassword(String mail){
        if(mail == null || mail.isEmpty()){
            loginEml.setError("fill email address");
        }
        else{
             mAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  if(task.isSuccessful()){
                      Utils.ToastMessage(Login.this,"check your mail and reset password");
                  }
                  else{
                      Utils.ToastMessage(Login.this,"reset password failed");
                  }
                }
            });
        }
    }
    void setUpLogin(){
        String eml = loginEml.getText().toString();
        String pass = loginPwd.getText().toString();

      boolean ans = validDetail(eml,pass);

      if(!ans){
          return;
      }
        checkInFirebase(eml,pass);
    }

    void checkInFirebase(String eml,String pss){

        mAuth.signInWithEmailAndPassword(eml,pss).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   if(mAuth.getCurrentUser().isEmailVerified()){
                         startActivity(new Intent(Login.this, MainActivity.class));
                         finish();
                   }
                   else{
                       Toast.makeText(Login.this, "don't verify email and password", Toast.LENGTH_SHORT).show();
                   }
                }
                else{
                    Toast.makeText(Login.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    boolean validDetail( String email, String pass) {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEml.setError("fill required email address");
            return false;
        }
        if (pass.length() < 6) {
            loginPwd.setError("the length very small");
            return false;
        }
        return true;
    }
}