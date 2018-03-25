package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;


public class GradesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner1, spinner2;
    TextView grades;
    private int userID;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);
        setupActionBar();
        setupUI();
        getUserID();
        setupDatabase();
        setupSpinners();
    }

    private void setupActionBar() {
        getSupportActionBar().setTitle("Notenübersicht");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUI() {
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        grades = findViewById(R.id.textViewGrades);
    }

    private void getUserID() {
        userID = getIntent().getExtras().getInt("userID");
    }

    private void setupDatabase() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("user" + userID);
    }

    private void setupSpinners() {
        fillFirstSpinner();
        emptySecondSpinner();
        disableSecondSpinner();
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

    private void fillTextView() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String subject = spinner1.getSelectedItem().toString();
                String modul = spinner2.getSelectedItem().toString();
                double grade1 = 0, grade2 = 0, grade3 = 0, modul_grade = 0;
                if (!subject.equals("Gesamtübersicht")) {
                    if (!modul.equals("MEI-M01") && !modul.equals("MEI-M02") && !modul.equals("INF-M01") && !modul.equals("INF-M05") && !modul.equals("INF-M07")) {
                        if (!dataSnapshot.child(subject).child(modul).child(modul + "1").child("note").getValue().toString().isEmpty()) {
                            grade1 = Double.parseDouble(dataSnapshot.child(subject).child(modul).child(modul + "1").child("note").getValue().toString());
                        }
                        if (!dataSnapshot.child(subject).child(modul).child(modul + "2").child("note").getValue().toString().isEmpty()) {
                            grade2 = Double.parseDouble(dataSnapshot.child(subject).child(modul).child(modul + "2").child("note").getValue().toString());
                        }
                        if (!dataSnapshot.child(subject).child(modul).child(modul + "3").child("note").getValue().toString().isEmpty()) {
                            grade3 = Double.parseDouble(dataSnapshot.child(subject).child(modul).child(modul + "3").child("note").getValue().toString());
                        }
                    } else {
                        if (!dataSnapshot.child(subject).child(modul).child(modul + "1").child("note").getValue().toString().isEmpty()) {
                            grade1 = Double.parseDouble(dataSnapshot.child(subject).child(modul).child(modul + "1").child("note").getValue().toString());
                        }
                        if (!dataSnapshot.child(subject).child(modul).child(modul + "2").child("note").getValue().toString().isEmpty()) {
                            grade2 = Double.parseDouble(dataSnapshot.child(subject).child(modul).child(modul + "2").child("note").getValue().toString());
                        }
                    }
                    switch (modul) {
                        case "MEI-M01":
                            modul_grade = calculateM1(grade1, grade2);
                            break;
                        case "MEI-M02":
                            modul_grade = calculateM2(grade1, grade2);
                            break;
                        case "MEI-M03":
                            modul_grade = calculateM3(grade1, grade2, grade3);
                            break;
                        case "MEI-M04":
                            modul_grade = calculateM4(grade1, grade2, grade3);
                            break;
                        case "MEI-M05":
                            modul_grade = calculateM5(grade1, grade2, grade3);
                            break;
                        case "MEI-M10":
                            modul_grade = calculateM10(grade1, grade2, grade3);
                            break;
                        case "INF-M01":
                            modul_grade = calculateI1(grade1, grade2);
                            break;
                        case "INF-M02":
                            modul_grade = calculateI2(grade1, grade2, grade3);
                            break;
                        case "INF-M04":
                            modul_grade = calculateI4(grade1, grade2, grade3);
                            break;
                        case "INF-M05":
                            modul_grade = calculateI5(grade1, grade2);
                            break;
                        case "INF-M06":
                            modul_grade = calculateI6(grade1, grade2, grade3);
                            break;
                        case "INF-M07":
                            modul_grade = calculateI7(grade1, grade2);
                            break;
                    }
                    String text;
                    if (modul.equals("MEI-M01") || modul.equals("MEI-M02") || modul.equals("INF-M01") || modul.equals("INF-M05") || modul.equals("INF-M07")) {
                        text = modul + ".1: " + grade1 + "\n" + "\n" +
                                modul + ".2: " + grade2 + "\n" + "\n" +
                                "Modulnote: " + modul_grade;
                    } else {
                        text = modul + ".1: " + grade1 + "\n" + "\n" +
                                modul + ".2: " + grade2 + "\n" + "\n" +
                                modul + ".3: " + grade3 + "\n" + "\n" +
                                "Modulnote: " + modul_grade;
                    }
                    grades.setText(text);
                } else {
                    String text;
                    double mei, inf;
                    HashMap<String, Double> list = new HashMap<>();
                    String[] mei_moduls = getResources().getStringArray(R.array.mei_module);
                    String[] inf_moduls = getResources().getStringArray(R.array.inf_module);
                    for(int i = 0; i < mei_moduls.length; i++) {
                        if (!mei_moduls[i].equals("MEI-M01") && !mei_moduls[i].equals("MEI-M02")) {
                            if (!dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("note").getValue().toString().isEmpty()) {
                                grade1 = Double.parseDouble(dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("note").getValue().toString());
                            }
                            if (!dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("note").getValue().toString().isEmpty()) {
                                grade2 = Double.parseDouble(dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("note").getValue().toString());
                            }
                            if (!dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").child("note").getValue().toString().isEmpty()) {
                                grade3 = Double.parseDouble(dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").child("note").getValue().toString());
                            }
                        } else {
                            if (!dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("note").getValue().toString().isEmpty()) {
                                grade1 = Double.parseDouble(dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("note").getValue().toString());
                            }
                            if (!dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("note").getValue().toString().isEmpty()) {
                                grade2 = Double.parseDouble(dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("note").getValue().toString());
                            }
                        }
                        switch (mei_moduls[i]) {
                            case "MEI-M01":
                                modul_grade = calculateM1(grade1, grade2);
                                break;
                            case "MEI-M02":
                                modul_grade = calculateM2(grade1, grade2);
                                break;
                            case "MEI-M03":
                                modul_grade = calculateM3(grade1, grade2, grade3);
                                break;
                            case "MEI-M04":
                                modul_grade = calculateM4(grade1, grade2, grade3);
                                break;
                            case "MEI-M05":
                                modul_grade = calculateM5(grade1, grade2, grade3);
                                break;
                            case "MEI-M10":
                                modul_grade = calculateM10(grade1, grade2, grade3);
                                break;
                        }
                        list.put(mei_moduls[i], modul_grade);
                        grade1 = 0;
                        grade2 = 0;
                        grade3 = 0;
                        modul_grade = 0;
                    }
                    for(int i = 0; i < inf_moduls.length; i++) {
                        if (!inf_moduls[i].equals("INF-M01") && !inf_moduls[i].equals("INF-M05") && !inf_moduls[i].equals("INF-M07")) {
                            if (!dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("note").getValue().toString().isEmpty()) {
                                grade1 = Double.parseDouble(dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("note").getValue().toString());
                            }
                            if (!dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("note").getValue().toString().isEmpty()) {
                                grade2 = Double.parseDouble(dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("note").getValue().toString());
                            }
                            if (!dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").child("note").getValue().toString().isEmpty()) {
                                grade3 = Double.parseDouble(dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").child("note").getValue().toString());
                            }
                        } else {
                            if (!dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("note").getValue().toString().isEmpty()) {
                                grade1 = Double.parseDouble(dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("note").getValue().toString());
                            }
                            if (!dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("note").getValue().toString().isEmpty()) {
                                grade2 = Double.parseDouble(dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("note").getValue().toString());
                            }
                        }
                        switch (inf_moduls[i]) {
                            case "INF-M01":
                                modul_grade = calculateI1(grade1, grade2);
                                break;
                            case "INF-M02":
                                modul_grade = calculateI2(grade1, grade2, grade3);
                                break;
                            case "INF-M04":
                                modul_grade = calculateI4(grade1, grade2, grade3);
                                break;
                            case "INF-M05":
                                modul_grade = calculateI5(grade1, grade2);
                                break;
                            case "INF-M06":
                                modul_grade = calculateI6(grade1, grade2, grade3);
                                break;
                            case "INF-M07":
                                modul_grade = calculateI7(grade1, grade2);
                                break;
                        }
                        list.put(inf_moduls[i], modul_grade);
                        grade1 = 0;
                        grade2 = 0;
                        grade3 = 0;
                        modul_grade = 0;
                    }
                    mei = calculateTotalMI(list.get("MEI-M03"), list.get("MEI-M04"), list.get("MEI-M05"), list.get("MEI-M10"));
                    inf = calculateTotalINF(list.get("INF-M01"), list.get("INF-M02"), list.get("INF-M04"), list.get("INF-M05"), list.get("INF-M06"), list.get("INF-M07"));
                    double bachelor1 = mei*0.5 + inf*0.3 + 0.2;
                    double bachelor2 = mei*0.5 + inf*0.3 + 0.4;
                    double bachelor3 = mei*0.5 + inf*0.3 + 0.6;
                    text = "Gesamtnote: " + "\n" +
                            "   - mit 1.0 Bachelor: " + bachelor1 + "\n" +
                            "   - mit 2.0 Bachelor: " + bachelor2 + "\n" +
                            "   - mit 3.0 Bachelor: " + bachelor3 + "\n" + "\n" +
                            "Medieninformatik: " + mei + "\n" + "\n" +
                            "Informationswissenschaften: " + inf;
                    grades.setText(text);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
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
            fillTextView();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}

    private double calculateM1(double m11, double m12) {
        return 0.7 * m11 + 0.3 * m12;
    }

    private double calculateM2(double m21, double m22) {
        return 0.5 * m21 + 0.5 * m22;
    }

    private double calculateM3(double m31, double m32, double m33) {
        return 0.25 * m31 + 0.25 * m32 + 0.5 * m33;
    }

    private double calculateM4(double m41, double m42, double m43) {
        return 0.25 * m41 + 0.25 * m42 + 0.5 * m43;
    }

    private double calculateM5(double m51, double m52, double m53) {
        return 0.25 * m51 + 0.25 * m52 + 0.5 * m53;
    }

    private double calculateM10(double m101, double m102, double m103) {
        return 0 * m101 + 0 * m102 + 1 * m103;
    }

    private double calculateI1(double i11, double i12) {
        return 0.5 * i11 + 0.5 * i12;
    }

    private double calculateI2(double i21, double i22, double i23) {
        return 0.5 * i21 + 0.5 * i22 + 0 * i23;
    }

    private double calculateI4(double i41, double i42, double i43) {
        return 0.25 * i41 + 0.25 * i42 + 0.5 * i43;
    }

    private double calculateI5(double i51, double i52) {
        return 0.33 * i51 + 0.66 * i52;
    }

    private double calculateI6(double i61, double i62, double i63) {
        return 0.25 * i61 + 0.25 * i62 + 0.5 * i63;
    }

    private double calculateI7(double i71, double i72) {
        return 0 * i71 + 1 * i72;
    }

    private double calculateTotalMI(double m3, double m4, double m5, double m10) {
        return 0.25 * m3 + 0.25 * m4 + 0.25 * m5 + 0.25 * m10;
    }

    private double calculateTotalINF(double i1, double i2, double i4, double i5, double i6, double i7) {
        return 0.15 * i1 + 0.15 * i2 + 0.15 * i4 + 0.275 * i5 + 0.275 * i6 + 0.275 * i7;
    }
}
