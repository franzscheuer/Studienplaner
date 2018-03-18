package com.example.franz.studienplaner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CourseDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "courseList.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "courseList";
    public static final String  COLUMN_ID = "id";
    public static final String COLUMN_POSITION = "position";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GRADE = "grade";
    public static final String COLUMN_DONE = "done";

    public CourseDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_COURSELIST_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POSITION + " TEXT NOT NULL, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_GRADE + " INTEGER, " +
                COLUMN_DONE + " TEXT" + ");";
        db.execSQL(SQL_CREATE_COURSELIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
