package com.example.dbproj;

import android.provider.BaseColumns;

public final class TPlace {



    private TPlace() {}

    public  static class Place implements BaseColumns{
        public static final String TABLE_NAME = "Place";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_STREET = "street";
        public static final String COLUMN_NAME_NUMBERHOUSE = "numberHouse";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_NAMEPLACE = "namePlace";
    }
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TPlace.Place.TABLE_NAME + " (" +
                    Place._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Place.COLUMN_NAME_STREET + " TEXT NOT NULL," +
                    Place.COLUMN_NAME_NUMBERHOUSE + " TEXT NOT NULL," +
                    Place.COLUMN_NAME_LONGITUDE + " DOUBLE," +
                    Place.COLUMN_NAME_LATITUDE + " DOUBLE," +
                    Place.COLUMN_NAME_NAMEPLACE + " TEXT NOT NULL" +
                    ")";
}




