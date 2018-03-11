package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ProgressBar;

public class ProgressActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView missingCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getSupportActionBar().setTitle("Fortschrittsanzeige");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
        updateProgressBar();
        setMissingCourses();
    }

    private void setupUI() {
        progressBar = findViewById(R.id.progressBar);
        missingCourses = findViewById(R.id.listView);
    }

    private void updateProgressBar() {
        int value = 75;
        progressBar.setProgress(value);
    }

    private void setMissingCourses() {
        // get all not finished courses from database
    }
}
