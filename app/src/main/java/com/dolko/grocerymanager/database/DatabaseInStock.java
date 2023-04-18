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

    public boolean addData(String name, String category, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL1, name);
        Log.d(TAG, "addData: Adding " + category + " to " + TABLE_NAME);

        contentValues.put(COL6, category);
        Log.d(TAG, "addData: Adding " + category + " to " + TABLE_NAME);

        contentValues.put(COL2, quantity);
        Log.d(TAG, "addData: Adding " + quantity + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public void updateItem(String name, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 + " = '" + quantity + "' WHERE " + COL1 + " = '" + name + "';";
        db.execSQL(query);
    }

    public Cursor getCategoryItems(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL6 + " = '" + category + "'";
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
        addData("Chlieb","Pečivo", 0);
        addData("Rohlík","Pečivo", 0);
        addData("Bageta","Pečivo", 0);
        addData("Kaiserka","Pečivo", 0);
        addData("Croissant","Pečivo", 0);
        addData("Donut","Pečivo", 0);
        addData("Tortila","Pečivo", 0);

        addData("Hydina","Mäsové výrobky", 0);
        addData("Bravčové","Mäsové výrobky", 0);
        addData("Hovädzie","Mäsové výrobky", 0);
        addData("Párky","Mäsové výrobky", 0);
        addData("Klobásy","Mäsové výrobky", 0);
        addData("Salámy","Mäsové výrobky", 0);
        addData("Paštety","Mäsové výrobky", 0);
        addData("Ryby","Mäsové výrobky", 0);

        addData("Jablko", "Ovocie a Zelenia", 0);
        addData("Hruška", "Ovocie a Zelenia", 0);
        addData("Hrozno", "Ovocie a Zelenia", 0);
        addData("Jahody", "Ovocie a Zelenia", 0);
        addData("Melón", "Ovocie a Zelenia", 0);
        addData("Mrkva", "Ovocie a Zelenia", 0);
        addData("Mandarinka", "Ovocie a Zelenia", 0);
        addData("Kapusta", "Ovocie a Zelenia", 0);

        addData("Pizza", "Mrazené výrobky", 0);
        addData("Ryby", "Mrazené výrobky", 0);
        addData("Zmrzlina", "Mrazené výrobky", 0);
        addData("Ľad", "Mrazené výrobky", 0);
        addData("Zemiakové výrobky", "Mrazené výrobky", 0);

        addData("Vaječné","Cestoviny", 0);

        addData("Maslo","Mliečne výrobky", 0);
        addData("Nátierka","Mliečne výrobky", 0);
        addData("Syr","Mliečne výrobky", 0);
        addData("Vajcia","Mliečne výrobky", 0);
        addData("Jogurty","Mliečne výrobky", 0);
        addData("Smotana","Mliečne výrobky", 0);

        addData("Bazový sirup","Trvanlivé potraviny", 0);
        addData("Kokosový koktejl", "Trvanlivé potraviny", 0);
        addData("Mysli sypané", "Trvanlivé potraviny", 0);

        addData("Kofola", "Nápoje", 0);
        addData("Pepsi", "Nápoje", 0);
        addData("Coca-Cola", "Nápoje", 0);
        addData("Vinea", "Nápoje", 0);
        addData("Fanta", "Nápoje", 0);
    }
}
