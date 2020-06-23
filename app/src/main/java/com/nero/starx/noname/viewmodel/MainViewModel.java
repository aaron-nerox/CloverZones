package com.nero.starx.noname.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainViewModel {

    private SharedPreferences preferences;

    public void getDeviceLocation(GoogleMap googleMapCore, boolean permissionstate,
                               FusedLocationProviderClient fusedLocationProviderClient, Activity activity) {
        preferences= activity.getSharedPreferences("DATA_STORE" , Context.MODE_PRIVATE);
        try {
            if (permissionstate) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(activity, (OnCompleteListener<Location>) task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location lastKnownLocation = task.getResult();
                        Gson gson = new Gson();
                        if (lastKnownLocation != null) {
                            preferences.edit().putString("LONG" ,gson.toJson(lastKnownLocation.getLongitude())).apply();
                            preferences.edit().putString("LAT" ,gson.toJson(lastKnownLocation.getLatitude())).apply();
                            LatLng position = new LatLng(lastKnownLocation.getLatitude(),
                                    lastKnownLocation.getLongitude());
                            googleMapCore.animateCamera(CameraUpdateFactory.newLatLngZoom(position
                                    , 15));
                            googleMapCore.addMarker(new MarkerOptions()
                            .position(position)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                        }
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Localisation invalable", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (SecurityException e)  {
            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void getDeviceLocation(boolean permissionstate,
                                  FusedLocationProviderClient fusedLocationProviderClient, Activity activity) {
        preferences= activity.getSharedPreferences("DATA_STORE" , Context.MODE_PRIVATE);
        try {
            if (permissionstate) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(activity, (OnCompleteListener<Location>) task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location lastKnownLocation = task.getResult();
                        Gson gson = new Gson();
                        if (lastKnownLocation != null) {
                            preferences.edit().putString("LONG" ,gson.toJson(lastKnownLocation.getLongitude())).apply();
                            preferences.edit().putString("LAT" ,gson.toJson(lastKnownLocation.getLatitude())).apply();
                        }
                    } else {
                        Toast.makeText(activity.getApplicationContext(), "Localisation invalable", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (SecurityException e)  {
            Toast.makeText(activity.getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String ReturnWilayaName(Double longitud,Double Latitude,Context context) throws IOException {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(context, Locale.getDefault());
        String state;
        addresses = geocoder.getFromLocation( Latitude,longitud, 5);
        //String city = addresses.get(0).getLocality();
        state = addresses.get(0).getAdminArea();
        //String country = addresses.get(0).getCountryName();
        //String postalCode = addresses.get(0).getPostalCode();
        //String knownName = addresses.get(0).getFeatureName();
        Log.d("state",state.toString());

        return state;
    }

}
