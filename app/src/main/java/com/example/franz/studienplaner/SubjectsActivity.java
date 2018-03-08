package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SubjectsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

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
        hideAll();
        fillSpinner(getSubject());
    }

    private String getSubject() {
        String subject = "";
        Bundle extras = getIntent().getExtras();
        if(extras.getInt("subject") == 0x7f080026) {
            subject = "Medieninformatik";
        } else if(extras.getInt("subject") == 0x7f080025) {
            subject = "Informationswissenschaften";
        }
        return subject;
    }

    private void setupUI() {
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
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

    private void fillSpinner(String i) {
        if(i.equals("Medieninformatik")) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.mei_module, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        } else if(i.equals("Informationswissenschaften")) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.inf_module, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }

    private void showAll() {
        position1.setVisibility(View.VISIBLE);
        position2.setVisibility(View.VISIBLE);
        position3.setVisibility(View.VISIBLE);
        checkbox1.setVisibility(View.VISIBLE);
        checkbox2.setVisibility(View.VISIBLE);
        checkbox3.setVisibility(View.VISIBLE);
        name1.setVisibility(View.VISIBLE);
        name2.setVisibility(View.VISIBLE);
        name3.setVisibility(View.VISIBLE);
        note1.setVisibility(View.VISIBLE);
        note2.setVisibility(View.VISIBLE);
        note3.setVisibility(View.VISIBLE);
    }

    private void hideAll() {
        position1.setVisibility(View.INVISIBLE);
        position2.setVisibility(View.INVISIBLE);
        position3.setVisibility(View.INVISIBLE);
        checkbox1.setVisibility(View.INVISIBLE);
        checkbox2.setVisibility(View.INVISIBLE);
        checkbox3.setVisibility(View.INVISIBLE);
        name1.setVisibility(View.INVISIBLE);
        name2.setVisibility(View.INVISIBLE);
        name3.setVisibility(View.INVISIBLE);
        note1.setVisibility(View.INVISIBLE);
        note2.setVisibility(View.INVISIBLE);
        note3.setVisibility(View.INVISIBLE);
    }

    private void hideLast() {
        position3.setVisibility(View.INVISIBLE);
        checkbox3.setVisibility(View.INVISIBLE);
        name3.setVisibility(View.INVISIBLE);
        note3.setVisibility(View.INVISIBLE);
    }

    private void fillInformation(String k) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getItemAtPosition(i).toString()){
            case "MEI-M01":
            case "MEI-M02":
            case "INF-M01":
            case "INF-M05":
            case "INF-M07":
                showAll();
                hideLast();
                //fillInformation(adapterView.getItemAtPosition(i).toString());
                break;
            default:
                showAll();
                //fillInformation(adapterView.getItemAtPosition(i).toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
