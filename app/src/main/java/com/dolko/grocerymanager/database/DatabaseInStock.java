package com.dolko.grocerymanager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseInStock extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "stored_items";
    private static final String COL1 = "name";
    private static final String COL2 = "quantity";
    private static final String COL3= "unit_id";
    private static final String COL4 = "exp_date";
    private static final String COL5 = "buy_date";
    private static final String COL6 = "category_id"; //ovocie, zelenina, mäso, mliečne výrobky, atď.
    private static final String COL7 = "description";

    public DatabaseInStock(Context context) {
        super(context, TABLE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                "(id integer PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " text NULL DEFAULT NULL, " +
                COL2 + " integer DEFAULT 1, " +
                COL3 + " INTEGER, " +
                COL4 + " DATETIME NULL DEFAULT NULL, " +
                COL5 + " DATETIME NULL DEFAULT NULL, " +
                COL6 + " INTEGER, "+
                COL7 + " text NULL DEFAULT NULL, " +
                "FOREIGN KEY (category_id) REFERENCES categories(category_id), " +
                "FOREIGN KEY (unit_id) REFERENCES units(unit_id));";
        db.execSQL(createTable);

        createTable = "CREATE TABLE categories (category_id INTEGER PRIMARY KEY, category_name TEXT);";
        db.execSQL(createTable);
        createTable = "CREATE TABLE units (unit_id INTEGER PRIMARY KEY, unit_name TEXT);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int factory, int version) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";" +
                   "DROP TABLE IF EXISTS categories;" +
                   "DROP TABLE IF EXISTS units;");
        onCreate(db);
    }

    public void insertCategory(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO categories (category_name) VALUES ('" +  name + "');";
        db.execSQL(query);
    }

    public Cursor getCategories(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM categories;";
        return db.rawQuery(query, null);
    }

    public void removeCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM stored_items WHERE category_id = (SELECT category_id FROM categories WHERE category_name = '" + category + "')";
        db.execSQL(query);
        query = "DELETE FROM categories WHERE category_name = '" + category + "';";
        db.execSQL(query);
    }

    public void insertUnit(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO units (unit_name) VALUES ('" +  name + "');";
        db.execSQL(query);
    }

    public Cursor getUnits(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM units;";
        return db.rawQuery(query, null);
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL1 +" DESC";
        return db.rawQuery(query, null);
    }

    public Cursor getTimeLimited(int limit){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT name, quantity, exp_date  FROM " + TABLE_NAME + " WHERE exp_date not NULL ORDER BY " + COL4 + " ASC" + " LIMIT " + limit;
        return db.rawQuery(query, null);
    }

    public void addDebugData(String name, String category, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + " (name, quantity, unit_id, category_id) " +
                "VALUES ('" + name + "', " + quantity + ", (SELECT unit_id FROM units WHERE unit_name = 'Ks'), (SELECT category_id FROM categories WHERE category_name = '" + category + "'));";
        Log.d("insert", query);
        db.execSQL(query);
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

    public Cursor getCategoryItems(String category){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT id, stored_items.name, stored_items.quantity FROM " + TABLE_NAME +
                       " JOIN categories ON stored_items.category_id = categories.category_id " +
                       "WHERE categories.category_name = '" + category + "';";
        return db.rawQuery(query, null);
    }

    public Cursor checkExistence(String name, String exp_date, String buy_date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " +
                " FROM " + TABLE_NAME +
                " WHERE "+ COL1 + " = '" + name + "' AND " + COL2 + " = '" + exp_date + "'";

        return db.rawQuery(query, null);
    }

    public Cursor getItemByID(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * " + " FROM " + TABLE_NAME +
                " WHERE id = '" + id + "'";
        return db.rawQuery(query, null);
    }

    public void addItem(String name, String quantity, String unit, String exp_date, String buy_date, String category, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_NAME + " (name, quantity, unit_id, exp_date, buy_date, category_id, description) " +
                "VALUES ('" + name + "', " + quantity + ", " +
                "(SELECT unit_id FROM units WHERE unit_name = 'Ks'), '" + exp_date + "', '" + buy_date + "', " +
                "(SELECT category_id FROM categories WHERE category_name = '" + category + "'), '" + description+ "');";
        Log.d("insertItem", query);
        db.execSQL(query);
    }

    public void deleteItem(String id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + name + "'" + " AND " + "id" + " = " + id;
        db.execSQL(query);
    }

    public void deleteContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME +";");
        db.execSQL("DELETE FROM " + "categories" +";");
        db.execSQL("DELETE FROM " + "units" +";");

    }

    public void insertContent(){
        deleteContent();

        insertUnit("(Žiadna)");
        insertUnit("Ks");

        insertCategory("Pečivo");
        insertCategory("Mäsové výrobky");
        insertCategory("Ovocie a Zelenia");
        insertCategory("Mrazené výrobky");
        insertCategory("Cestoviny");
        insertCategory("Mliečne výrobky");
        insertCategory("Trvanlivé potraviny");
        insertCategory("Nápoje");

        addDebugData("Chlieb","Pečivo", 0);
        addDebugData("Rohlík","Pečivo", 0);
        addDebugData("Bageta","Pečivo", 0);
        addDebugData("Kaiserka","Pečivo", 0);
        addDebugData("Croissant","Pečivo", 0);
        addDebugData("Donut","Pečivo", 0);
        addDebugData("Tortila","Pečivo", 0);

        addDebugData("Hydina","Mäsové výrobky", 0);
        addDebugData("Bravčové","Mäsové výrobky", 0);
        addDebugData("Hovädzie","Mäsové výrobky", 0);
        addDebugData("Párky","Mäsové výrobky", 0);
        addDebugData("Klobásy","Mäsové výrobky", 0);
        addDebugData("Salámy","Mäsové výrobky", 0);
        addDebugData("Paštety","Mäsové výrobky", 0);
        addDebugData("Ryby","Mäsové výrobky", 0);

        addDebugData("Jablko", "Ovocie a Zelenia", 0);
        addDebugData("Hruška", "Ovocie a Zelenia", 0);
        addDebugData("Hrozno", "Ovocie a Zelenia", 0);
        addDebugData("Jahody", "Ovocie a Zelenia", 0);
        addDebugData("Melón", "Ovocie a Zelenia", 0);
        addDebugData("Mrkva", "Ovocie a Zelenia", 0);
        addDebugData("Mandarinka", "Ovocie a Zelenia", 0);
        addDebugData("Kapusta", "Ovocie a Zelenia", 0);

        addDebugData("Pizza", "Mrazené výrobky", 0);
        addDebugData("Ryby", "Mrazené výrobky", 0);
        addDebugData("Zmrzlina", "Mrazené výrobky", 0);
        addDebugData("Ľad", "Mrazené výrobky", 0);
        addDebugData("Zemiakové výrobky", "Mrazené výrobky", 0);

        addDebugData("Vaječné","Cestoviny", 0);

        addDebugData("Maslo","Mliečne výrobky", 0);
        addDebugData("Nátierka","Mliečne výrobky", 0);
        addDebugData("Syr","Mliečne výrobky", 0);
        addDebugData("Vajcia","Mliečne výrobky", 0);
        addDebugData("Jogurty","Mliečne výrobky", 0);
        addDebugData("Smotana","Mliečne výrobky", 0);

        addDebugData("Bazový sirup","Trvanlivé potraviny", 0);
        addDebugData("Kokosový koktejl", "Trvanlivé potraviny", 0);
        addDebugData("Mysli sypané", "Trvanlivé potraviny", 0);

        addDebugData("Kofola", "Nápoje", 0);
        addDebugData("Pepsi", "Nápoje", 0);
        addDebugData("Coca-Cola", "Nápoje", 0);
        addDebugData("Vinea", "Nápoje", 0);
        addDebugData("Fanta", "Nápoje", 0);
    }
}
