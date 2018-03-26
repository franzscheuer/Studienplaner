package com.example.franz.studienplaner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ModulsActivity extends AppCompatActivity implements View.OnClickListener {

    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moduls);
        setupActionBar();
        setupUI();
        getUserID();
    }

    private void setupActionBar() {
        getSupportActionBar().setTitle("Module");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUI() {
        Button medieninformatik = findViewById(R.id.button_mei);
        medieninformatik.setTag("mei");
        medieninformatik.setOnClickListener(this);
        Button informationswissenschaft = findViewById(R.id.button_inf);
        informationswissenschaft.setTag("inf");
        informationswissenschaft.setOnClickListener(this);
    }

    private void getUserID() {
        userID = getIntent().getExtras().getInt("userID");
    }

    @Override
    public void onClick(View view) {
        Bundle extras = new Bundle();
        extras.putString("subject", String.valueOf(view.getTag()));
        extras.putInt("userID", userID);
        Intent intent = new Intent(ModulsActivity.this, SubjectsActivity.class).putExtras(extras);
        startActivity(intent);
    }
}
