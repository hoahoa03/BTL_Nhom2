package com.example.btl_nhom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    // Tên bảng và cột
    private static final String DATABASE_NAME = "BTL_JAVA_ANDROID.db";
    private static final int DATABASE_VERSION = 2;

    ///table CATEGORY
    private static final String TABLE_CATEGORY_NAME = "CATEGORY";
    private static final String COLUMN_CATEGORY_ID = "ID";
    private static final String COLUMN_CATEGORY_NAME = "CategoryName";


    ///table TASK
    private static final String TABLE_TASK_NAME = "TASK";
    private static final String COLUMN_TASK_ID = "ID";
    private static final String COLUMN_TASK_NAME = "TaskName";
    private static final String COLUMN_TASK_CONTENT = "Content";
    private static final String COLUMN_TASK_PRIORITY = "Priority";
    private static final String COLUMN_TASK_START_DAY = "StartDay";
    private static final String COLUMN_TASK_END_DAY = "EndDay";
    private static final String COLUMN_TASK_CATEGORY_ID = "CategoryID"; // trường liên kết với bảng Category

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoryTableStatement = "CREATE TABLE " + TABLE_CATEGORY_NAME +
                "(" + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY_NAME + " TEXT)";
        db.execSQL(createCategoryTableStatement);

        String createTaskTableStatement =
                "CREATE TABLE " + TABLE_TASK_NAME + "(" +
                        COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_TASK_NAME + " TEXT," +
                        COLUMN_TASK_CONTENT + " TEXT," +
                        COLUMN_TASK_PRIORITY + " TEXT," +
                        COLUMN_TASK_START_DAY + " INTEGER," +
                        COLUMN_TASK_END_DAY + " INTEGER," +
                        COLUMN_TASK_CATEGORY_ID + " INTEGER," +
                        "FOREIGN KEY(" + COLUMN_TASK_CATEGORY_ID + ") REFERENCES " +
                        TABLE_TASK_NAME + "(" + COLUMN_CATEGORY_ID + "))";
        db.execSQL(createTaskTableStatement);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý nâng cấp cơ sở dữ liệu khi có thay đổi version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_NAME);
        onCreate(db);
    }

    public long addCategory(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORY_NAME, categoryName);

        // Thêm CATEGORY mới
        long newRowId = db.insert(TABLE_CATEGORY_NAME, null, values);
        db.close();
        return newRowId;
    }
}
