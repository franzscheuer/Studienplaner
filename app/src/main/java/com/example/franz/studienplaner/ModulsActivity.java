package com.example.franz.studienplaner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ModulsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moduls);
        getSupportActionBar().setTitle("Module");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
    }

    private void setupUI() {
        Button medieninformatik = findViewById(R.id.button_mei);
        medieninformatik.setTag("mei");
        medieninformatik.setOnClickListener(this);
        Button informationswissenschaft = findViewById(R.id.button_inf);
        informationswissenschaft.setTag("inf");
        informationswissenschaft.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(ModulsActivity.this, SubjectsActivity.class).putExtra("subject", String.valueOf(view.getTag())));
    }
}
