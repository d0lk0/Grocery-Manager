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

    public DatabaseReceipts(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(\n" +
                "\t`id` integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\t" + COL1 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL2 + " DATE NULL DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int factory, int version) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }


    public boolean addReceipt(String name, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, name);
        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);

        contentValues.put(COL2, date);
        Log.d(TAG, "addData: Adding " + date + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllReceipts(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }


    public Cursor checkExistence(String product_name, String expiration_date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " +
                " FROM " + TABLE_NAME +
                " WHERE "+ COL1 + " = '" + product_name + "' AND " + COL2 + " = '" + expiration_date + "'";;

        return db.rawQuery(query, null);
    }

    public Cursor getItemByID(int name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + name + "'";
        return db.rawQuery(query, null);
    }

    /*
    public void updateReceipt(int newCode, String newName, String newExpirationDate, int id, String oldName, String oldExpDate){
        SQLiteDatabase db = this.getWritableDatabase();

        String query =  "UPDATE " + TABLE_NAME +
                " SET " + COL2 + " = " + newCode + " , " + COL3 + " = '" + newName + "' , " + COL4 + " = '" + newExpirationDate + "'" +
                " WHERE " + COL2 + " = " + id + " AND " + COL3 + " = '" + oldName + "' AND " + COL4 + " = '" + oldExpDate + "'";

        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteReceipt(int id, String name, String expDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL2 + " = '" + id + "'" + " AND " + COL3 + " = '" + name + "'" + " AND " + COL4 + " = '" + expDate + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " with id: " + id + "and expirationDate of: " + expDate + " from database.");
        db.execSQL(query);
    }*/

    public void deleteContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void insertContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + "VALUES\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"V-766D712F48DB4C36AD712F48DBCC366A\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"O-AC6D5656CDC64336AD5656CDC60336E0\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"O-AC6D5656CDC64336AD5656CDC60336E0\"),\n" +
                "(\"V-766D712F48DB4C36AD712F48DBCC366A\"),\n" +
                "(\"O-AC6D5656CDC64336AD5656CDC60336E0\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"O-AC6D5656CDC64336AD5656CDC60336E0\"),\n" +
                "(\"O-AC6D5656CDC64336AD5656CDC60336E0\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\"),\n" +
                "(\"V-766D712F48DB4C36AD712F48DBCC366A\"),\n" +
                "(\"O-AC6D5656CDC64336AD5656CDC60336E0\"),\n" +
                "(\"V-766D712F48DB4C36AD712F48DBCC366A\"),\n" +
                "(\"V-766D712F48DB4C36AD712F48DBCC366A\"),\n" +
                "(\"O-41ACDB5025954A58ACDB5025954A586E\");";
        db.execSQL(query);
    }
}
