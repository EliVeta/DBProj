package com.example.dbproj;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddPlaceActivity extends Activity {
    //Button btnSave;
    EditText etStreet, etNumberHouse, etLat, etLon, etNamePlace;
    TextView tview;
    DBHelper dbHelper;
    DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        //btnSave = (Button) findViewById(R.id.buttonSavePlace);
        etStreet = (EditText) findViewById(R.id.editTextStreet);
        etNumberHouse = (EditText) findViewById(R.id.editTextNumberHouse);
        etLat = (EditText) findViewById(R.id.editTextLat);
        etLon = (EditText) findViewById(R.id.editTextLon);
        etNamePlace = (EditText) findViewById(R.id.editTextNamePlace);
        tview =(TextView) findViewById(R.id.textViewPlace);

        dbHelper = new DBHelper(this);
        dbManager = new DBManager(this);
    }

    public void ClickCancel(View view) {
        Intent intent=new Intent(AddPlaceActivity.this,MainActivity.class);
        //Запускаем его при нажатии:
        startActivity(intent);
    }

    @Override
    protected void onResume(){
        super.onResume();
        dbManager.openDB();
        /*for (String namePlaces : dbManager.getFromDBNamePlace()){
            tview.append("\n");
            tview.append(namePlaces);
        }*/
    }
    public void ClickSavePlace(View view) {
        String street = etStreet.getText().toString();
        String numberHouse = etNumberHouse.getText().toString();
        Double lat = Double.parseDouble(etLat.getText().toString());
        Double lon = Double.parseDouble(etLon.getText().toString());
        String namePlace = etNamePlace.getText().toString();

        dbManager.insertToDB(street,numberHouse,lon,lat,namePlace);

        /*for (String namePlaces : dbManager.getFromDBNamePlace()){
            tview.append("\n");
            tview.append(namePlaces);
        }*/

        //SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ContentValues contentValues = new ContentValues();

        //contentValues.put(, street);
        //database.insert(TPlace.Place.TABLE_NAME, null,contentValues );
    }
    @Override
    protected  void onDestroy(){
        super.onDestroy();
        dbManager.closeDB();
    }
}
