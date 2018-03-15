package com.example.franz.studienplaner;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class SubjectsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private TextView position1, position2, position3;
    private CheckBox checkbox1, checkbox2, checkbox3;
    private EditText name1, name2, name3;
    private EditText note1, note2, note3;
    private Button button;
    private boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        getSupportActionBar().setTitle(getSubject());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupUI();
        hideAll();
        disableEditMode();
        fillSpinner(getSubject());
    }

    private String getSubject() {
        String subject = "";
        Bundle extras = getIntent().getExtras();
        if(extras.getString("subject").equals("mei")) {
            subject = "Medieninformatik";
        } else if(extras.getString("subject").equals("inf")) {
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
        button = findViewById(R.id.addButton);
        button.setOnClickListener(this);
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

    private void fillModulNames(String m) {
        String modulPosition1 = m + ".1";
        String modulPosition2 = m + ".2";
        String modulPosition3 = m + ".3";
        position1.setText(modulPosition1);
        position2.setText(modulPosition2);
        position3.setText(modulPosition3);
    }

    private void fillInformation(String k) {
        // get data from database and fill in
    }

    private ContentValues getData() {
        ContentValues cv = new ContentValues();
        cv.put("name1", name1.getText().toString());
        cv.put("checkbox1", checkbox1.isChecked());
        cv.put("grade1", note1.getText().toString());
        cv.put("name2", name1.getText().toString());
        cv.put("checkbox2", checkbox1.isChecked());
        cv.put("grade2", note1.getText().toString());
        cv.put("name3", name1.getText().toString());
        cv.put("checkbox3", checkbox1.isChecked());
        cv.put("grade3", note1.getText().toString());
        return cv;
    }

    private void safeDataInDatabase() {
        // getData();
        // safe data into database
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        switch(item){
            case "MEI-M01":
            case "MEI-M02":
            case "INF-M01":
            case "INF-M05":
            case "INF-M07":
                showAll();
                hideLast();
                fillModulNames(item);
                fillInformation(adapterView.getItemAtPosition(i).toString());
                break;
            default:
                showAll();
                fillModulNames(item);
                fillInformation(adapterView.getItemAtPosition(i).toString());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if(!editMode) {
            button.setText(R.string.speichern);
            editMode = true;
            activateEditMode();
        } else {
            safeDataInDatabase();
            button.setText(R.string.bearbeiten);
            editMode = false;
            disableEditMode();
            Toast.makeText(this, "Speichern erfolgreich", Toast.LENGTH_LONG).show();
        }
    }

    private void activateEditMode() {
        name1.setEnabled(true);
        name2.setEnabled(true);
        name3.setEnabled(true);
        note1.setEnabled(true);
        note2.setEnabled(true);
        note3.setEnabled(true);
        checkbox1.setEnabled(true);
        checkbox2.setEnabled(true);
        checkbox3.setEnabled(true);
    }

    private void disableEditMode() {
        name1.setEnabled(false);
        name2.setEnabled(false);
        name3.setEnabled(false);
        note1.setEnabled(false);
        note2.setEnabled(false);
        note3.setEnabled(false);
        checkbox1.setEnabled(false);
        checkbox2.setEnabled(false);
        checkbox3.setEnabled(false);
    }
}
