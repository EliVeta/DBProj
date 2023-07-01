package com.example.dbproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private Context context;
    private  DBHelper dbHelper;
    private SQLiteDatabase db;

    public  DBManager(Context context){
        this.context = context;
        dbHelper = new DBHelper(context);
    }
    /*public void OpenDB(){
        db = dbHelper.getWritableDatabase();
    }*/
    public  void  insertToDB(String street, String numberHouse,
                             Double lon, Double lat, String namePlace){
        ContentValues cv = new ContentValues();
        cv.put(TPlace.Place.COLUMN_NAME_STREET, street);
        cv.put(TPlace.Place.COLUMN_NAME_NUMBERHOUSE, numberHouse);
        cv.put(TPlace.Place.COLUMN_NAME_LONGITUDE, lon);
        cv.put(TPlace.Place.COLUMN_NAME_LATITUDE, lat);
        cv.put(TPlace.Place.COLUMN_NAME_NAMEPLACE, namePlace);
        db.insert(TPlace.Place.TABLE_NAME, null, cv);
    }
    public List<String> getFromDBNamePlace(){
        List<String> tempListPlace = new ArrayList<>();
        Cursor cursor = db.query(TPlace.Place.TABLE_NAME, null,
                null, null, null, null, null);

        while (cursor.moveToNext()){
            String namePlaceData = cursor.getString(cursor.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_NAMEPLACE));
            tempListPlace.add(namePlaceData);
        }
        cursor.close();
        return tempListPlace;
    }
    public void closeDB(){
        dbHelper.close();
    }

    public void openDB() {
        db = dbHelper.getWritableDatabase();
    }
}
