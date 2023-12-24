package com.example.btl_nhom2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.btl_nhom2.models.Category;
import com.example.btl_nhom2.models.Task;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Tên bảng và cột
    private static final String DATABASE_NAME = "BTL_JAVA_ANDROID.db";
    private static final int DATABASE_VERSION = 8;

    ///table CATEGORY
    private static final String TABLE_CATEGORY_NAME = "CATEGORY";
    private static final String COLUMN_CATEGORY_ID = "ID";
    private static final String COLUMN_CATEGORY_NAME = "CategoryName";


    ///table TASK
    private static final String TABLE_TASK_NAME = "TASK";
    private static final String COLUMN_TASK_ID = "ID";
    private static final String COLUMN_TASK_NAME = "TaskName";
    private static final String COLUMN_TASK_PRIORITY = "Priority";
    private static final String COLUMN_TASK_START_DAY = "StartDay";
    private static final String COLUMN_TASK_START_TIME = "StartTime";
    private static final String COLUMN_TASK_END_DAY = "EndDay";
    private static final String COLUMN_TASK_END_TIME = "EndTime";
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
                            COLUMN_TASK_PRIORITY + " INTEGER," +
                            COLUMN_TASK_START_DAY+ " TEXT," +
                            COLUMN_TASK_START_TIME+ " TEXT," +
                            COLUMN_TASK_END_DAY + " TEXT," +
                            COLUMN_TASK_END_TIME + " TEXT," +
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

        values.put(COLUMN_CATEGORY_NAME, "Working");
        db.insert(TABLE_CATEGORY_NAME, null, values);

        values.clear();

        values.put(COLUMN_CATEGORY_NAME, "New work");
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

    public void deleteAllTaskData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASK_NAME, null, null);
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

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_TASK_ID, COLUMN_TASK_NAME, COLUMN_TASK_PRIORITY,
        COLUMN_TASK_START_DAY, COLUMN_TASK_START_TIME, COLUMN_TASK_END_DAY, COLUMN_TASK_END_TIME, COLUMN_TASK_CATEGORY_ID};
        // Truy vấn cơ sở dữ liệu
        Cursor cursor = db.query(TABLE_TASK_NAME, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_TASK_ID);
                int taskNameIndex = cursor.getColumnIndex(COLUMN_TASK_NAME);
                int taskPriorityIndex = cursor.getColumnIndex(COLUMN_TASK_PRIORITY);
                int taskStartDayIndex = cursor.getColumnIndex(COLUMN_TASK_START_DAY);
                int taskStartTimeIndex = cursor.getColumnIndex(COLUMN_TASK_START_TIME);
                int taskEndDayIndex = cursor.getColumnIndex(COLUMN_TASK_END_DAY);
                int taskEndTimeIndex = cursor.getColumnIndex(COLUMN_TASK_END_TIME);
                int taskCategoryIdIndex = cursor.getColumnIndex(COLUMN_TASK_CATEGORY_ID);

                if (idIndex != -1 && taskNameIndex != -1 && taskPriorityIndex != -1
                        && taskStartDayIndex != -1 && taskStartTimeIndex != -1
                        && taskEndDayIndex != -1 && taskEndTimeIndex != -1 && taskCategoryIdIndex != -1) {

                    int id = cursor.getInt(idIndex);
                    String taskName = cursor.getString(taskNameIndex);
                    int taskPriority = cursor.getInt(taskPriorityIndex);
                    String taskStartDay = cursor.getString(taskStartDayIndex);
                    String taskStartTime = cursor.getString(taskStartTimeIndex);
                    String taskEndDay = cursor.getString(taskEndDayIndex);
                    String taskEndTime = cursor.getString(taskEndTimeIndex);
                    int taskCategoryId = cursor.getInt(taskCategoryIdIndex);

                    Task task = new Task(id, taskName, taskPriority,
                            taskStartDay, taskStartTime, taskEndDay, taskEndTime, taskCategoryId);

                    taskList.add(task);
                }
            } while (cursor.moveToNext());

            cursor.close();
        }

        // Đóng kết nối đến cơ sở dữ liệu
        db.close();

        return taskList;
    }

    public long insertTask(String taskName, int taskPriority,
                           String taskStartDay, String taskStartTime, String taskEndDay, String taskEndTime, long taskCategoryId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_TASK_PRIORITY, taskPriority);
        values.put(COLUMN_TASK_START_DAY, taskStartDay);
        values.put(COLUMN_TASK_START_TIME, taskStartTime);
        values.put(COLUMN_TASK_END_DAY, taskEndDay);
        values.put(COLUMN_TASK_END_TIME, taskEndTime);
        values.put(COLUMN_TASK_CATEGORY_ID, taskCategoryId);

        long taskId = db.insert(TABLE_TASK_NAME, null, values);

        db.close();

        return taskId;
    }


}
