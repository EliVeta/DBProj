package com.example.dbproj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
//import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        dbManager = new DBManager(this);



    }

    @Override
    protected void onResume(){
        super.onResume();
        dbManager.openDB();

        ArrayAdapter<String> namePlaceAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dbManager.getFromDBNamePlace()
        );
        namePlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spNamePlace = (Spinner) findViewById(R.id.spinnerPlace);
        spNamePlace.setAdapter(namePlaceAdapter);

        spNamePlace.setOnItemSelectedListener(onItemSelectedListener());
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


    AdapterView.OnItemSelectedListener onItemSelectedListener(){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
    }
}