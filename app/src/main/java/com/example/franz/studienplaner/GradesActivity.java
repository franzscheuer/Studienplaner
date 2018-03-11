package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class GradesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner1, spinner2;
    TextView grades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        getSupportActionBar().setTitle("Notenübersicht");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
        fillFirstSpinner();
        emptySecondSpinner();
        disableSecondSpinner();
    }

    private void setupUI() {
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        grades = findViewById(R.id.textView);
    }

    private void disableSecondSpinner() {
        spinner2.setEnabled(false);
    }

    private void enableSecondSpinner() {
        spinner2.setEnabled(true);
    }

    private void fillFirstSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.noten_auswahl, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
    }

    private void fillSecondSpinner(String k) {
        if(k.equals("mei")) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mei_module, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter);
        } else if(k.equals("inf")) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.inf_module, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner2.setAdapter(adapter);
        }
    }

    private void emptySecondSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.empty_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
    }

    private void fillTextView(String g) {
        // get all grades for chosen modul
        grades.setText(g);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        String input = adapterView.getItemAtPosition(i).toString();
        if (spinner.getId() == R.id.spinner1) {
            switch (input) {
                case "Gesamtübersicht":
                    emptySecondSpinner();
                    disableSecondSpinner();
                    break;
                case "Medieninformatik":
                    enableSecondSpinner();
                    fillSecondSpinner("mei");
                    break;
                case "Informationswissenschaften":
                    enableSecondSpinner();
                    fillSecondSpinner("inf");
                    break;
            }
        } else if (spinner.getId() == R.id.spinner2) {
            fillTextView(input);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
