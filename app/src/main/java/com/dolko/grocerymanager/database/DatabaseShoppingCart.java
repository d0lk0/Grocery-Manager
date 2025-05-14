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
    private static final String COL3 = "signed"; // preškrknute 0 : 1


    public DatabaseShoppingCart(Context context) {
        super(context, TABLE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(\n" +
                "\t`id` integer PRIMARY KEY AUTOINCREMENT,\n" +
                "\t" + COL1 + " text NULL DEFAULT NULL,\n" +
                "\t" + COL2 + " integer NULL DEFAULT NULL, \n" +
                "\t" + COL3 + " boolean NULL DEFAULT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addData(String product_name, String quantity, int signed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, product_name);
        Log.d(TAG, "addData: Adding " + product_name + " to " + TABLE_NAME);

        contentValues.put(COL2, quantity);
        Log.d(TAG, "addData: Adding " + quantity + " to " + TABLE_NAME);

        contentValues.put(COL3, signed);

        db.insert(TABLE_NAME, null, contentValues);
    }

    public void updateItemQuantity(int id, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("Update_Quantity " , id  + " " + quantity);
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + quantity + "' WHERE id=\"" + id + "\"");
    }

    public void updateItemName(int id, String new_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("Update_Name " , "Renamed : " + id + " to : " + new_name);
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COL1 + " = '" + new_name + "' WHERE id=\"" + id + "\";");
    }

    public void updateItemState(int id, String name, int state) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("Update_State name and id" , name + ", id: " + id + ", state: " + state);
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + COL3 + " = '" + state + "' WHERE " + "id" + " = '" + id + "';");
    }

    public void removeItem(String id, String name, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.e("cred" , id  + " " + name  + " " + quantity);

        db.execSQL("DELETE FROM " + TABLE_NAME + "\n" +
                "WHERE id == \"" + id + "\" AND " + COL1 + " == \"" + name + "\" AND " + COL2 + " == \"" + quantity + "\";");
    }

    public Cursor getNameByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE id=\"" + id + "\";";
        return db.rawQuery(query, null);
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
        deleteDBContent();

        addData("Syr","1", 0);
        addData("Kečup","2", 0);
        addData("Horčica","3", 0);
        addData("Šunka","4", 0);
        addData("Slivky","12", 0);
        addData("Maslo","3", 0);
        addData("Med","1", 0);
        addData("Banány","16", 0);
        addData("Jalbká","8", 0);
        addData("Mrkva","3", 0);
        addData("Vajcia","1", 0);
        addData("Chlieb","1", 0);
        addData("Smotana","2", 0);
    }


}

