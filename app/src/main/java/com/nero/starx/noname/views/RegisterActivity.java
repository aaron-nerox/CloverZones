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
import com.nero.starx.noname.databinding.ActivityRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    BaseApiService mApiService;
    private ActivityRegisterBinding registerBinding;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Fresco.initialize(getApplicationContext());
            registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
            View view = registerBinding.getRoot();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setTheme(R.style.AppTheme);
            setContentView(view);
            mApiService = UtilsApi.getAPIService();


            InitElements();
        }

        private void InitElements() {
            registerBinding.backgroundPic.setActualImageResource(R.drawable.screen_background);
            registerBinding.logoPic.setImageResource(R.drawable.ic_logo_white);
            registerBinding.registerButton.setOnClickListener( v -> LogIn());

            registerBinding.loginButton.setOnClickListener((v) -> {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            });
        }

        private void LogIn() {
            //getting the email and password from the view
            String emailStr = registerBinding.emailField.getText().toString();
            String passwordStr = registerBinding.passwordField.getText().toString();
            String name=registerBinding.nameField.getText().toString();
            if(!emailStr.isEmpty() && !passwordStr.isEmpty()){
                StartLogin(name,emailStr , passwordStr);
            }else{
                Toast.makeText(this, "Remplir votre email et mot de pass et ressayer", Toast.LENGTH_SHORT).show();
            }
        }

        private void StartLogin(String name,String emailStr, String passwordStr) {
            mApiService.registerRequest(name,
                    emailStr,passwordStr
                    )
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()){

                                try {
                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                    if (jsonRESULTS.getString("success").equals("true")){
                                        Toast.makeText(getApplicationContext(), "register successfully", Toast.LENGTH_SHORT).show();
                                        SharedPrefManager.getInstance(RegisterActivity.this)
                                                .saveUser(new User(jsonRESULTS.getJSONObject("user").getInt("id"),
                                                        jsonRESULTS.getJSONObject("user").getString("email")
                                                        ,jsonRESULTS.getJSONObject("user").getString("name")));
                                        startActivity(new Intent(RegisterActivity.this, MainScreenActivity.class));

                                        Intent intent=new Intent(getApplicationContext(), MainScreenActivity.class);
                                        intent.putExtra("name",jsonRESULTS.getString("name"));
                                        intent.putExtra("email",jsonRESULTS.getString("email"));
                                        startActivity(intent);
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
                            Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                            Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    });




        }
    @Override
    protected void onStart() {
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainScreenActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    }

