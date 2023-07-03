package com.example.dbproj;

import static com.example.dbproj.TPlace.SQL_CREATE_ENTRIES;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class DBHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "PlaceOnMap.db";
    static  final int DB_VERSION = 1;
    DBManager dbManager;
    Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME,  null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        // Добавляем записи в таблицу
        /*ContentValues values = new ContentValues();

        // Получим файл из ресурсов
        Resources res = context.getResources();

        // Открываем xml-файл
        XmlResourceParser _xml = res.getXml(R.xml.data_table);
        try {
            // Ищем конец документа
            int eventType = _xml.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Ищем теги record
                if ((eventType == XmlPullParser.START_TAG)
                        && (_xml.getName().equals("record"))) {
                    // Тег Record найден, теперь получим его атрибуты и
                    // вставляем в таблицу
                    String street = _xml.getAttributeValue(0);
                    String numberHouse = _xml.getAttributeValue(1);
                    Double lon = Double.valueOf(_xml.getAttributeValue(2));
                    Double lat = Double.valueOf(_xml.getAttributeValue(3));
                    String namePlace = _xml.getAttributeValue(4);

                    dbManager.insertToDB(street,numberHouse,lon,lat,namePlace);

                }
                eventType = _xml.next();
            }
        }
        // Catch errors
        catch (XmlPullParserException e) {
            Log.e("Test", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("Test", e.getMessage(), e);

        } finally {
            // Close the xml file
            _xml.close();
        }*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onUpgrade(sqLiteDatabase, i, i1);
    }



}
