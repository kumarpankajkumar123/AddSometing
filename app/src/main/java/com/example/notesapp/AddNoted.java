package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.UserData.Notes;
import com.example.notesapp.Util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AddNoted extends AppCompatActivity {

    ImageView imageView;
    EditText title,description;
    Timestamp timestamp;
    TextView pageTitle,deleteNode;
    FirebaseFirestore fireStore;
    String setTitle,content,docId;

    boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noted);

        imageView = findViewById(R.id.addImage);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        pageTitle = findViewById(R.id.pageTitle);
        deleteNode = findViewById(R.id.deleteNode);

        fireStore = FirebaseFirestore.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNotes();
            }
        });

        setTitle = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("des");
        docId = getIntent().getStringExtra("docId");

        if(docId != null && !docId.isEmpty()){
            isEdit = true;
        }
        title.setText(setTitle);
        description.setText(content);
        if(isEdit){
            pageTitle.setText("Edit your Notes");
            deleteNode.setVisibility(View.VISIBLE);
        }
        deleteNode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFromFirebase();
            }
        });
    }
    void deleteFromFirebase(){
        DocumentReference documentReference;
        documentReference = Utils.getCollectionReferenceForAddingDataNotes().document(docId);
        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.ToastMessage(AddNoted.this,"task is remove successful");
                    finish();
                }
                else{
                    Utils.ToastMessage(AddNoted.this,"deletion failed ");
                }
            }
        });
    }

    void saveNotes(){
        String tl = title.getText().toString();
        String des = description.getText().toString();
        timestamp = Timestamp.now();

        if(tl.isEmpty() || tl == null){
            title.setError("this is required");
            return;
        }
        else{
            saveNotesClass(tl,des,timestamp);
        }
    }
    void saveNotesClass(String til,String des,Timestamp timestamp){
        Notes notes = new Notes(til,des,timestamp);
        notes.setTitle(til);
        notes.setDescription(des);
        notes.setTimestamp(timestamp);
        savesToFirebase(notes);
    }
    public  void savesToFirebase(Notes notes){

        DocumentReference documentReference;
        if(isEdit){
            documentReference = Utils.getCollectionReferenceForAddingDataNotes().document(docId);
        }
        else{
            documentReference = Utils.getCollectionReferenceForAddingDataNotes().document();
        }

        documentReference.set(notes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.ToastMessage(AddNoted.this,"task is successful");
                    finish();
                }
                else{
                    Utils.ToastMessage(AddNoted.this,"task not successful");
                }
            }
        });
    }
}