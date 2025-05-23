package com.dolko.grocerymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseReceipts extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseReceipts";
    private static final String TABLE_NAME = "stored_receipts";
    private static final String COL1 = "receipt_id";
    private static final String COL2 = "add_date";
    private static final String COL3 = "price";
    private static final String COL4 = "name";

    public DatabaseReceipts(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(\n" +
                "\t`id` integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\t" + COL1 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL2 + " DATETIME NULL DEFAULT NULL,\n" +
                "\t" + COL3 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL4 + " text NULL DEFAULT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int factory, int version) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllReceipts(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL2 + " DESC";
        return db.rawQuery(query, null);
    }

    public Cursor getReceipt(String receipt_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + receipt_id + "'";
        return db.rawQuery(query, null);
    }

    public Cursor getLimitedData(int limit){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL2 + " DESC" + " LIMIT " + limit;
        return db.rawQuery(query, null);
    }


    public Cursor checkExistence(String name, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " +
                " FROM " + TABLE_NAME +
                " WHERE "+ COL1 + " = '" + name + "' AND " + COL2 + " = '" + date + "'";

        return db.rawQuery(query, null);
    }

    public Cursor getItemByID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + TABLE_NAME +
                       " WHERE " + COL1 + " = '" + name + "'";
        return db.rawQuery(query, null);
    }

    public boolean addReceipt(String receiptID, String time, String price, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, receiptID);
        contentValues.put(COL2, time);
        contentValues.put(COL3, price);
        contentValues.put(COL4, name);

        Log.d(TAG, "addData: Adding " + receiptID + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + time + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + price + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public void updateReceipt(String time, String price, String name, String receipt_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query =  "UPDATE " + TABLE_NAME +
                " SET " + COL2 + " = '" + time + "' , " + COL3 + " = '" + price + "' , " + COL4 + " = '" + name + "'" +
                " WHERE " + COL1 + " = '" + receipt_id + "'";

        Log.d(TAG, "updateName: query: " + query);
        db.execSQL(query);
    }

    public void removeReceipt(String receiptId){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME +
                       " WHERE " + COL1 + " = '" + receiptId + "'";

        Log.d(TAG, "deleteReceipt: Deleting " + receiptId);
        db.execSQL(query);
    }

    public void deleteContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void insertContent(){
        deleteContent();

        addReceipt("O-41ACDB5025954A58ACDB5025954A586E", "", "", "");
        addReceipt("V-766D712F48DB4C36AD712F48DBCC366A", "", "", "");
        addReceipt("O-AC6D5656CDC64336AD5656CDC60336E0", "", "", "");

    }
}
