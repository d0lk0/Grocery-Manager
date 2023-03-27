package com.dolko.grocerymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "stored_products";
    private static final String COL1 = "product_name";
    private static final String COL2 = "expiration_date";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(\n" +
                "\t`id` integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\t" + COL1 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL2 + " DATE NULL DEFAULT NULL,\n" +
                "\t`creation_date` DATE NULL DEFAULT CURRENT_TIMESTAMP);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String product_name, String expiration_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, product_name);
        Log.d(TAG, "addData: Adding " + product_name + " to " + TABLE_NAME);

        contentValues.put(COL2, expiration_date);
        Log.d(TAG, "addData: Adding " + expiration_date + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    /*
    public Cursor getSpecificData(String option, String item){
        SQLiteDatabase db = this.getWritableDatabase();

        switch (option){
            case "Kód":
                option = COL2;
                break;
            case "Názov":
                option = COL3;
                break;
            case "Dátum - Expirácie":
                option = COL4;
                break;
            default:
                break;
        }

        String query = "SELECT * " +
                " FROM " + TABLE_NAME +
                " WHERE " + option  + " like '%" + item + "%'";

        return db.rawQuery(query, null);
    }
    */
    public Cursor Exists(String product_name, String expiration_date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " +
                " FROM " + TABLE_NAME +
                " WHERE "+ COL1 + " = '" + product_name + "' AND " + COL2 + " = '" + expiration_date + "'";;

        return db.rawQuery(query, null);
    }

    public Cursor getLimitedData(int limit){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " LIMIT " + limit;
        return db.rawQuery(query, null);
    }

    public Cursor getItemID(int name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + name + "'";
        return db.rawQuery(query, null);
    }

    /*
    public void updateName(int newCode, String newName, String newExpirationDate, int id, String oldName, String oldExpDate){
        SQLiteDatabase db = this.getWritableDatabase();

        String query =  "UPDATE " + TABLE_NAME +
                " SET " + COL2 + " = " + newCode + " , " + COL3 + " = '" + newName + "' , " + COL4 + " = '" + newExpirationDate + "'" +
                " WHERE " + COL2 + " = " + id + " AND " + COL3 + " = '" + oldName + "' AND " + COL4 + " = '" + oldExpDate + "'";

        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name, String expDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL2 + " = '" + id + "'" + " AND " + COL3 + " = '" + name + "'" + " AND " + COL4 + " = '" + expDate + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " with id: " + id + "and expirationDate of: " + expDate + " from database.");
        db.execSQL(query);
    }*/

    public void deleteDBContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void insertDBContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + " VALUES " +
                "(1,\"salads\",\"2022-11-30 00:27:31\",\"2023-11-13 09:37:25\"),\n" +
                "  (2,\"seafood\",\"2023-07-07 00:41:37\",\"2022-04-11 11:50:33\"),\n" +
                "  (3,\"stews\",\"2022-12-08 21:58:15\",\"2022-04-18 01:07:53\"),\n" +
                "  (4,\"seafood\",\"2022-12-15 18:36:47\",\"2023-10-13 00:26:28\"),\n" +
                "  (5,\"stews\",\"2023-02-25 16:54:19\",\"2022-03-31 09:30:03\"),\n" +
                "  (6,\"salads\",\"2022-03-01 15:34:25\",\"2022-11-14 23:16:24\"),\n" +
                "  (7,\"pies\",\"2023-09-05 22:03:10\",\"2023-01-20 14:28:07\"),\n" +
                "  (8,\"pies\",\"2023-10-08 03:00:10\",\"2022-07-27 08:03:31\"),\n" +
                "  (9,\"salads\",\"2023-04-07 15:21:56\",\"2023-07-23 03:00:00\"),\n" +
                "  (10,\"desserts\",\"2022-09-12 11:36:52\",\"2022-04-22 11:16:22\"),\n" +
                "  (11,\"noodles\",\"2023-04-24 12:50:30\",\"2023-11-30 07:55:42\"),\n" +
                "  (12,\"cereals\",\"2024-01-07 12:47:16\",\"2023-12-22 19:06:42\"),\n" +
                "  (13,\"pies\",\"2023-05-11 12:32:16\",\"2023-05-01 14:53:32\"),\n" +
                "  (14,\"noodles\",\"2022-11-29 18:53:59\",\"2023-05-23 03:35:12\"),\n" +
                "  (15,\"seafood\",\"2023-10-20 02:21:05\",\"2023-03-18 16:11:35\"),\n" +
                "  (16,\"seafood\",\"2023-09-29 06:04:19\",\"2024-02-19 08:02:50\"),\n" +
                "  (17,\"pasta\",\"2022-07-15 11:00:35\",\"2023-11-17 22:47:15\"),\n" +
                "  (18,\"salads\",\"2022-05-15 21:52:47\",\"2023-08-16 06:41:31\"),\n" +
                "  (19,\"pies\",\"2023-03-10 09:55:08\",\"2023-08-12 05:41:24\"),\n" +
                "  (20,\"salads\",\"2022-10-11 09:34:39\",\"2024-01-31 22:11:55\");";
        db.execSQL(query);
    }

}

