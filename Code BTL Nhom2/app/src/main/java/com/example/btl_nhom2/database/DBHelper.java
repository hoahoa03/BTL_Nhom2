package com.example.btl_nhom2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // Tên bảng và cột
    private static final String DATABASE_NAME = "BTL_JAVA_ANDROID.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "TASK";
    private static final String COL_ID = "ID";
    private static final String COL_TaskName = "TaskName";
    private static final String COL_Content = "Content";
    private static final String COL_Priority = "Priority";
    private static final String COL_StartDay = "StartDay";
    private static final String COL_EndDay = "EndDay";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStatement =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COL_TaskName + " TEXT," +
                        COL_Content + " TEXT," +
                        COL_Priority + " TEXT," +
                        COL_StartDay + " INTEGER," +
                        COL_EndDay + " INTEGER);";
        db.execSQL(createStatement);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý nâng cấp cơ sở dữ liệu khi có thay đổi version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
