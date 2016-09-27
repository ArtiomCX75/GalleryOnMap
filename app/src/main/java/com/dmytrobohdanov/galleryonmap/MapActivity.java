package com.dmytrobohdanov.galleryonmap;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static String KEY_LOCATION = "location";
    private LatLng photoLocation;
    private Marker marker;
    private GoogleMap map;
    GoogleMap.OnMarkerDragListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        String location = getIntent().getStringExtra(KEY_LOCATION);
        if (location != null) {
            //setting location to map
            photoLocation = getLatLngFromString(location);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //if there wasn't location on photo - put marker on my current location
        if (photoLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map.setMyLocationEnabled(true);
            Location location = map.getMyLocation();
            if (location == null) {
                photoLocation = new LatLng(0, 0);
            } else {
                photoLocation = new LatLng(location.getLatitude(), location.getLongitude());
            }
        }

        // Add a marker in Sydney and move the camera
        marker = map.addMarker(new MarkerOptions().position(photoLocation).draggable(true).title("Marker NOT in Sydney"));
        map.moveCamera(CameraUpdateFactory.newLatLng(photoLocation));

        listener = new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                updateLocation(marker);
            }
        };

        map.setOnMarkerDragListener(listener);
    }

    public void updateLocation(Marker marker){
        this.marker = marker;
    }

    /**
     * Forming LatLng class instance from sting of location
     *
     * @param location string with location separeted by coma
     * @return location in LatLng class instance
     */
    public LatLng getLatLngFromString(String location) {
        String[] latlong = location.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        return new LatLng(latitude, longitude);
    }

    //temp
    private void setResultOk() {
        Intent intent = new Intent();
        intent.putExtra(MapActivity.KEY_LOCATION,
                "" + marker.getPosition().latitude + ", " + marker.getPosition().longitude);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_save_location:
                setResultOk();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
