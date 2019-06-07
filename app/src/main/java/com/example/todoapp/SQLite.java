package com.example.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import com.example.todoapp.Models.Todos;

import java.util.ArrayList;


class SQLite extends SQLiteOpenHelper {

    private static final String TAG = SQLite.class
            .getSimpleName();


    // Name und Version der Datenbank
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    // Name und Attribute der Tabelle "todos"
    private static final String TODOS_ID = "TODOS_ID";
    private static final String TABLE_NAME_TODOS = "todos";
    private static final String TODOS_TITLE = "title";
    private static final String TODOS_DESCRIPTION = "description";
    private static final String TODOS_PRIORITY = "priority";
    private static final String TODOS_ADDRESS = "address";



    // Tabelle "todos" anlegen
    private static final String TABLE_TODOS_CREATE = "CREATE TABLE "
            + TABLE_NAME_TODOS + " ( TODOS_ID"
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TODOS_TITLE
            + " TEXT, " + TODOS_DESCRIPTION + " TEXT, " + TODOS_PRIORITY
            + " TEXT, "+ TODOS_ADDRESS + " TEXT);";

    // Tabelle "todos" löschen
    private static final String TABLE_TODOS_DROP =
            "DROP TABLE IF EXISTS " + TABLE_NAME_TODOS;

    public SQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLE_TODOS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(TAG, "Upgrade der Datenbank von Version "
                + oldVersion + " zu "
                + newVersion + "; alle Daten werden gelöscht");
        db.execSQL(TABLE_TODOS_DROP);
        onCreate(db);
    }


    public void insert(String title, String description, String priority, String address) {
        long rowId = -1;
        try {
// Datenbank öffnen
            SQLiteDatabase db = getWritableDatabase();
            Log.d(TAG, "Pfad: " + db.getPath());
// die zu speichernden Werte
            ContentValues values = new ContentValues();
            values.put(TODOS_TITLE, title);
            values.put(TODOS_DESCRIPTION, description);
            values.put(TODOS_PRIORITY, priority);
            values.put(TODOS_ADDRESS, address);
// in die Tabelle "TODOS" einfügen
            rowId = db.insert(TABLE_NAME_TODOS, null, values);
        } catch (SQLiteException e) {
            Log.e(TAG, "insert()", e);
        } finally {
            Log.d(TAG, "insert(): rowId=" + rowId);
        }
    }


    // Delete User
    public void deleteUser(int userid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_TODOS, TODOS_ID+" =?",new String[]{String.valueOf(userid)});
        db.close();
    }
    // Update User
    public int updateUser(String title, String description, String priority, String address, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TODOS_TITLE, title);
        values.put(TODOS_DESCRIPTION, description);
        values.put(TODOS_PRIORITY, priority);
        values.put(TODOS_ADDRESS, address);
        int count = db.update(TABLE_NAME_TODOS, values, TODOS_ID+" =?",new String[]{String.valueOf(id)});
        return count;
    }



    public ArrayList<Todos> getAllData(){
        ArrayList<Todos> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todos", null);

        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            String priority = cursor.getString(3);
            String address = cursor.getString(4);

            Todos todos = new Todos(id, title, description, priority, address);

            arrayList.add(todos);

        }
        return arrayList;
    }
}
