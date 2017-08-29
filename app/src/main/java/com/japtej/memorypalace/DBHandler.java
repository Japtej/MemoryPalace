package com.japtej.memorypalace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JaptejSingh on 04-08-2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="palace.db";
    private static final String TABLE_NAME="palaceTable";
    private static final String COLUMN_NAME="palaceName";
    private static final String ID="_id";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE "+TABLE_NAME+" ( "+ID+" INTEGER PRIMARY KEY, "+COLUMN_NAME+" TEXT )";
        sqLiteDatabase.execSQL(query);
        Log.i("DBHandler", "onCreate called");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        Log.i("DBHandler", "onUpgrade called");
    }

    public void addPalace(Palace palace){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, palace.get_name());
        db.insert(TABLE_NAME,null,values);
        db.close();

    }

    public Palace getPalace(int id){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[]{ID, COLUMN_NAME},
                ID+"=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if(cursor!=null)
            cursor.moveToFirst();
        Palace palace = new Palace(Integer.parseInt(cursor.getString(0)),cursor.getString(1));
        return palace;

    }

    public List<Palace> getAllPalaces(){

        List<Palace> palaces = new ArrayList<>();
        String query = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                Palace palace = new Palace();
                palace.set_id(Integer.parseInt(cursor.getString(0)));
                palace.set_name(cursor.getString(1));

                palaces.add(palace);
            }while(cursor.moveToNext());
        }

        return palaces;
    }

    public int upgradePalace(Palace palace){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(ID, palace.get_id());
        values.put(COLUMN_NAME, palace.get_name());

        Log.i("Check", "Inside upgradePalace. Value of ID: "+palace.get_id());
        return  db.update
                (TABLE_NAME,
                values,
                ID+"=?",
                new String[]{String.valueOf(palace.get_id())}
                );
    }

    public void deletePalace(Palace palace){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_NAME+"=?",new String[]{palace.get_name()});
        db.close();

    }
}
