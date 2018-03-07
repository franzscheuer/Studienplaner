package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SubjectsActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    TextView position1, position2, position3;
    CheckBox checkbox1, checkbox2, checkbox3;
    EditText name1, name2, name3;
    EditText note1, note2, note3;

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
        spinner = findViewById(R.id.spinner);
        position1 = findViewById(R.id.position1);
        position2 = findViewById(R.id.position2);
        position3 = findViewById(R.id.position3);
        checkbox1 = findViewById(R.id.checkBox1);
        checkbox2 = findViewById(R.id.checkBox2);
        checkbox3 = findViewById(R.id.checkBox3);
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        note1 = findViewById(R.id.note1);
        note2 = findViewById(R.id.note2);
        note3 = findViewById(R.id.note3);
    }

    @Override
    public void onClick(View view) {

    }
}
