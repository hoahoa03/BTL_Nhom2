package com.example.btl_nhom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.btl_nhom2.database.Category;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Tên bảng và cột
    private static final String DATABASE_NAME = "BTL_JAVA_ANDROID.db";
    private static final int DATABASE_VERSION = 4;

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

    private boolean isTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT DISTINCT tbl_name FROM sqlite_master WHERE tbl_name = '" + tableName + "'", null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        if (!isTableExists(db, TABLE_CATEGORY_NAME)) {
            String createCategoryTableStatement = "CREATE TABLE " + TABLE_CATEGORY_NAME +
                    "(" + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CATEGORY_NAME + " TEXT)";
            db.execSQL(createCategoryTableStatement);
        }
        initialData(db);
        if (!isTableExists(db, TABLE_TASK_NAME)) {
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý nâng cấp cơ sở dữ liệu khi có thay đổi version
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY_NAME);
        onCreate(db);

    }

    private void initialData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_CATEGORY_NAME, "New work");
        db.insert(TABLE_CATEGORY_NAME, null, values);

        values.clear();

        values.put(COLUMN_CATEGORY_NAME, "Working");
        db.insert(TABLE_CATEGORY_NAME, null, values);

        values.clear();

        values.put(COLUMN_CATEGORY_NAME, "Complete");
        db.insert(TABLE_CATEGORY_NAME, null, values);

        values.clear();

        values.put(COLUMN_CATEGORY_NAME, "Late");
        db.insert(TABLE_CATEGORY_NAME, null, values);
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
    public void deleteAllCategoryData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY_NAME, null, null);
        db.close();
    }
    public List<Category> getCategoryData() {
        List<Category> categoryList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_CATEGORY_ID, COLUMN_CATEGORY_NAME};
        Cursor cursor = db.query(TABLE_CATEGORY_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_CATEGORY_ID);
                int categoryNameIndex = cursor.getColumnIndex(COLUMN_CATEGORY_NAME);

                if (idIndex != -1 && categoryNameIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String categoryName = cursor.getString(categoryNameIndex);

                    // Sử dụng dữ liệu theo ý muốn
                    Category category = new Category(id, categoryName);
                    categoryList.add(category);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }
        db.close();

        return categoryList;
    }

}
