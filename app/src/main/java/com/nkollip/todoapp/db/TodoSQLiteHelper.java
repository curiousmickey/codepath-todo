package com.nkollip.todoapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLite Helper class. Used by DAO
 * Created by nkollip on 4/8/2015.
 */
public class TodoSQLiteHelper extends SQLiteOpenHelper{

    //Initialization of Log TAG
    private static final String TAG = TodoSQLiteHelper.class.getName();

    //Variables for database name, table name and columns
    public static final String TODO_APP_DATABASE_NAME = "items.db";

    //Database version: Started with version 1; Current version is 3 after making few changes to column names
    public static final int TODO_APP_DATABASE_VERSION = 3;

    public static final String ITEMS_TABLE_NAME = "ITEMS";
    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_ITEM = "ITEM_NAME";

    //Table create sql
    public static final String ITEMS_TABLE_CREATE_SQL = "CREATE TABLE ITEMS ("+ COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM + " TEXT NOT NULL);";

    //Singleton Instance  -- To ensure no leaks
    private static TodoSQLiteHelper todoSQLiteHelper;

    /**
     * Singleton implementation of <code>TodoSQLiteHelper</code> instance
     * This ensures we are dealing with single instance of database and no leaks
     *
     * @param context
     * @return
     */
    public static synchronized  TodoSQLiteHelper getInstance(Context context) {
        if (todoSQLiteHelper == null) {
            todoSQLiteHelper = new TodoSQLiteHelper(context.getApplicationContext());
        }
        return  todoSQLiteHelper;
    }

    public TodoSQLiteHelper(Context context) {
        super(context, TODO_APP_DATABASE_NAME, null, TODO_APP_DATABASE_VERSION);
        Log.i(TAG, "Database " + "TODO_APP_DATABASE_NAME" + " is created successfully");
    }

    /**
     * This callback method creates Items databse table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ITEMS_TABLE_CREATE_SQL);
        Log.i(TAG, "Table " + "ITEMS_TABLE_NAME" + " is created successfully");
    }

    /**
     * This callback method is executed whenever database version is upgraded.
     * Started with version 1; Current version is 3 after making few changes to column names
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME);
        onCreate(db);
        Log.i(TAG, "Table " + "ITEMS_TABLE_NAME" + " is upgraded successfully");
    }
}
