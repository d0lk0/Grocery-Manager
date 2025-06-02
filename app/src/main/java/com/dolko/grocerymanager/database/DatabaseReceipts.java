package com.dolko.grocerymanager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseReceipts extends SQLiteOpenHelper {
    private static final String RECEIPTS_TABLE_NAME = "stored_receipts";
    private static final String COL_ID = "id";
    private static final String COL_RECEIPT_ID = "receipt_id";
    private static final String COL_ADD_DATE = "add_date";
    private static final String COL_PRICE = "price";
    private static final String COL_NAME = "name";

    public DatabaseReceipts(Context context) {
        super(context, RECEIPTS_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NULL DEFAULT NULL, %s DATETIME NULL DEFAULT NULL, %s TEXT NULL DEFAULT NULL, %s TEXT NULL DEFAULT NULL);",
                RECEIPTS_TABLE_NAME, COL_ID, COL_RECEIPT_ID, COL_ADD_DATE, COL_PRICE, COL_NAME);;

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int factory, int version) {
        db.execSQL("DROP TABLE IF EXISTS " + RECEIPTS_TABLE_NAME);
        onCreate(db);
    }

    /*
     * ADD, GET
     * INSERT, UPDATE
     * DELETE
     * */

    public Cursor getAllReceipts(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s ORDER BY %s DESC;",
                RECEIPTS_TABLE_NAME, COL_ADD_DATE);
        ;
        return db.rawQuery(query, null);
    }

    public Cursor getReceipt(String receipt_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s = '%s';",
                RECEIPTS_TABLE_NAME, COL_RECEIPT_ID, receipt_id);

        return db.rawQuery(query, null);
    }

    public Cursor getLimitedData(int limit){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s ORDER BY %s DESC LIMIT %s;",
                RECEIPTS_TABLE_NAME, COL_ADD_DATE, limit);

        return db.rawQuery(query, null);
    }

    public boolean checkExistence(String receipt_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s='%s' LIMIT 1;",
                RECEIPTS_TABLE_NAME, COL_RECEIPT_ID, receipt_id);

        Cursor cursor =  db.rawQuery(query, null);
        boolean bool = cursor.moveToFirst();
        cursor.close();
        return bool;
    }

    public void addReceipt(String receipt_id, String time, String price, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES ('%s','%s','%s','%s');",
                RECEIPTS_TABLE_NAME, COL_RECEIPT_ID, COL_ADD_DATE, COL_PRICE, COL_NAME,
                receipt_id, time, price, name);

        db.execSQL(query);
    }

    public void updateReceipt(String receipt_id, String time, String price, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String query =  String.format("UPDATE %s SET %s = '%s', %s = '%s', %s = '%s' WHERE %s = '%s';",
                RECEIPTS_TABLE_NAME, COL_ADD_DATE, time, COL_PRICE, price, COL_NAME, name, COL_RECEIPT_ID, receipt_id);

        db.execSQL(query);
    }

    public void removeReceipt(String receipt_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("DELETE FROM %s WHERE %s = '%s';",
                RECEIPTS_TABLE_NAME, COL_RECEIPT_ID, receipt_id);

        db.execSQL(query);
    }

    public void deleteContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + RECEIPTS_TABLE_NAME);
    }

    public void insertContent(){
        deleteContent();

        addReceipt("O-41ACDB5025954A58ACDB5025954A586E", "", "", "");
        addReceipt("V-766D712F48DB4C36AD712F48DBCC366A", "", "", "");
        addReceipt("O-AC6D5656CDC64336AD5656CDC60336E0", "", "", "");

    }
}
