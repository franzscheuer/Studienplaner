package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProgressActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ListView missingCourses;
    private int userID;
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        setupActionBar();
        setupUI();
        getUserID();
        setupDatabase();
        setMissingCourses();
    }

    private void setupActionBar() {
        getSupportActionBar().setTitle("Fortschrittsanzeige");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupUI() {
        progressBar = findViewById(R.id.progressBar);
        missingCourses = findViewById(R.id.missingCourses);
    }

    private void setupDatabase() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("user" + userID);
    }

    private void getUserID() {
        userID = getIntent().getExtras().getInt("userID");
    }

    // alle Positionen werden durchsucht, als nicht erledigt markierte Kurse werden
    // dem Array courses[] hinzugefügt und mitgezählt, um die Fortschrittsanzeige zu aktualisieren
    private void setMissingCourses() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] mei_courses = getResources().getStringArray(R.array.mei_kurse);
                String[] inf_courses = getResources().getStringArray(R.array.inf_kurse);
                int totalAmount = mei_courses.length + inf_courses.length;
                String[] courses = new String[totalAmount];
                int k = 0;
                String[] mei_moduls = getResources().getStringArray(R.array.mei_module);
                String[] inf_moduls = getResources().getStringArray(R.array.inf_module);
                int coursesDone = 0;
                for(int i = 0; i < mei_moduls.length; i++) {
                    String course_position = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").getKey();
                    String course_missing = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("done").getValue().toString();
                    if(!course_missing.equals("true")) {
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses[k] = course_position;
                        k++;
                    } else {
                        coursesDone++;
                    }
                    course_position = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").getKey();
                    course_missing = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("done").getValue().toString();
                    if(!course_missing.equals("true")) {
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses[k] = course_position;
                        k++;
                    } else {
                        coursesDone++;
                    }
                    if(!mei_moduls[i].equals("MEI-M01") && !mei_moduls[i].equals("MEI-M02")) {
                        course_position = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").getKey();
                        course_missing = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").child("done").getValue().toString();
                        if(!course_missing.equals("true")) {
                            course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                            courses[k] = course_position;
                            k++;
                        } else {
                            coursesDone++;
                        }
                    }
                }
                for(int i = 0; i < inf_moduls.length; i++) {
                    String course_position = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").getKey();
                    String course_missing = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("done").getValue().toString();
                    if(!course_missing.equals("true")) {
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses[k] = course_position;
                        k++;
                    } else {
                        coursesDone++;
                    }
                    course_position = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").getKey();
                    course_missing = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("done").getValue().toString();
                    if(!course_missing.equals("true")) {
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses[k] = course_position;
                        k++;
                    } else {
                        coursesDone++;
                    }
                    if(!inf_moduls[i].equals("INF-M01") && !inf_moduls[i].equals("INF-M05") && !inf_moduls[i].equals("INF-M07")) {
                        course_position = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").getKey();
                        course_missing = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").child("done").getValue().toString();
                        if(!course_missing.equals("true")) {
                            course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                            courses[k] = course_position;
                            k++;
                        } else {
                            coursesDone++;
                        }
                    }
                }
                // der ListView wird befüllt
                updateListView(coursesDone, courses);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    // alle gesammelten Kurse werden in den ListView eingetragen
    private void updateListView(int coursesDone, String[] courses) {
        int list_length = 0;
        for(int i = 0; i < courses.length; i++) {
            if(courses[i] != null) {
                list_length++;
            }
        }
        String[] list_array = new String[list_length];
        int k = 0;
        for(int i = 0; i < courses.length; i++) {
            if(courses[i] != null) {
                list_array[k] = courses[i];
                k++;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_array);
        missingCourses.setAdapter(adapter);
        updateProgressBar(coursesDone);
    }

    // die Fortschrittsanzeige wird mit Hilfe der mitgezählten Kurse aktualisiert
    private void updateProgressBar(int coursesDone) {
        String[] mei_courses = getResources().getStringArray(R.array.mei_kurse);
        String[] inf_courses = getResources().getStringArray(R.array.inf_kurse);
        int totalAmount = mei_courses.length + inf_courses.length;
        int value = (int)((coursesDone * 100.0f) / totalAmount);
        progressBar.setProgress(value);
    }
}
