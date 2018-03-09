package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    TextView missingCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getSupportActionBar().setTitle("Fortschrittsanzeige");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
        int progress = getProgress();
        updateProgressBar(progress);
        setMissingCourses();
    }

    private void setupUI() {
        progressBar = findViewById(R.id.progressBar);
        missingCourses = findViewById(R.id.textView2);
    }

    private void updateProgressBar(int value) {
        progressBar.setProgress(value);
    }

    private int getProgress() {
        // int progress;
        //calculate progress
        // return progress;
        return 75;
    }

    private void setMissingCourses() {
        //missingCourses.setText();
    }

    @Override
    public void onClick(View view) {

    }
}
