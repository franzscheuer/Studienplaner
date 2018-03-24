package com.example.franz.studienplaner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ProgressActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView missingCourses;
    private int userID;
    private String courses = "";
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
        missingCourses.setSingleLine(false);
    }

    private void setupDatabase() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("user" + userID);
    }

    private void getUserID() {
        userID = getIntent().getExtras().getInt("userID");
    }

    private void setMissingCourses() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] mei_moduls = getResources().getStringArray(R.array.mei_module);
                String[] inf_moduls = getResources().getStringArray(R.array.inf_module);
                int coursesDone = 0;
                for(int i = 0; i < mei_moduls.length; i++) {
                    String course_position = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").getKey();
                    String course_missing = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("done").getValue().toString();
                    if(!course_missing.equals("true")){
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses = courses.concat("- " + course_position + "\n");
                    } else {
                        coursesDone++;
                    }
                    course_position = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").getKey();
                    course_missing = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("done").getValue().toString();
                    if(!course_missing.equals("true")){
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses = courses.concat("- " + course_position + "\n");
                    } else {
                        coursesDone++;
                    }
                    if(!mei_moduls[i].equals("MEI-M01") && !mei_moduls[i].equals("MEI-M02")) {
                        course_position = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").getKey();
                        course_missing = dataSnapshot.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").child("done").getValue().toString();
                        if(!course_missing.equals("true")){
                            course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                            courses = courses.concat("- " + course_position + "\n");
                        } else {
                            coursesDone++;
                        }
                    }
                }
                for(int i = 0; i < inf_moduls.length; i++) {
                    String course_position = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").getKey();
                    String course_missing = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("done").getValue().toString();
                    if(!course_missing.equals("true")){
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses = courses.concat("- " + course_position + "\n");
                    } else {
                        coursesDone++;
                    }
                    course_position = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").getKey();
                    course_missing = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("done").getValue().toString();
                    if(!course_missing.equals("true")){
                        course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                        courses = courses.concat("- " + course_position + "\n");
                    } else {
                        coursesDone++;
                    }
                    if(!inf_moduls[i].equals("INF-M01") && !inf_moduls[i].equals("INF-M05") && !inf_moduls[i].equals("INF-M07")) {
                        course_position = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").getKey();
                        course_missing = dataSnapshot.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").child("done").getValue().toString();
                        if(!course_missing.equals("true")){
                            course_position = course_position.substring(0, 7) + "." + course_position.substring(7, course_position.length());
                            courses = courses.concat("- " + course_position + "\n");
                        } else {
                            coursesDone++;
                        }
                    }
                }
                missingCourses.setText(courses);
                updateProgressBar(coursesDone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void updateProgressBar(int coursesDone) {
        String[] mei_courses = getResources().getStringArray(R.array.mei_kurse);
        String[] inf_courses = getResources().getStringArray(R.array.inf_kurse);
        int totalAmount = mei_courses.length + inf_courses.length;
        int value = (int)((coursesDone * 100.0f) / totalAmount);
        progressBar.setProgress(value);
    }
}
