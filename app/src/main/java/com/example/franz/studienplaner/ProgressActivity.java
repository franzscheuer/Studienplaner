package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        getSupportActionBar().setTitle("Fortschrittsanzeige");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
    }

    private void setupUI() {

    }

    @Override
    public void onClick(View view) {


    }
}
