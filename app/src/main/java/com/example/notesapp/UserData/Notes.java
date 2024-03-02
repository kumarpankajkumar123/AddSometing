package com.example.notesapp.UserData;

import com.google.firebase.Timestamp;

public class Notes {
    String title;
    String description;
    Timestamp timestamp;
    public Notes() {
    }

    public Notes(String title, String description,Timestamp timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
