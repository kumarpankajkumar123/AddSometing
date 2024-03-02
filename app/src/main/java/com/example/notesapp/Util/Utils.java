package com.example.notesapp.Util;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;

public class Utils {

    public static void ToastMessage(Context context,String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

  public static CollectionReference getCollectionReferenceForAddingDataNotes(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
       return FirebaseFirestore.getInstance().collection("notes").document(currentUser.getUid()).collection("myNotes");
    }

   public static String Timestamp(Timestamp timestamp){
       return new SimpleDateFormat("hh:mm a").format(timestamp.toDate());

    }
}
