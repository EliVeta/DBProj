package com.example.dbproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //SQLiteOpenHelper sqLiteOpenHelper;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);//???
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                TPlace.Place.COLUMN_NAME_NAMEPLACE
        };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                TPlace.Place.COLUMN_NAME_NAMEPLACE + " DESC";

        Cursor cursor = db.query(
                TPlace.Place.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        List itemIds = new ArrayList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(TPlace.Place._ID));
            itemIds.add(itemId);
        }
        cursor.close();

        ArrayAdapter<String> namePlaceAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, itemIds
        );
        namePlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spNamePlace = (Spinner) findViewById(R.id.spinnerPlace);
        spNamePlace.setAdapter(namePlaceAdapter);

        spNamePlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void Click(View view) {
        Intent intent=new Intent(MainActivity.this,AddPlaceActivity.class);
        //Запускаем его при нажатии:
        startActivity(intent);
    }

   /* public void ClickChoisePlace(View view) {
    }*/
}