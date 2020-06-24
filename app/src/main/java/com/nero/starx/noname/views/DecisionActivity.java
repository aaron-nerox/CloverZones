package com.nero.starx.noname.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.nero.starx.noname.R;
import com.nero.starx.noname.Utlis.Api.BaseApiService;
import com.nero.starx.noname.Utlis.Api.UtilsApi;
import com.nero.starx.noname.Utlis.SharedPrefManager;
import com.nero.starx.noname.Utlis.model.ResponseDecisionModel;
import com.nero.starx.noname.Utlis.model.ResponseDecisions;
import com.nero.starx.noname.databinding.ActivityDecisionBinding;
import com.nero.starx.noname.model.OutletClickListener;
import com.nero.starx.noname.model.adapters.DecisionElementAdapter;
import com.nero.starx.noname.viewmodel.MainViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DecisionActivity extends AppCompatActivity {

    private ActivityDecisionBinding binding;
    private RecyclerView DecisionRecycler;
    private SharedPreferences preferences;
    private boolean locationPermissionGranted;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private MainViewModel viewModel;
    BaseApiService mApiService;
    DecisionElementAdapter adapter;
    List<ResponseDecisionModel> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDecisionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        //some code that draws over the status bar
        Window window = this.getWindow();
        Drawable background = this.getResources().getDrawable(R.drawable.gradient_background , getTheme());
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(this.getResources().getColor(android.R.color.transparent , getTheme()));
        window.setBackgroundDrawable(background);
        setTheme(R.style.AppTheme);
        setContentView(view);

        //init the shared preferences
        preferences = getSharedPreferences("DATA_STORE" , MODE_PRIVATE);
        locationPermissionGranted = preferences.getBoolean("IS_PERMISSION_GRANTED" , false);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //init the viewmodel
        viewModel = new MainViewModel();

        mApiService = UtilsApi.getAPIService();
        initActivity();
    }

    private void initActivity() {
        binding.profileName.setText(SharedPrefManager.getInstance(getApplicationContext()).getUser().getName());
        binding.miniProfilPicture.setActualImageResource(R.drawable.screen_background);
        binding.backButtonDec.setOnClickListener(v -> finish());
        binding.reloadButton.setOnClickListener(v -> RefreshRecycler());
        startRecycler();
    }

    private void startRecycler() {
        DecisionRecycler = binding.decisionRecycler;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        DecisionRecycler.setHasFixedSize(false);
        DecisionRecycler.setLayoutManager(layoutManager);
        getDecicisions();

    }

    private void RefreshRecycler() {
        //refreshing decisions
        getDecicisions();
    }

   OutletClickListener listener = this::ShowDeciderDetailes;

    private void ShowDeciderDetailes(int position) {
        Dialog dialogbox = new Dialog(this);
        dialogbox.setContentView(R.layout.dialog_layout);
        Window window = dialogbox.getWindow();
        assert window != null;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = Objects.requireNonNull(window).getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        Objects.requireNonNull(dialogbox.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //initialising the dialog content
        SimpleDraweeView deciderPic = dialogbox.findViewById(R.id.decider_profile_picture);
        TextView fullname = dialogbox.findViewById(R.id.person_decider_name);
        TextView grade = dialogbox.findViewById(R.id.decider_grade);
        TextView title = dialogbox.findViewById(R.id.decider_title);
        TextView content = dialogbox.findViewById(R.id.decider_text_detail);
        fullname.setText(list.get(position).getUser().getName());
        grade.setText(list.get(position).getUser().getGrade());
        title.setText(list.get(position).getTitle());
        content.setText(list.get(position).getContent());
        //setting the dialog content Here you add your values missoum according to the position of the card you click
        deciderPic.setActualImageResource(R.drawable.screen_background);
        //this is after setting up the content
        dialogbox.show();
    }

    private void getDecicisions(){
        String wilaya = getWilaya();
        mApiService.getDecisions(wilaya).enqueue(new Callback<ResponseDecisions>() {

            @Override
            public void onResponse(Call<ResponseDecisions> call, Response<ResponseDecisions> response) {
                if(response.isSuccessful()){
                    list=response.body().getDecisions();
                    adapter = new DecisionElementAdapter((ArrayList<ResponseDecisionModel>) list, getApplicationContext() , listener);
                    DecisionRecycler.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    Log.d("missoum",response.body().getDecisions().toString());

                }else {
                    Toast.makeText(getApplicationContext(),response.message(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseDecisions> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private String getWilaya() {
        //get the current location
         viewModel.getDeviceLocationName(
                locationPermissionGranted ,
                fusedLocationProviderClient,
                this);
         return preferences.getString("WILAYA" , "");
    }
}
