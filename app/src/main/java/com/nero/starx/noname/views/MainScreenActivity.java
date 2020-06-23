package com.nero.starx.noname.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import  com.nero.starx.noname.Utlis.model.Wilaya;
import com.nero.starx.noname.R;
import com.nero.starx.noname.Utlis.Api.BaseApiService;
import com.nero.starx.noname.Utlis.Api.UtilsApi;
import com.nero.starx.noname.Utlis.SharedPrefManager;
import com.nero.starx.noname.Utlis.model.ResponseEtat;
import com.nero.starx.noname.Utlis.model.User;
import com.nero.starx.noname.Utlis.model.Wilaya;
import com.nero.starx.noname.databinding.ActivityMainScreenBinding;
import com.nero.starx.noname.viewmodel.MainViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainScreenActivity extends AppCompatActivity {
    private ActivityMainScreenBinding binding;
    BaseApiService mApiService;
    private boolean locationPermissionGranted = false;
    private final int  PERMISSIONS_REQUEST_ACCESS = 200;
    private SharedPreferences preferences;
    int etat;
    private MainViewModel viewModel;
    private FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        //some code that draws over the status bar
        Window window = this.getWindow();
        Drawable background = this.getResources().getDrawable(R.drawable.gradient_background , getTheme());
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.transparent , getTheme()));
        window.setBackgroundDrawable(background);
        setTheme(R.style.AppTheme);
        setContentView(view);

        preferences = getSharedPreferences("DATA_STORE" , MODE_PRIVATE);
        viewModel = new MainViewModel();
        locationPermissionGranted=true;

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        preferences.edit().putBoolean("IS_PERMISSION_GRANTED" , true).apply();
        setPermission();
        initActivity();
        mApiService = UtilsApi.getAPIService();


    }

    private void initActivity() {
        binding.username.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
        binding.fullmail.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail());
        binding.profilPicture.setActualImageResource(R.drawable.screen_background);
        binding.logoutButton.setOnClickListener(v -> LogOut());
        binding.mapButton.setOnClickListener(v -> LaunchActivity(new Intent(MainScreenActivity.this , MapActivity.class)));
        binding.decisionButton.setOnClickListener(v -> LaunchActivity(new Intent(MainScreenActivity.this , DecisionActivity.class)));
        binding.userState.setText(Etat("relizane"));
    }

    private String getWilaya() {
        Gson gson = new Gson();
        //get the current location
        viewModel.getDeviceLocation(
                locationPermissionGranted ,
                fusedLocationProviderClient,
                this);
        Double locationLongitude = gson.fromJson(preferences.getString("LONG" , "") , Double.class);
        Double locationlatitude = gson.fromJson(preferences.getString("LAT" , "") , Double.class);

        try {
            return viewModel.ReturnWilayaName(locationLongitude , locationlatitude , this);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void LogOut() {

            SharedPrefManager.getInstance(MainScreenActivity.this).clear();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

    }

    private String Etat(String wilaya) {
        mApiService = UtilsApi.getAPIService();
        mApiService.getEtat(wilaya).enqueue(
                new Callback<ResponseEtat>() {
                    @Override
                    public void onResponse(Call<ResponseEtat> call, Response<ResponseEtat> response) {
                        if (response.isSuccessful()) {

                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().toString());

                                etat=jsonRESULTS.getJSONObject("wilaya").getInt("etat");
                                 Log.d("etat", String.valueOf(etat));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseEtat> call, Throwable t) {

                    }
                }

        );
        return (String.valueOf(etat).equals("0"))? "Votre état:Dangerous place":"Votre état:Safe place";



    }

    private void LaunchActivity(Intent intent){
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void setPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode == PERMISSIONS_REQUEST_ACCESS) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                preferences.edit().putBoolean("IS_PERMISSION_GRANTED" , true).apply();
            }
        }
    }

}
