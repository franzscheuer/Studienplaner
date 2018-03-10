package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {

    ProgressBar progressBar;
    TextView missingCourses;

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
        missingCourses = findViewById(R.id.textView2);
    }

    private void updateProgressBar() {
        int value = 75;
        progressBar.setProgress(value);
    }

    private void setMissingCourses() {
        // missingCourses.setText();
    }
}
