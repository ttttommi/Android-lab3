package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Клас для управління базою даних SQLite
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "languages.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "results";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LANGUAGE = "language";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Створення таблиці
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LANGUAGE + " TEXT)";
        db.execSQL(createTable);
    }

    // Оновлення бази даних при зміні її структури
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Додавання нового запису
    public boolean insertLanguage(String language) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE, language);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Отримання всіх записів з таблиці
    public Cursor getAllLanguages() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Оновлення запису за ID
    public boolean updateLanguage(int id, String newLanguage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LANGUAGE, newLanguage);
        int result = db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Видалення запису за ID
    public boolean deleteLanguage(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }
}
