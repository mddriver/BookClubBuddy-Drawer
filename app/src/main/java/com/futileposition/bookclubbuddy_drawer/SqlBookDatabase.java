package com.futileposition.bookclubbuddy_drawer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.data;


/**
 * Created by MD on 6/17/2017.
 */

public class SqlBookDatabase extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "BOOK_TABLE";
    public static final String COLUMN_NAME_TITLE = "TITLE";
    public static final String COLUMN_NAME_AUTHOR = "AUTHOR";
    public static final String COLUMN_NAME_PAGES = "PAGES";
    public static final String COLUMN_NAME_START_DATE = "START_DATE";
    public static final String COLUMN_NAME_GOAL_DATE = "GOAL_DATE";
    public static final int DATABASE_VERSION = 1;

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    BaseColumns._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT," +
                    COLUMN_NAME_AUTHOR + " TEXT," +
                    COLUMN_NAME_PAGES + " INTEGER," +
                    COLUMN_NAME_START_DATE + " DATE," +
                    COLUMN_NAME_GOAL_DATE + " DATE)";

    public SqlBookDatabase(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void createBook(Context context, String title, String author, int pages, String start_date, String end_date)
    {
        SqlBookDatabase dBHelper = new SqlBookDatabase(context);
        SQLiteDatabase db = dBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SqlBookDatabase.COLUMN_NAME_TITLE, title);
        values.put(SqlBookDatabase.COLUMN_NAME_AUTHOR, author);
        values.put(SqlBookDatabase.COLUMN_NAME_PAGES, pages);
        values.put(SqlBookDatabase.COLUMN_NAME_START_DATE, start_date);
        values.put(SqlBookDatabase.COLUMN_NAME_GOAL_DATE, end_date);
        db.insert(TABLE_NAME,
                null,
                values);
        db.close();
    }




}