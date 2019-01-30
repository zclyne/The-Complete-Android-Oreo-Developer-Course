package com.yifan.memorableplaces;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    public LocationManager locationManager;
    public LocationListener locationListener;

    // function to add marker and move the camera on the map
    public void centerMapOnLocation(Location location, String title) {
        if (location != null) {
            LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(locationLatLng).title(title));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, 10)); // the second parameter is between 1 and 20, which is the magnitude of zooming
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
                    Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    centerMapOnLocation(lastKnownLocation, "Your Location");
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this); // setup long click

        // receive intent
        Intent intent = getIntent();
        int placeNumber = intent.getIntExtra("placeNumber", 0);
        if (placeNumber == 0) { // zoom in on our user location
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    centerMapOnLocation(location, "Your Location");
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1); // ask for permission
            } else { // already got the permission
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                centerMapOnLocation(lastKnownLocation, "Your Location");
            }
        } else {
            Location placeLocation = new Location(LocationManager.GPS_PROVIDER);
            placeLocation.setLatitude(MainActivity.latLngs.get(placeNumber).latitude);
            placeLocation.setLongitude(MainActivity.latLngs.get(placeNumber).longitude);
            centerMapOnLocation(placeLocation, MainActivity.addresses.get(placeNumber));
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.yifan.memorableplaces", Context.MODE_PRIVATE);
        String titleString = "";
        try {
            List<Address> listAddresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // get address information
            if (listAddresses != null && listAddresses.size() > 0) {
                if (listAddresses.get(0).getThoroughfare() != null) {
                    titleString = listAddresses.get(0).getThoroughfare();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (titleString == "") {
            // get time
            SimpleDateFormat sd = new SimpleDateFormat("HH:mm yyyy-MM-dd");
            Date curDate = new Date(System.currentTimeMillis());
            titleString = sd.format(curDate);
        }
        mMap.addMarker(new MarkerOptions().position(latLng).title(titleString));

        MainActivity.addresses.add(titleString);
        MainActivity.latLngs.add(latLng);

        Toast.makeText(MapsActivity.this, "Location Saved!", Toast.LENGTH_SHORT).show();

        MainActivity.arrayAdapter.notifyDataSetChanged(); // update ListView

        // save title and latLongs permanently
        try {
            sharedPreferences.edit().putString("addresses", ObjectSerializer.serialize(MainActivity.addresses)).apply();
//            sharedPreferences.edit().putString("latLngs", ObjectSerializer.serialize(MainActivity.latLngs)).apply();
            // the above is wrong because our serializer cannot convert an ArrayList<LatLng> into a String
            // the right method is to create two ArrayLists for latitude and longitude
            ArrayList<String> latitudes = new ArrayList<>();
            ArrayList<String> longitudes = new ArrayList<>();
            for (LatLng coord : MainActivity.latLngs) {
                latitudes.add(String.valueOf(coord.latitude));
                longitudes.add(String.valueOf(coord.longitude));
            }
            sharedPreferences.edit().putString("latitudes", ObjectSerializer.serialize(latitudes)).apply();
            sharedPreferences.edit().putString("longitudes", ObjectSerializer.serialize(longitudes)).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
