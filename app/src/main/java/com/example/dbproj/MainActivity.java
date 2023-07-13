package com.example.dbproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
//import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    DBManager dbManager;
    //SQLiteDatabase db;
    public MapView mapview;
    private Connection connection;

    public LocationManager locationManager;
    public LocationListener myLocationListener;
    public CoordinatorLayout rootCoordinatorLayout;
    public Point myLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("b36689fb-9a9f-499b-a707-c2a23f7acd92");
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        dbManager = new DBManager(this);
        mapview = findViewById(R.id.mapview);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //rootCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_coordinators);
        //locationManager = (locationManager) getSystemService(LOCALE_SERVICE);

    }

    private void pointMap() {
        dbManager.openDB();
        List<ArrayList<Double>> latLon = dbManager.getFromDBNameLatLon();
        for (ArrayList oneLatLon : latLon) {
            ArrayList<Double> coord = oneLatLon;
            for (int i = 0; i < coord.size(); i++) {
                Point mappoint = new Point(coord.get(i + 1), coord.get(i));
                mapview.getMap().getMapObjects().addPlacemark(mappoint);
                break;
            }
        }
        dbManager.closeDB();
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }


    @Override
    protected void onStart() {
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
        super.onStart();
        mapview.getMap().move(
                new CameraPosition(
                        new Point(55.994310, 92.797575), 15.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        pointMap();
        /*Point mappoint= new Point(55.994310, 92.797575);
        mapview.getMap().getMapObjects().addPlacemark(mappoint);
        Point mappoint2= new Point(56.005872,92.829969);
        mapview.getMap().getMapObjects().addPlacemark(mappoint2);*/
    }



   /* @Override
    public void onMapReady(MapKit map) {
        // Add some markers to the map, and add a data object to each marker.
        //ImageProvider imageProvider = ImageProvider.fromResource(this, R.drawable.ic_pin);
        //PlacemarkMapObject placemark = mapview.map.mapObjects.addPlacemark(Point(55.751225, 37.629540), imageProvider);
        LatLng place1 = new LatLng(-31.952854, 115.857342);
        Marker marker1 = map.addMarker(new MarkerOptions()
                .position(place1)
                .title("Perth"));
        marker1.setTag(0);

        Marker marker2 = map.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        marker2.setTag(0);

        Marker marker3 = map.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        marker3.setTag(0);

        // Set a listener for marker click.
        map.setOnMarkerClickListener(this);
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDB();

        /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, myLocationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                myLocationListener);
        checkEnabled();
*/

        ArrayAdapter<String> namePlaceAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, dbManager.getFromDBNamePlace()
        );

        namePlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spNamePlace = findViewById(R.id.spinnerPlace);
        spNamePlace.setAdapter(namePlaceAdapter);

        spNamePlace.setOnItemSelectedListener(onItemSelectedListener());
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void Click(View view) {
        Intent intent = new Intent(MainActivity.this, AddPlaceActivity.class);
        //Запускаем его при нажатии:
        startActivity(intent);
    }


    AdapterView.OnItemSelectedListener onItemSelectedListener() {
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

    public void ClickGoToMap(View view) {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

    public void ClickMyLocation(View view) {
        myLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }


        };
    }
}