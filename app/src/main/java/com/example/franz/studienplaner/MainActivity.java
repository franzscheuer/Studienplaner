package com.example.franz.studienplaner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int userID;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String USER_ID = "userID";
    FirebaseDatabase db;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserID();
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

    private void setupDatabase() {
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("user" + userID);
    }

    // falls die App das erste Mal gestartet wird und noch keine ID gespeichert wurde,
    // wird standardmäßig 0 als ID vergeben -> createNewUserID()
    private void getUserID() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userID = sharedPreferences.getInt(USER_ID, 0);
        if(userID == 0) {
            setupDatabase();
            createNewUserID();
        } else {
            setupDatabase();
        }
    }

    // diese Methode wird nur beim ersten Starten der App aufgerufen und teilt dem Nutzer
    // eine neue ID zu, welche in der Datenbank als belegt gespeichert wird
    private void createNewUserID() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 1;
                while(dataSnapshot.hasChild("user" + i)) {
                    i++;
                }
                ref.child("user" + i).setValue("user" + i);
                ref = db.getReference("user" + i);
                userID = i;
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(USER_ID, userID);
                editor.apply();
                prepareDatabase();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    // beim ersten Starten der App wird die Datenbank komplett angelegt, um Fehler beim
    // Abrufen nicht existierender Daten zu vermeiden
    private void prepareDatabase() {
        String[] mei_moduls = getResources().getStringArray(R.array.mei_module);
        String[] inf_moduls = getResources().getStringArray(R.array.inf_module);
        for(int i = 0; i < mei_moduls.length; i++) {
            ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("done").setValue(false);
            ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("name").setValue("");
            ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "1").child("note").setValue("");
            ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("done").setValue(false);
            ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("name").setValue("");
            ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "2").child("note").setValue("");
            if(!mei_moduls[i].equals("MEI-M01") && !mei_moduls[i].equals("MEI-M02")) {
                ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").child("done").setValue(false);
                ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").child("name").setValue("");
                ref.child("Medieninformatik").child(mei_moduls[i]).child(mei_moduls[i] + "3").child("note").setValue("");
            }
        }
        for(int i = 0; i < inf_moduls.length; i++) {
            ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("done").setValue(false);
            ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("name").setValue("");
            ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "1").child("note").setValue("");
            ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("done").setValue(false);
            ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("name").setValue("");
            ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "2").child("note").setValue("");
            if(!inf_moduls[i].equals("INF-M01") && !inf_moduls[i].equals("INF-M05") && !inf_moduls[i].equals("INF-M07")) {
                ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").child("done").setValue(false);
                ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").child("name").setValue("");
                ref.child("Informationswissenschaften").child(inf_moduls[i]).child(inf_moduls[i] + "3").child("note").setValue("");
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_module:
                startActivity(new Intent(MainActivity.this, ModulsActivity.class).putExtra("userID", userID));
                break;
            case R.id.button_noten:
                startActivity(new Intent(MainActivity.this, GradesActivity.class).putExtra("userID", userID));
                break;
            case R.id.button_fortschritt:
                startActivity(new Intent(MainActivity.this, ProgressActivity.class).putExtra("userID", userID));
                break;
            default:
                break;
        }
    }
}
