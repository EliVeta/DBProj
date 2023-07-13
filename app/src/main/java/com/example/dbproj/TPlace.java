package com.example.dbproj;

import android.provider.BaseColumns;

public final class TPlace {



    private TPlace() {}


    public  static class Place implements BaseColumns{
        public static final String TABLE_NAME = "Place";
        public static final String _ID = "_id";
        public static final String COLUMN_NAME_STREET = "street";
        public static final String COLUMN_NAME_NUMBERHOUSE = "numberHouse";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";
        public static final String COLUMN_NAME_NAMEPLACE = "namePlace";




    }
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TPlace.Place.TABLE_NAME + " (" +
                    Place._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Place.COLUMN_NAME_STREET + " TEXT NOT NULL," +
                    Place.COLUMN_NAME_NUMBERHOUSE + " TEXT NOT NULL," +
                    Place.COLUMN_NAME_LATITUDE + " DOUBLE," +
                    Place.COLUMN_NAME_LONGITUDE + " DOUBLE," +
                    Place.COLUMN_NAME_NAMEPLACE + " TEXT NOT NULL" +
                    ")";

    /*public  static final String SQL_INSERT_ENTRIES_IN_TPLACE =
            "INSERT INTO " + Place.TABLE_NAME + " ("+
                    Place.COLUMN_NAME_STREET + ", " +
                    Place.COLUMN_NAME_NUMBERHOUSE + ", " +
                    Place.COLUMN_NAME_LONGITUDE + ", " +
                    Place.COLUMN_NAME_LATITUDE + ", " +
                    Place.COLUMN_NAME_NAMEPLACE + ") " +
                    "VALUES " + "(\"Взлетная\", \"7а\", 56.032487, 92.913987, \"Офис АБС\"), " +
                    "(\"Весны\", \"1\", 56.034233, 92.907810, \"Взлетка Plaza\"), " +
                    "(\"Партизана Железняка\", \"42\", 56.036729, 92.926350, \"Кристалл Арена\"), " +
                    "(\"Академика Киренского\", \"26к1\", 55.994310, 92.797575, \"Институт космических и информационных технологий\"), " +
                    "(\"Взлетная\", \"26к1\", 56.032487, 92.913987, \"Офис АБС\");"

            ;*/
}




