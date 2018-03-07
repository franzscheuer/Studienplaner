package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class GradesActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner1, spinner2;
    TextView grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        getSupportActionBar().setTitle("Notenübersicht");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
    }

    private void setupUI() {
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        grades = findViewById(R.id.textView);
    }

    @Override
    public void onClick(View view) {

    }
}
