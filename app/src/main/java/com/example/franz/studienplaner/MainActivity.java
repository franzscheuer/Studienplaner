package com.example.franz.studienplaner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {
        Button modulübersicht = findViewById(R.id.button_module);
        modulübersicht.setOnClickListener(this);
        Button notenübersicht = findViewById(R.id.button_noten);
        notenübersicht.setOnClickListener(this);
        Button fortschrittsanzeige = findViewById(R.id.button_fortschritt);
        fortschrittsanzeige.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.button_module:
                startActivity(new Intent(MainActivity.this, ModulsActivity.class));
                break;
            case R.id.button_noten:
                startActivity(new Intent(MainActivity.this, GradesActivity.class));
                break;
            case R.id.button_fortschritt:
                startActivity(new Intent(MainActivity.this, ProgressActivity.class));
                break;
            default:
                break;
        }
    }
}
