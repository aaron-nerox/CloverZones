package com.nero.starx.noname.views;


import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.libraries.places.api.Places;
import com.nero.starx.noname.R;
import com.nero.starx.noname.Utlis.SharedPrefManager;
import com.nero.starx.noname.databinding.ActivityMapBinding;
import com.nero.starx.noname.model.data.StaticData;
import com.nero.starx.noname.viewmodel.MainViewModel;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private ActivityMapBinding binding;
    private GoogleMap Gmap;
    private MapView mapView;
    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();

        //some code that draws over the status bar
        Window window = this.getWindow();
        Drawable background = this.getResources().getDrawable(R.drawable.gradient_background , getTheme());
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.transparent , getTheme()));
        window.setBackgroundDrawable(background);

        setTheme(R.style.AppTheme);
        setContentView(v);


        SharedPreferences preferences = getSharedPreferences("DATA_STORE", MODE_PRIVATE);
        locationPermissionGranted = preferences.getBoolean("IS_PERMISSION_GRANTED" , false);

        //init the
        viewModel = new MainViewModel();

        //Init the places API
        String API_KEY = "AIzaSyBzM1LUbA64IzUPofoyiD9X9iBFtsY0Jm4";
        Places.initialize(getApplicationContext(), API_KEY);
        //PlacesClient placesClient = Places.createClient(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        initActivity();
        mapView.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    private void initActivity() {
        binding.miniProfil.setActualImageResource(R.drawable.screen_background);
        binding.profileName2.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
        mapView = binding.mapView;
        binding.relocateButton.setOnClickListener(v -> relocateGps());
        if(locationPermissionGranted){
            mapView.getMapAsync(this);
        }else{
            Toast.makeText(this, "Donnez la permission de localisation s'il-vous plait", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.Gmap = googleMap;
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(false);
        MapsInitializer.initialize(this);
        setDangerZones(googleMap);
        relocateGps();
    }

    private void setDangerZones(GoogleMap map) {
        //BLIDA=====================================================================================
            PolygonOptions polygonOptions = new PolygonOptions()
                    .strokeColor(getColor(R.color.red))
                    .fillColor(getColor(R.color.redtransparent))
                    .strokeWidth(3)
                    .add(StaticData.blida1)
                    .add(StaticData.blida2)
                    .add(StaticData.blida3)
                    .add(StaticData.blida4)
                    .add(StaticData.blida5)
                    .add(StaticData.blida6)
                    .add(StaticData.blida7)
                    .add(StaticData.blida8)
                    .add(StaticData.blida9)
                    .add(StaticData.blida10)
                    .add(StaticData.blida11)
                    .add(StaticData.blida12)
                    .add(StaticData.blida13)
                    .add(StaticData.blida14)
                    .add(StaticData.blida15)
                    .add(StaticData.blida16)
                    .add(StaticData.blida17)
                    .add(StaticData.blida18)
                    .add(StaticData.blida19)
                    .add(StaticData.blida20)
                    .add(StaticData.blida21)
                    .add(StaticData.blida22)
                    .add(StaticData.blida23)
                    .add(StaticData.blida24);
            map.addPolygon(polygonOptions);

        //ALGER=====================================================================================
        PolygonOptions polygonOptions2 = new PolygonOptions()
                .strokeColor(getColor(R.color.red))
                .fillColor(getColor(R.color.redtransparent))
                .strokeWidth(3)
                .add(StaticData.ALG1)
                .add(StaticData.ALG2)
                .add(StaticData.ALG3)
                .add(StaticData.ALG4)
                .add(StaticData.ALG5)
                .add(StaticData.ALG6)
                .add(StaticData.ALG7)
                .add(StaticData.ALG8)
                .add(StaticData.ALG9)
                .add(StaticData.ALG10)
                .add(StaticData.ALG11)
                .add(StaticData.ALG12)
                .add(StaticData.ALG13)
                .add(StaticData.ALG14)
                .add(StaticData.ALG15)
                .add(StaticData.ALG16)
                .add(StaticData.ALG17);

        map.addPolygon(polygonOptions2);
        //EL BORDJ==================================================================================
        PolygonOptions polygonOptions3 = new PolygonOptions()
                .strokeColor(getColor(R.color.red))
                .fillColor(getColor(R.color.redtransparent))
                .strokeWidth(3)
                .add(StaticData.BOR1)
                .add(StaticData.BOR2)
                .add(StaticData.BOR3)
                .add(StaticData.BOR4)
                .add(StaticData.BOR5)
                .add(StaticData.BOR6)
                .add(StaticData.BOR7)
                .add(StaticData.BOR8)
                .add(StaticData.BOR9)
                .add(StaticData.BOR10)
                .add(StaticData.BOR11)
                .add(StaticData.BOR12)
                .add(StaticData.BOR13)
                .add(StaticData.BOR14)
                .add(StaticData.BOR15)
                .add(StaticData.BOR16);

        map.addPolygon(polygonOptions3);
        //SETIF=====================================================================================
        PolygonOptions polygonOptions4 = new PolygonOptions()
                .strokeColor(getColor(R.color.red))
                .fillColor(getColor(R.color.redtransparent))
                .strokeWidth(3)
                .add(StaticData.SET1)
                .add(StaticData.SET2)
                .add(StaticData.SET3)
                .add(StaticData.SET4)
                .add(StaticData.SET5)
                .add(StaticData.SET6)
                .add(StaticData.SET7)
                .add(StaticData.SET8)
                .add(StaticData.SET9)
                .add(StaticData.SET10)
                .add(StaticData.SET11)
                .add(StaticData.SET12)
                .add(StaticData.SET13)
                .add(StaticData.SET14);

        map.addPolygon(polygonOptions4);
        //SKIKDA====================================================================================
        PolygonOptions polygonOptions5 = new PolygonOptions()
                .strokeColor(getColor(R.color.red))
                .fillColor(getColor(R.color.redtransparent))
                .strokeWidth(3)
                .add(StaticData.SKI1)
                .add(StaticData.SKI2)
                .add(StaticData.SKI3)
                .add(StaticData.SKI4)
                .add(StaticData.SKI5)
                .add(StaticData.SKI6)
                .add(StaticData.SKI7)
                .add(StaticData.SKI8)
                .add(StaticData.SKI9)
                .add(StaticData.SKI10)
                .add(StaticData.SKI11)
                .add(StaticData.SKI12)
                .add(StaticData.SKI13)
                .add(StaticData.SKI14)
                .add(StaticData.SKI15)
                .add(StaticData.SKI16);

        map.addPolygon(polygonOptions5);
        //TIZI======================================================================================
        PolygonOptions polygonOptions6 = new PolygonOptions()
                .strokeColor(getColor(R.color.red))
                .fillColor(getColor(R.color.redtransparent))
                .strokeWidth(3)
                .add(StaticData.TZI1)
                .add(StaticData.TZI2)
                .add(StaticData.TZI3)
                .add(StaticData.TZI4)
                .add(StaticData.TZI5)
                .add(StaticData.TZI6)
                .add(StaticData.TZI7)
                .add(StaticData.TZI8)
                .add(StaticData.TZI9)
                .add(StaticData.TZI10)
                .add(StaticData.TZI11)
                .add(StaticData.TZI12)
                .add(StaticData.TZI13)
                .add(StaticData.TZI14)
                .add(StaticData.TZI15)
                .add(StaticData.TZI16)
                .add(StaticData.TZI17);

        map.addPolygon(polygonOptions6);
        //MASCARA===================================================================================
        PolygonOptions polygonOptions7 = new PolygonOptions()
                .strokeColor(getColor(R.color.red))
                .fillColor(getColor(R.color.redtransparent))
                .strokeWidth(3)
                .add(StaticData.MCA1)
                .add(StaticData.MCA2)
                .add(StaticData.MCA3)
                .add(StaticData.MCA4)
                .add(StaticData.MCA5)
                .add(StaticData.MCA6)
                .add(StaticData.MCA7)
                .add(StaticData.MCA8)
                .add(StaticData.MCA9)
                .add(StaticData.MCA10)
                .add(StaticData.MCA11)
                .add(StaticData.MCA12)
                .add(StaticData.MCA13)
                .add(StaticData.MCA14)
                .add(StaticData.MCA15)
                .add(StaticData.MCA16)
                .add(StaticData.MCA17);

        map.addPolygon(polygonOptions7);
        //MSILA=====================================================================================
        PolygonOptions polygonOptions8 = new PolygonOptions()
                .strokeColor(getColor(R.color.red))
                .fillColor(getColor(R.color.redtransparent))
                .strokeWidth(3)
                .add(StaticData.MSL1)
                .add(StaticData.MSL2)
                .add(StaticData.MSL3)
                .add(StaticData.MSL4)
                .add(StaticData.MSL5)
                .add(StaticData.MSL6)
                .add(StaticData.MSL7)
                .add(StaticData.MSL8)
                .add(StaticData.MSL9)
                .add(StaticData.MSL10)
                .add(StaticData.MSL11)
                .add(StaticData.MSL12)
                .add(StaticData.MSL13)
                .add(StaticData.MSL14)
                .add(StaticData.MSL15)
                .add(StaticData.MSL16)
                .add(StaticData.MSL17)
                .add(StaticData.MSL18)
                .add(StaticData.MSL19)
                .add(StaticData.MSL20);
        map.addPolygon(polygonOptions8);


    }

    private void relocateGps() {
        //get the current location
        viewModel.getDeviceLocation(Gmap ,
                locationPermissionGranted ,
                fusedLocationProviderClient,
                this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
