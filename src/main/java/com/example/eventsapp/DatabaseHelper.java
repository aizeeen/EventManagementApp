package com.example.eventsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "EventsApp.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_USERS = "users";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_REGISTRATIONS = "registrations";


    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";


    private static final String COLUMN_EVENT_ID = "id";
    private static final String COLUMN_EVENT_NAME = "name";
    private static final String COLUMN_EVENT_ICON = "icon";


    private static final String COLUMN_REG_ID = "id";
    private static final String COLUMN_REG_EVENT_ID = "event_id";
    private static final String COLUMN_REG_USER_NAME = "user_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_EMAIL + " TEXT UNIQUE, " +
                COLUMN_USER_PASSWORD + " TEXT)";
        db.execSQL(createUserTable);


        String createEventTable = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EVENT_NAME + " TEXT, " +
                COLUMN_EVENT_ICON + " TEXT)";
        db.execSQL(createEventTable);


        String createRegistrationTable = "CREATE TABLE " + TABLE_REGISTRATIONS + " (" +
                COLUMN_REG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REG_EVENT_ID + " INTEGER, " +
                COLUMN_REG_USER_NAME + " TEXT)";
        db.execSQL(createRegistrationTable);


        insertEvents(db);
    }

    private void insertEvents(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, "Enactus Esen");
        values.put(COLUMN_EVENT_ICON, "enactus_icon");
        db.insert(TABLE_EVENTS, null, values);

        values.put(COLUMN_EVENT_NAME, "Esen Android Club");
        values.put(COLUMN_EVENT_ICON, "android_club_icon");
        db.insert(TABLE_EVENTS, null, values);

        values.put(COLUMN_EVENT_NAME, "Esen Hive Club");
        values.put(COLUMN_EVENT_ICON, "hive_club_icon");
        db.insert(TABLE_EVENTS, null, values);

        values.put(COLUMN_EVENT_NAME, "Joker Esen");
        values.put(COLUMN_EVENT_ICON, "joker_icon");
        db.insert(TABLE_EVENTS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATIONS);
        onCreate(db);
    }


    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_USER_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }


    public boolean loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                COLUMN_USER_EMAIL + "=? AND " + COLUMN_USER_PASSWORD + "=?", new String[]{email, password});
        return cursor.getCount() > 0;
    }


    public Cursor getEvents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_EVENTS, null);
    }


    public boolean registerForEvent(int eventId, String userName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_REG_EVENT_ID, eventId);
        values.put(COLUMN_REG_USER_NAME, userName);

        long result = db.insert(TABLE_REGISTRATIONS, null, values);
        return result != -1;
    }


    public boolean isUserRegistered(int eventId, String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTRATIONS + " WHERE " +
                COLUMN_REG_EVENT_ID + "=? AND " + COLUMN_REG_USER_NAME + "=?", new String[]{String.valueOf(eventId), userName});
        boolean isRegistered = cursor.getCount() > 0;
        cursor.close();
        return isRegistered;
    }


    public ArrayList<String> getEventParticipants(int eventId) {
        ArrayList<String> participants = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_REGISTRATIONS, 
            new String[]{COLUMN_REG_USER_NAME}, 
            COLUMN_REG_EVENT_ID + " = ?",
            new String[]{String.valueOf(eventId)}, 
            null, null, null);

        if (cursor.moveToFirst()) {
            do {
                participants.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        return participants;
    }
}