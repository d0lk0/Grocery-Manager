package com.dolko.grocerymanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseShoppingCart extends SQLiteOpenHelper {

    private static final String TAG = "ShoppingCart";

    private static final String TABLE_NAME = "shopping_cart";
    private static final String COL1 = "product_name";
    private static final String COL2 = "quantity";


    public DatabaseShoppingCart(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(\n" +
                "\t`id` integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\t" + COL1 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL2 + " integer NULL DEFAULT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String product_name, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, product_name);
        Log.d(TAG, "addData: Adding " + product_name + " to " + TABLE_NAME);

        contentValues.put(COL2, quantity);
        Log.d(TAG, "addData: Adding " + quantity + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public void removeItem(String id, String name, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("cred" , id  + " " + name  + " " + quantity);

        db.execSQL("DELETE FROM " + TABLE_NAME + "\n" +
                "WHERE id == \"" + id + "\" AND " + COL1 + " == \"" + name + "\" AND " + COL2 + " == \"" + quantity + "\";");
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public Cursor getLimitedData(int limit){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY id ASC" + " LIMIT " + limit;
        return db.rawQuery(query, null);
    }

    public void deleteDBContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void insertDBContent(){
        addData("Syr","1");
        addData("Kečup","6");
        addData("Horčica","3");
        addData("Šunka","4");
        addData("Slivky","144");
        addData("Maslo","148");
        addData("Med","102");
        addData("Banány","16");
        addData("Jalbká","44");
        addData("Mrkva","89");
        addData("Vajcia","7");
        addData("Chlieb","14");
        addData("Smotana","10");
    }


}

