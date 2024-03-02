package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.notesapp.Adaptor.myAdaptor;
import com.example.notesapp.UserData.Notes;
import com.example.notesapp.Util.Utils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView menuImageView;
    FirebaseAuth mAuth;
    myAdaptor adaptor;
    FloatingActionButton floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        floatingButton = findViewById(R.id.floatingButton);
        recyclerView = findViewById(R.id.recyclerView);
        menuImageView = findViewById(R.id.menuImage);

        floatingButton.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddNoted.class));
        });

        menuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });

        setUpRecyclerView();
    }

    void showMenu() {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,menuImageView);
        popupMenu.getMenu().add("Logout");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle() == "Logout"){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this, Login.class));
                    finish();
                    return true;
                }
                return false;
            }
        });
    }

    void setUpRecyclerView() {

        Query query = Utils.getCollectionReferenceForAddingDataNotes().orderBy("timestamp", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Notes> options = new FirestoreRecyclerOptions.Builder<Notes>()
                .setQuery(query, Notes.class).build();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adaptor = new myAdaptor(options, this);
        recyclerView.setAdapter(adaptor);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptor.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adaptor.notifyDataSetChanged();
    }


}