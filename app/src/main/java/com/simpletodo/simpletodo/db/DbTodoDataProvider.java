package com.simpletodo.simpletodo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpletodo.simpletodo.db.TodoDataModel.TodoEntry;

import java.util.ArrayList;

public class DbTodoDataProvider {
    private DbHelper mDbHelper;

    public DbTodoDataProvider(Context context) {
        mDbHelper =  new DbHelper(context);
    }

    public void storeTodoItemInDB(String item) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoEntry.TODO_COLUMN_NAME, item);

        db.insert(TodoEntry.TABLE_NAME, null, values);
    }

    public Boolean isTodoItemInDB(String item) {
        Cursor cursor = readTodoItemFromDB(item);
        return cursor.getCount() > 0;
    }

    public ArrayList<String> getTodoDataFromDb() {
        ArrayList<String> listOfToDoItems = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from " + TodoEntry.TABLE_NAME,null);
        while(cursor.moveToNext()) {
            String item = cursor.getString(
                cursor.getColumnIndexOrThrow(TodoEntry.TODO_COLUMN_NAME));
            listOfToDoItems.add(item);
        }
        cursor.close();

        return listOfToDoItems;
    }

    // Update Database
    public void updateTodoItemInDb(String previousItem, String newItem) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(TodoEntry.TODO_COLUMN_NAME, newItem);

        // Which row to update, based on the title
        String selection = TodoEntry.TODO_COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { previousItem };

        db.update(
            TodoEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs);

    }

    // Remove row from Database
    public void removeTodoItemFromDb(String item) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = TodoEntry.TODO_COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { item };
        db.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void close() {
        mDbHelper.close();
    }

    private Cursor readTodoItemFromDB(String item) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
            TodoEntry.TODO_COLUMN_ID,
            TodoEntry.TODO_COLUMN_NAME
        };

        String selection = TodoEntry.TODO_COLUMN_NAME + " = ?";
        String[] selectionArgs = {item};
        String sortOrder = TodoEntry.TODO_COLUMN_NAME + " DESC";

        return db.query(
            TodoEntry.TABLE_NAME,
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            selectionArgs,                            // The values for the WHERE clause
            null,
            null,
            sortOrder
        );
    }
}
