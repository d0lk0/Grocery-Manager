package com.dolko.grocerymanager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseShoppingCart extends SQLiteOpenHelper {
    private static final String CART_TABLE_NAME = "shopping_cart";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "product_name";
    private static final String COL_QUANTITY = "quantity";
    private static final String COL_STATE = "signed"; // preškrknute 0 : 1


    public DatabaseShoppingCart(Context context) {
        super(context, CART_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NULL DEFAULT NULL, %s INTEGER NULL DEFAULT NULL, %s BOOLEAN NULL DEFAULT NULL);",
                CART_TABLE_NAME, COL_ID, COL_NAME, COL_QUANTITY, COL_STATE);

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CART_TABLE_NAME);
        onCreate(db);
    }

    /*
    * ADD, GET
    * INSERT, UPDATE
    * DELETE
    * */

    /* TODO: Add check for duplicity */

    public void addItem(String name, String quantity, int signed) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("INSERT INTO %s (%s,%s,%s) VALUES ('%s','%s','%s');",
                CART_TABLE_NAME, COL_NAME, COL_QUANTITY, COL_STATE, name, quantity, signed);

        db.execSQL(query);
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s;",
                CART_TABLE_NAME);

        return db.rawQuery(query, null);
    }

    public Cursor getLimitedData(int limit){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s ORDER BY %s ASC LIMIT %s;",
                CART_TABLE_NAME, COL_ID,limit);

        return db.rawQuery(query, null);
    }

    public void updateItemQuantity(int id, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s='%s';",
                CART_TABLE_NAME, COL_QUANTITY, quantity, COL_ID, id);

        db.execSQL(query);
    }

    public void updateItemName(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s='%s';",
                CART_TABLE_NAME, COL_NAME, name, COL_ID, id);

        db.execSQL(query);
    }

    public void updateItemState(int id, int state) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s='%s';",
                CART_TABLE_NAME, COL_STATE, state, COL_ID, id);

        db.execSQL(query);
    }

    public void removeItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("DELETE FROM %s WHERE %s = '%s';",
                CART_TABLE_NAME, COL_ID, id);

        db.execSQL(query);
    }

    /* Debug */
    public void deleteContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + CART_TABLE_NAME);
    }

    public void insertContent(){
        deleteContent();

        addItem("Syr","1", 0);
        addItem("Kečup","2", 0);
        addItem("Horčica","3", 0);
        addItem("Šunka","4", 0);
        addItem("Slivky","12", 0);
        addItem("Maslo","3", 0);
        addItem("Med","1", 0);
        addItem("Banány","16", 0);
        addItem("Jalbká","8", 0);
        addItem("Mrkva","3", 0);
        addItem("Vajcia","1", 0);
        addItem("Chlieb","1", 0);
        addItem("Smotana","2", 0);
    }

}

