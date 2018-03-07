package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SubjectsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        getSupportActionBar().setTitle(getSubject());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
    }

    private String getSubject() {
        String subject = "";
        Bundle extras = getIntent().getExtras();
        if(extras.getInt("subject") == 0x7f070026) {
            subject = "Medieninformatik";
        } else if(extras.getInt("subject") == 0x7f070025) {
            subject = "Informationswissenschaften";
        }
        return subject;
    }

    private void setupUI() {

    }

    @Override
    public void onClick(View view) {


    }
}
