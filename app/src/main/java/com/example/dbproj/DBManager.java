package com.example.dbproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class DBManager {
    private Context context;
    private  DBHelper dbHelper;
    private SQLiteDatabase db;
    public Cursor cursor;


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
        cv.put(TPlace.Place.COLUMN_NAME_LATITUDE, lat);
        cv.put(TPlace.Place.COLUMN_NAME_LONGITUDE, lon);
        cv.put(TPlace.Place.COLUMN_NAME_NAMEPLACE, namePlace);
        db.insert(TPlace.Place.TABLE_NAME, null, cv);
    }
    public HashMap<String, String> getFromDBNamePlace(){
        HashMap<String, String> tempListPlace = new HashMap<>();
        Cursor cursor = db.query(TPlace.Place.TABLE_NAME, null,
                null, null, null, null, null);

        while (cursor.moveToNext()){
            String _id = cursor.getString(cursor.getColumnIndexOrThrow(TPlace.Place._ID));
            String namePlaceData = cursor.getString(cursor.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_NAMEPLACE));
            tempListPlace.put(_id,namePlaceData);
        }
        cursor.close();
        return tempListPlace;
    }
    public List<ArrayList<Double>> getFromDBNameLatLon(){
        /*List<Double> tempListLatLon = new ArrayList<>();
        Cursor cursor1 = db.query(TPlace.Place.TABLE_NAME, null,
                null, null, null, null, null);

        cursor1.moveToFirst();
            Double latData = cursor1.getDouble(cursor1.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_LATITUDE));
            Double lonData = cursor1.getDouble(cursor1.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_LONGITUDE));
           // List<Double> latLon = new ArrayList<>();
            //latLon.add(latData);
            //latLon.add(lonData);
            tempListLatLon.add(latData);
            tempListLatLon.add(lonData);

        cursor1.close();
        return tempListLatLon;*/
        //ArrayList<String> tempListLat = new ArrayList<>();
        //ArrayList<String> tempListLon = new ArrayList<>();


        List<ArrayList<Double>> tempListLatLon = new ArrayList<>();
        cursor = db.query(TPlace.Place.TABLE_NAME, null,
                null, null, null, null, null);


        if (tempListLatLon.size()>0)tempListLatLon.clear();

        while (cursor.moveToNext()){
            //if (oneC.size()>0)oneC.clear();
            ArrayList<Double> oneC = new ArrayList<>();
            Double latData = cursor.getDouble(cursor.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_LATITUDE));
            Double lonData = cursor.getDouble(cursor.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_LONGITUDE));
            oneC.add(latData);
            oneC.add(lonData);
            tempListLatLon.add(oneC);
        }

        cursor.close();
        return tempListLatLon;
    }

    public List<ArrayList<Double>> getFromDBOneLatLon(int _id){

        List<ArrayList<Double>> tempListLatLon = new ArrayList<>();
        cursor = db.query(TPlace.Place.TABLE_NAME, null,
                TPlace.Place._ID=String.valueOf(_id), null, null, null, null);
        if (tempListLatLon.size()>0)tempListLatLon.clear();
        while (cursor.moveToNext()){
            ArrayList<Double> oneC = new ArrayList<>();
            Double latData = cursor.getDouble(cursor.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_LATITUDE));
            Double lonData = cursor.getDouble(cursor.getColumnIndexOrThrow(TPlace.Place.COLUMN_NAME_LONGITUDE));
            oneC.add(latData);
            oneC.add(lonData);
            tempListLatLon.add(oneC);
        }
        cursor.close();
        return tempListLatLon;
    }

    public void closeDB(){
        dbHelper.close();
    }

    public void openDB() {
        db = dbHelper.getWritableDatabase();
    }
}
