package com.simpletodo.simpletodo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.simpletodo.simpletodo.db.TodoDataModel.TodoEntry;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TodoList.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + TodoEntry.TABLE_NAME + " (" +
            TodoEntry.TODO_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TodoEntry.TODO_COLUMN_NAME + " TEXT," +
            TodoEntry.TODO_COLUMN_PRIORITY + " INTEGER," +
            TodoEntry.TODO_COLUMN_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + TodoEntry.TABLE_NAME;

    public DbHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(SQL_DELETE_ENTRIES);
        onCreate(database);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}