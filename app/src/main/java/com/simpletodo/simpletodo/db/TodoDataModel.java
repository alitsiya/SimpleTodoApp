package com.simpletodo.simpletodo.db;

import android.provider.BaseColumns;

public class TodoDataModel {

    private TodoDataModel() {}

    /* Inner class that defines the table contents */
    public static class TodoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todoItems";
        public static final String TODO_COLUMN_ID = "id";
        public static final String TODO_COLUMN_NAME = "item";
        public static final String TODO_COLUMN_PRIORITY = "priority";
        public static final String TODO_COLUMN_DATE = "date";
    }
}
