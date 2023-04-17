package com.dolko.grocerymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseInStock extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseInStock";
    private static final String TABLE_NAME = "stored_items";
    private static final String COL1 = "name";
    private static final String COL2 = "quantity";
    private static final String COL3= "unit"; //ks, bal, kg
    private static final String COL4 = "exp_date";
    private static final String COL5 = "buy_date";
    private static final String COL6 = "category"; //ovocie, zelenina, mäso, mliečne výrobky, atď.
    private static final String COL7 = "description";

    public DatabaseInStock(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(\n" +
                "\t`id` integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\t" + COL1 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL2 + " integer DEFAULT 1,\n" +
                "\t" + COL3 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL4 + " DATETIME NULL DEFAULT NULL,\n" +
                "\t" + COL5 + " DATETIME NULL DEFAULT NULL,\n" +
                "\t" + COL6 + " text NULL DEFAULT NULL,\n"+
                "\t" + COL7 + " text NULL DEFAULT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int factory, int version) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL1 +" DESC";
        return db.rawQuery(query, null);
    }

    public Cursor checkExistence(String name, String exp_date, String buy_date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " +
                " FROM " + TABLE_NAME +
                " WHERE "+ COL1 + " = '" + name + "' AND " + COL2 + " = '" + exp_date + "'";

        return db.rawQuery(query, null);
    }

    public Cursor getItemByID(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + TABLE_NAME +
                " WHERE id = '" + id + "'";
        return db.rawQuery(query, null);
    }

    public boolean addReceipt(String name, String quantity, String unit, String exp_date, String buy_date, String category, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, name);
        contentValues.put(COL2, quantity);
        contentValues.put(COL3, unit);
        contentValues.put(COL4, exp_date);
        contentValues.put(COL5, buy_date);
        contentValues.put(COL6, category);
        contentValues.put(COL7, description);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + quantity + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + unit + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + exp_date + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + buy_date + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + category + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + description + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public void deleteItem(String name, String unit, String exp_date, String buy_date, String category){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + name + "'" + " AND " + COL2 + " = '" + unit + "'";

        //Log.d(TAG, "deleteReceipt: Deleting " + receiptId + " with date: " + date + " from database.");
        db.execSQL(query);
    }

    public void deleteContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void insertContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + "VALUES\n" +
                "x";
        db.execSQL(query);
    }
}
