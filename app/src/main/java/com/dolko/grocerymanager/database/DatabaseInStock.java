package com.dolko.grocerymanager.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseInStock extends SQLiteOpenHelper {
    private static final String ITEMS_TABLE_NAME = "stored_items";
    private static final String COL_ITEMS_ID = "id";
    private static final String COL_ITEMS_NAME = "name";
    private static final String COL_ITEMS_QUANTITY = "quantity";
    private static final String COL_ITEMS_EXP_DATE = "exp_date";
    private static final String COL_ITEMS_BUY_DATE = "buy_date";
    private static final String COL_ITEMS_DESCRIPTION = "description";


    private static final String CATEGORY_TABLE_NAME = "categories";
    private static final String COL_CATEGORY_ID = "category_id";
    private static final String COL_CATEGORY_NAME = "category_name";

    private static final String UNITS_TABLE_NAME = "units";
    private static final String COL_UNITS_ID = "unit_id";
    private static final String COL_UNITS_NAME = "unit_name";


    public DatabaseInStock(Context context) {
        super(context, ITEMS_TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + ITEMS_TABLE_NAME + "(" +
                COL_ITEMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ITEMS_NAME + " TEXT NULL DEFAULT NULL, " +
                COL_ITEMS_QUANTITY + " INTEGER DEFAULT 1, " +
                COL_UNITS_ID + " INTEGER, " +
                COL_ITEMS_EXP_DATE + " DATETIME NULL DEFAULT NULL, " +
                COL_ITEMS_BUY_DATE + " DATETIME NULL DEFAULT NULL, " +
                COL_CATEGORY_ID + " INTEGER, "+
                COL_ITEMS_DESCRIPTION + " TEXT NULL DEFAULT NULL, " +
                "FOREIGN KEY (" + COL_CATEGORY_ID +") REFERENCES " + CATEGORY_TABLE_NAME + "(" + COL_CATEGORY_ID + "), " +
                "FOREIGN KEY (" + COL_UNITS_ID + ") REFERENCES " + UNITS_TABLE_NAME + "(" + COL_UNITS_ID + "));";

        Log.d(ITEMS_TABLE_NAME, createTable);
        db.execSQL(createTable);

        createTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT);",
                CATEGORY_TABLE_NAME, COL_CATEGORY_ID, COL_CATEGORY_NAME);
        Log.d(CATEGORY_TABLE_NAME, createTable);
        db.execSQL(createTable);


        createTable = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT);",
                UNITS_TABLE_NAME, COL_UNITS_ID, COL_UNITS_NAME);
        Log.d(UNITS_TABLE_NAME, createTable);
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int factory, int version) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE_NAME + ";" +
                   "DROP TABLE IF EXISTS " + CATEGORY_TABLE_NAME + ";" +
                   "DROP TABLE IF EXISTS " + UNITS_TABLE_NAME + ";");
        onCreate(db);
    }

    /*
    * ADD, GET
    * INSERT, UPDATE
    * DELETE
    * */

    public Cursor getTimeLimited(int limit){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT %s, %s, %s  FROM %s WHERE %s NOT NULL ORDER BY %s ASC LIMIT %s;",
                COL_ITEMS_NAME, COL_ITEMS_QUANTITY, COL_ITEMS_EXP_DATE, ITEMS_TABLE_NAME, COL_ITEMS_EXP_DATE, COL_ITEMS_EXP_DATE, limit);

        return db.rawQuery(query, null);
    }

    /* Categories -> */

    public Cursor getAllCategories(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s;",
                CATEGORY_TABLE_NAME);

        return db.rawQuery(query, null);
    }

    public Cursor getCategoryItems(String category){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format
                ("SELECT %s, %s.%s, %s.%s " +
                "FROM %s " +
                "JOIN %s ON %s.%s = %s.%s " +
                "WHERE %s.%s = '%s';",
                COL_ITEMS_ID, ITEMS_TABLE_NAME, COL_ITEMS_NAME, ITEMS_TABLE_NAME, COL_ITEMS_QUANTITY
                , ITEMS_TABLE_NAME
                , CATEGORY_TABLE_NAME, ITEMS_TABLE_NAME, COL_CATEGORY_ID, CATEGORY_TABLE_NAME, COL_CATEGORY_ID
                , CATEGORY_TABLE_NAME, COL_CATEGORY_NAME, category);

        return db.rawQuery(query, null);
    }

    public void insertCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format
                ("INSERT INTO %s (%s) VALUES ('%s');",
                CATEGORY_TABLE_NAME, COL_CATEGORY_NAME, category);

        db.execSQL(query);
    }

    public void deleteCategory(String category){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("DELETE FROM %s WHERE %s = (SELECT %s FROM %s WHERE %s ='%s');",
                ITEMS_TABLE_NAME, COL_CATEGORY_ID, COL_CATEGORY_ID, CATEGORY_TABLE_NAME, COL_CATEGORY_NAME, category);
        db.execSQL(query);

        query = String.format("DELETE FROM %s WHERE %s = '%s';",
                CATEGORY_TABLE_NAME, COL_CATEGORY_NAME, category);
        db.execSQL(query);
    }

    /* <- Categories */

    /* Units -> */

    public Cursor getAllUnits(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s;",
                UNITS_TABLE_NAME);

        return db.rawQuery(query, null);
    }

    public void insertUnit(String unit){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("INSERT INTO %s (%s) VALUES ('%s');",
                UNITS_TABLE_NAME, COL_UNITS_NAME, unit);

        db.execSQL(query);
    }

    public void deleteUnit(String unit){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("DELETE FROM %s WHERE %s = '%s';",
                UNITS_TABLE_NAME, COL_UNITS_NAME, unit);

        db.execSQL(query);
    }

    /* <- Units */

    /* Items -> */

    public void addItem(String name, String quantity, String unit, String exp_date, String buy_date, String category, String description) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) " +
                        "VALUES ('%s', '%s', (SELECT %s FROM %s WHERE %s = '%s'), '%s', '%s', (SELECT %s FROM %s WHERE %s = '%s'), '%s');",
                ITEMS_TABLE_NAME, COL_ITEMS_NAME, COL_ITEMS_QUANTITY, COL_UNITS_ID, COL_ITEMS_EXP_DATE, COL_ITEMS_BUY_DATE, COL_CATEGORY_ID, COL_ITEMS_DESCRIPTION,
                name, quantity, COL_UNITS_ID, UNITS_TABLE_NAME, COL_UNITS_NAME, unit, exp_date, buy_date, COL_CATEGORY_ID, CATEGORY_TABLE_NAME, COL_CATEGORY_NAME, category, description);

        db.execSQL(query);
    }

    public Cursor getAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s ORDER BY %s DESC;",
                ITEMS_TABLE_NAME, COL_ITEMS_NAME);

        return db.rawQuery(query, null);
    }

    public Cursor getItemByID(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("SELECT * FROM %s WHERE %s = '%s';",
                ITEMS_TABLE_NAME, COL_ITEMS_ID, id);

        return db.rawQuery(query, null);
    }

    public void updateItemQuantity(int id, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("stock.database.update_item_quantity " , "id: " + id  + " quantity: " + quantity);

        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s='%s';",
                ITEMS_TABLE_NAME, COL_ITEMS_QUANTITY, quantity, COL_ITEMS_ID , id);

        db.execSQL(query);
    }

    public void updateItemName(int id, String new_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("Update_Name " , "Renamed : " + id + " to : " + new_name);

        String query = String.format("UPDATE %s SET %s = '%s' WHERE %s='%s';",
                ITEMS_TABLE_NAME, COL_ITEMS_NAME, new_name, COL_ITEMS_ID , id);

        db.execSQL(query);
    }

    public void deleteItem(String id, String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = String.format("DELETE FROM %s WHERE %s='%s' AND %s='%s';",
                ITEMS_TABLE_NAME, COL_ITEMS_NAME,name, COL_ITEMS_ID, id);
        db.execSQL(query);

    }

    /* <- Items */

    /* Debug data -> */

    public void addDebugData(String name, String category, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + ITEMS_TABLE_NAME + " (name, quantity, unit_id, category_id) " +
                "VALUES ('" + name + "', " + quantity + ", (SELECT unit_id FROM units WHERE unit_name = 'Ks'), (SELECT category_id FROM categories WHERE category_name = '" + category + "'));";
        Log.d("insert", query);
        db.execSQL(query);
    }

    public void insertContent(){
        deleteContent();

        insertUnit("(Žiadna)");
        insertUnit("Ks");

        insertCategory("Pečivo");
        insertCategory("Mäsové výrobky");
        //insertCategory("Ovocie a Zelenia");
        //insertCategory("Mrazené výrobky");
        //insertCategory("Cestoviny");
        //insertCategory("Mliečne výrobky");
        //insertCategory("Trvanlivé potraviny");
        //insertCategory("Nápoje");

        addDebugData("Chlieb","Pečivo", 0);
        addDebugData("Rohlík","Pečivo", 0);
        addDebugData("Bageta","Pečivo", 0);
        /*addDebugData("Kaiserka","Pečivo", 0);
        addDebugData("Croissant","Pečivo", 0);
        addDebugData("Donut","Pečivo", 0);
        addDebugData("Tortila","Pečivo", 0);*/

        addDebugData("Hydina","Mäsové výrobky", 0);
        addDebugData("Bravčové","Mäsové výrobky", 0);
        addDebugData("Hovädzie","Mäsové výrobky", 0);
        /*addDebugData("Párky","Mäsové výrobky", 0);
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
        addDebugData("Fanta", "Nápoje", 0);*/
    }

    public void deleteContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + ITEMS_TABLE_NAME +";");
        db.execSQL("DELETE FROM " + CATEGORY_TABLE_NAME +";");
        db.execSQL("DELETE FROM " + UNITS_TABLE_NAME + ";");
    }

    /* <- Debug data */
}
