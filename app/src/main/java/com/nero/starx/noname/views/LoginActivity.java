package com.nero.starx.noname.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nero.starx.noname.R;
import com.nero.starx.noname.Utlis.Api.BaseApiService;
import com.nero.starx.noname.Utlis.Api.UtilsApi;
import com.nero.starx.noname.Utlis.SharedPrefManager;
import com.nero.starx.noname.Utlis.model.User;
import com.nero.starx.noname.databinding.ActivityLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    BaseApiService mApiService;

    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppTheme);
        setContentView(view);
        mApiService = UtilsApi.getAPIService();

        InitElements();
    }

    private void InitElements() {
        loginBinding.backgroundPic.setActualImageResource(R.drawable.screen_background);
        loginBinding.logoPic.setImageResource(R.drawable.ic_logo_white);
        loginBinding.loginButton.setOnClickListener( v -> LogIn());
        loginBinding.registerButton.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    private void LogIn() {
        //getting the email and password from the view
        String emailStr = loginBinding.emailField.getText().toString();
        String passwordStr = loginBinding.passwordField.getText().toString();
        if(!emailStr.isEmpty() && !passwordStr.isEmpty()){
            StartLogin(emailStr , passwordStr);
        }else{
            Toast.makeText(this, "Remplir votre email et mot de pass et ressayer", Toast.LENGTH_SHORT).show();
        }
        startActivity(new Intent(LoginActivity.this, MainScreenActivity.class));
        finish();

    }

    private void StartLogin(String emailStr, String passwordStr) {
      mApiService.loginRequest(emailStr,passwordStr).enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              if (response.isSuccessful()) {
                  try {
                      JSONObject jsonRESULTS = new JSONObject(response.body().string());
                      if (jsonRESULTS.getString("success").equals("true")) {
                          SharedPrefManager.getInstance(LoginActivity.this)
                                  .saveUser(new User(jsonRESULTS.getJSONObject("user").getInt("id"),
                                          jsonRESULTS.getJSONObject("user").getString("email")
                                          ,jsonRESULTS.getJSONObject("user").getString("name")));
                         startActivity(new Intent(LoginActivity.this, MainScreenActivity.class));
                         finish();
                      } else {
                          String error_message = jsonRESULTS.getString("success");
                          Toast.makeText(getApplicationContext(), error_message, Toast.LENGTH_SHORT).show();
                      }
                  } catch (JSONException | IOException e) {
                      e.printStackTrace();
                  }

              }

          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {
              Log.e("debug", "onFailure: ERROR > " + t.toString());
          }
      });


    }






}

