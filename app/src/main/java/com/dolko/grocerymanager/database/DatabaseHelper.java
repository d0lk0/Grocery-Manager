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

    public Cursor getDataForMainMenu(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + ", " + COL2 +
                " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " >= date() " +
                " ORDER BY " + COL2 + " ASC LIMIT 3";
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
        String query = "INSERT INTO " + TABLE_NAME + " VALUES (1,'est','2015-03-18','1984-12-27 02:12:49'),(2,'ea','1982-03-04','1992-10-07 13:39:33'),(3,'officiis','2000-11-19','1989-12-23 23:57:01'),(4,'aut','1995-08-04','1987-02-14 19:16:04'),(5,'fugit','2013-01-04','2014-05-21 10:52:04'),(6,'deserunt','1985-05-28','1999-08-26 07:12:15'),(7,'dicta','2009-04-05','1981-04-26 02:36:35'),(8,'voluptatem','2000-06-26','2010-11-21 00:39:18'),(9,'facere','1979-01-09','2000-11-09 06:22:54'),(10,'voluptatem','1998-09-06','1989-11-20 03:46:00'),(11,'ea','1986-01-14','2012-06-25 00:17:30'),(12,'dicta','1980-10-04','1995-08-24 22:26:28'),(13,'sit','2010-12-03','1992-01-30 08:24:12'),(14,'distinctio','2003-03-29','2007-10-12 06:29:56'),(15,'quaerat','1975-10-22','2018-07-24 07:53:05'),(16,'dolorum','2007-06-02','1993-12-24 12:00:51'),(17,'vero','1993-11-04','2014-09-07 02:25:32'),(18,'doloribus','1987-08-02','2020-06-10 15:24:54'),(19,'laborum','2014-03-06','2014-08-03 21:34:47'),(20,'eligendi','1995-09-11','2018-03-22 15:37:42'),(21,'voluptatum','2020-03-13','1999-11-08 00:43:31'),(22,'alias','1974-07-20','1991-09-19 02:39:10'),(23,'recusandae','1983-10-28','2008-10-25 08:10:53'),(24,'harum','1999-06-21','1981-01-17 14:46:52'),(25,'est','1991-02-13','2019-06-11 15:41:55'),(26,'eaque','1976-04-29','2003-01-22 01:56:32'),(27,'veritatis','2009-03-09','2009-03-24 20:57:08'),(28,'ullam','1973-03-08','2007-07-21 10:43:07'),(29,'dicta','1983-11-11','2010-06-03 03:29:33'),(30,'voluptatum','1987-09-05','1976-09-04 06:00:04'),(31,'laboriosam','1995-09-07','1996-07-30 19:02:16'),(32,'necessitatibus','2008-05-11','1978-04-27 13:12:38'),(33,'quo','1998-04-08','2010-02-14 09:18:13'),(34,'ullam','2005-09-03','1971-07-31 16:29:22'),(35,'perferendis','2002-09-03','1978-01-24 17:56:39'),(36,'atque','1974-02-12','1991-12-28 03:08:18'),(37,'eius','1980-08-05','2015-05-22 12:20:36'),(38,'repellat','2022-03-17','1982-07-10 09:46:49'),(39,'nihil','2014-08-05','1990-12-08 17:37:49'),(40,'distinctio','1982-10-20','1992-05-13 04:50:41'),(41,'soluta','2020-06-14','1979-01-08 05:55:18'),(42,'est','1972-09-04','1996-12-07 07:14:43'),(43,'voluptatem','1983-09-08','2003-04-21 02:42:29'),(44,'deleniti','1975-01-15','2020-11-17 19:54:29'),(45,'quia','1985-01-14','2021-01-08 07:57:16'),(46,'ut','2014-12-28','1993-03-09 05:38:13'),(47,'dolor','1998-04-16','1996-06-27 12:04:04'),(48,'occaecati','2018-12-20','1978-12-27 04:10:28'),(49,'dolor','1979-01-31','1982-04-11 14:06:28'),(50,'consequatur','1977-06-23','2003-07-16 08:09:19'),(51,'dolorem','2004-11-02','1993-02-05 19:34:19'),(52,'enim','2019-04-04','2018-07-15 07:19:11'),(53,'laborum','1995-10-30','1994-07-05 15:03:49'),(54,'nulla','1994-07-31','2010-09-30 00:56:00'),(55,'est','1994-01-19','2018-04-02 11:28:11'),(56,'similique','1995-02-16','2009-10-03 05:37:45'),(57,'fuga','2016-03-16','1998-04-07 22:53:29'),(58,'nihil','2007-03-24','1987-03-04 02:09:57'),(59,'nemo','1986-07-06','1992-08-07 20:49:53'),(60,'occaecati','2012-09-04','1977-12-06 03:08:15'),(61,'sunt','1975-08-20','1995-11-15 15:23:30'),(62,'necessitatibus','1975-12-04','1997-07-21 23:07:05'),(63,'rerum','2022-02-19','1992-05-22 20:07:52'),(64,'dolores','1990-06-30','1984-01-30 19:51:42'),(65,'aut','2022-01-17','2003-04-25 20:59:23'),(66,'eligendi','2014-05-05','1980-11-17 15:51:44'),(67,'repellendus','2002-11-08','2018-04-25 19:02:14'),(68,'nobis','2014-07-23','1985-01-05 18:24:52'),(69,'fugit','1976-11-22','1982-07-14 12:32:24'),(70,'incidunt','1986-11-26','2021-02-09 04:45:23'),(71,'quo','2002-03-13','1991-05-08 21:53:42'),(72,'quidem','2006-07-07','1987-02-19 08:40:24'),(73,'recusandae','2002-11-27','1992-09-26 13:05:29'),(74,'cupiditate','1995-08-03','1995-11-29 00:50:57'),(75,'et','1989-04-26','1992-11-09 21:20:20'),(76,'rerum','1985-06-20','1998-05-18 17:20:39'),(77,'enim','1972-12-11','1987-04-18 14:04:43'),(78,'dicta','1979-08-06','2008-06-19 04:30:38'),(79,'est','1986-08-26','2000-10-31 03:35:50'),(80,'blanditiis','1984-11-04','2002-10-27 19:45:38'),(81,'non','1976-08-30','2013-10-01 19:13:02'),(82,'non','1974-12-21','1995-12-03 02:50:51'),(83,'perspiciatis','2013-02-16','2020-12-15 20:03:12'),(84,'sit','2005-02-17','1978-10-19 13:22:25'),(85,'deleniti','2019-01-13','1983-06-02 03:50:56'),(86,'sunt','1989-10-07','1996-02-18 05:22:34'),(87,'harum','1993-04-03','2016-02-05 18:20:12'),(88,'accusantium','2002-01-05','2020-03-26 06:17:55'),(89,'officia','1983-06-21','2012-08-06 17:54:58'),(90,'quaerat','1985-10-28','1987-04-27 05:33:10'),(91,'optio','2001-07-01','1986-11-26 21:11:03'),(92,'tenetur','2016-10-10','2013-07-11 07:00:04'),(93,'quae','2003-10-10','2001-11-03 17:06:45'),(94,'consequatur','1998-09-02','1999-02-23 09:48:12'),(95,'asperiores','2013-09-09','1976-10-02 08:51:13'),(96,'quia','2011-02-21','1997-07-26 03:45:52'),(97,'doloremque','2005-11-06','2000-05-16 03:47:20'),(98,'non','1995-05-24','2019-05-12 17:59:06'),(99,'sequi','2020-10-16','1975-09-25 09:45:28'),(100,'eum','1978-09-17','1996-04-21 13:21:21');";
        db.execSQL(query);
    }

}

