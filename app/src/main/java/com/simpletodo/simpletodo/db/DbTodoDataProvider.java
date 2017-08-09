package com.simpletodo.simpletodo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.simpletodo.simpletodo.customAdapter.Todo;
import com.simpletodo.simpletodo.db.TodoDataModel.TodoEntry;

import java.util.ArrayList;
import java.util.List;

public class DbTodoDataProvider {
    private DbHelper mDbHelper;

    public DbTodoDataProvider(Context context) {
        mDbHelper =  new DbHelper(context);
    }

    public void storeTodoItemInDB(Todo todoItem) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TodoEntry.TODO_COLUMN_NAME, todoItem.name);
        values.put(TodoEntry.TODO_COLUMN_PRIORITY, todoItem.priority);
        values.put(TodoEntry.TODO_COLUMN_DATE, todoItem.date);

        try {
            db.beginTransaction();
            db.insert(TodoEntry.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public Boolean isTodoItemInDB(Todo todoItem) {
        Cursor cursor = readTodoItemFromDB(todoItem);
        return cursor.getCount() > 0;
    }

    public List<Todo> getTodoDataFromDb() {
        List<Todo> listOfToDoItems = new ArrayList<>();

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from " + TodoEntry.TABLE_NAME,null);
        while(cursor.moveToNext()) {
            String item = cursor.getString(
                cursor.getColumnIndexOrThrow(TodoEntry.TODO_COLUMN_NAME));
            Integer priority = cursor.getInt(
                cursor.getColumnIndexOrThrow(TodoEntry.TODO_COLUMN_PRIORITY));
            String date = cursor.getString(
                cursor.getColumnIndexOrThrow(TodoEntry.TODO_COLUMN_DATE));
            Todo todoItem = new Todo(item, priority, date);
            listOfToDoItems.add(todoItem);
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
    public void removeTodoItemFromDb(Todo todoItem) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String selection = TodoEntry.TODO_COLUMN_NAME + " LIKE ?";
        String[] selectionArgs = { todoItem.name };
        db.delete(TodoEntry.TABLE_NAME, selection, selectionArgs);
    }

    public void close() {
        mDbHelper.close();
    }

    private Cursor readTodoItemFromDB(Todo todoItem) {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
            TodoEntry.TODO_COLUMN_ID,
            TodoEntry.TODO_COLUMN_NAME
        };

        String selection = TodoEntry.TODO_COLUMN_NAME + " = ?";
        String[] selectionArgs = {todoItem.name};
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
