package com.example.dbproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.Toast;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    DBManager dbManager;
    public MapView mapview;
    private Connection connection;

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    public LocationManager locationManager;
    public LocationListener myLocationListener;
    public CoordinatorLayout rootCoordinatorLayout;
    public Point myPointLocation;

    public HashMap<String, String> key_name_place;
    public ArrayList<String> val_namePlace;

    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("b36689fb-9a9f-499b-a707-c2a23f7acd92");
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        dbManager = new DBManager(this);
        mapview = findViewById(R.id.mapview);



        // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        //rootCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.root_coordinators);
       /* locationManager = (LocationManager) getSystemService(Context.LOCALE_SERVICE);
        //Запрос обновления местоположения
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener()
        );*/
    }

    private void pointMap() {
        dbManager.openDB();
        List<ArrayList<Double>> latLon = dbManager.getFromDBLatLon();
        for (ArrayList oneLatLon : latLon) {
            ArrayList<Double> coord = oneLatLon;
            for (int i = 0; i < coord.size(); i++) {
                Point mappoint = new Point(coord.get(i + 1), coord.get(i)); //Поменять местами i+1 и i
                //потому что я неправильно внесла в бд долготу и широту
                //поэтому здесь наоборот вставила
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

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDB();
        key_name_place = new HashMap<>(dbManager.getFromDBNamePlace());
        val_namePlace = new ArrayList<>(key_name_place.values());
        ArrayAdapter<String> namePlaceAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, val_namePlace
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

    public void newCameraPosition(List<Double> latLon){
        mapview.getMap().move(
                new CameraPosition(
                        new Point(latLon.get(1), latLon.get(0)), 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
    }
    public void newCameraPosition(Location latLon){
        mapview.getMap().move(
                new CameraPosition(
                        new Point(latLon.getLatitude(), latLon.getLongitude()), 17.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        myPointLocation = new Point(latLon.getLatitude(), latLon.getLongitude());
        mapview.getMap().getMapObjects().addPlacemark(myPointLocation);
        //myPointLocation.setGeometry(new Point(latLon.getLatitude(), latLon.getLongitude()));
    }
    AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getBaseContext(), adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                String item = (String) adapterView.getItemAtPosition(i);

                for (Map.Entry<String, String> elem : key_name_place.entrySet()) {
                    if (elem.getValue() == item) {
                        int _id = Integer.parseInt(elem.getKey());
                        List<Double> latLon = dbManager.getFromDBOneLatLon(_id);
                        Toast.makeText(getBaseContext(), latLon.toString(), Toast.LENGTH_SHORT).show();
                        newCameraPosition(latLon);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        };
    }

    public void ClickMyLocation(View view) {
        final Context mainContext = this;
        Location loc = MyLocationListener.SetUpLocationListener(mainContext);
        //Toast.makeText(getBaseContext(), loc.toString(), Toast.LENGTH_SHORT).show();
        newCameraPosition(loc);
    }

}
