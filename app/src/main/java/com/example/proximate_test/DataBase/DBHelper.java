package com.example.proximate_test.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private final SQLiteDatabase database;

    public static final String TABLE_USERS ="USERINFO";
    public static final String USERS_TABLE_COLUMN_ID = "id";
    public static final String USERS_TABLE_COLUMN_STATUS = "status";
    public static final String USERS_TABLE_COLUMN_DATA = "data";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "USERSCREDENTIALS";

    public DBHelper(Context aContext) {
        super(aContext, DATABASE_NAME, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String buildSqlUser="CREATE TABLE " + TABLE_USERS + "( "
                + USERS_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERS_TABLE_COLUMN_STATUS + " TEXT, "
                + USERS_TABLE_COLUMN_DATA + " TEXT)";
        sqLiteDatabase.execSQL(buildSqlUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String buildSQL = "DROP TABLE IF EXISTS " + TABLE_USERS;
        sqLiteDatabase.execSQL(buildSQL);
    }
    public void InsertUsers(ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        sqLiteDatabase.insert(TABLE_USERS,null,contentValues);
    }

}
