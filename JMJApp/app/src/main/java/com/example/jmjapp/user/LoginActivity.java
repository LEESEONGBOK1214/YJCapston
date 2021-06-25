package com.example.jmjapp.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.jmjapp.JMJApplication;
import com.example.jmjapp.dto.Shop;
import com.example.jmjapp.network.Server;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.*;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private Call<ResponseBody> responseBodyCall;

    Button btn_signup;
    LinearLayout naver_login;

    Button btn_logout;
    Button btn_login;

    OAuthLogin mOAuthLoginModule;
    Context mContext;

    EditText et_login_id;
    EditText et_login_pw;

    FirebaseAuth mAuth = null;
    GoogleSignInClient mGoogleSignInClient;


    private AlertDialog dialog;
    private static final int RC_SIGN_IN = 9001;
    private SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.jmjapp.R.layout.activity_login2);

        mContext = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(com.example.jmjapp.R.id.login_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(com.example.jmjapp.R.drawable.close);

        et_login_id = (EditText) findViewById(com.example.jmjapp.R.id.et_login_id);
        et_login_pw = (EditText) findViewById(com.example.jmjapp.R.id.et_login_pw);
        btn_login = findViewById(com.example.jmjapp.R.id.btn_login);
        naver_login = findViewById(com.example.jmjapp.R.id.naver_login);
        btn_logout = findViewById(com.example.jmjapp.R.id.btn_logout);

        btn_login.setOnClickListener(v -> {
            if (et_login_id.getText().toString().length() == 0 || et_login_pw.getText().toString().length() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                dialog = builder.setMessage("아이디와 비밀번호를 확인해 주세요.").setPositiveButton("확인", null).create();
                dialog.show();
            } else {
                String id = et_login_id.getText().toString();
                String password = et_login_pw.getText().toString();
                Map<String, String> map = new HashMap();
                map.put("id", id);
                map.put("password", password);

                responseBodyCall = Server.getInstance().getApi().LoginOne(map);
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @SneakyThrows
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() == 200) { // 로그인성공
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Log.d("result ", String.valueOf(jsonObject));
                            String jwt = (String) jsonObject.get("access_token");
                            String role = (String) jsonObject.get("role");
                            Log.d("jsonobject :: jwt >> ", jwt);
                            Log.d("jsonobject :: role >> ", role);
                            Log.d("jsonobject :: userid >> ", et_login_id.getText().toString());

                            if (role.equals("ROLE_USER")) {
                                SharedPreferences pref = getSharedPreferences("auth", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("token", jwt);
                                editor.putString("user_id", et_login_id.getText().toString());
                                editor.putString("role", "ROLE_USER");
                                editor.apply();

                                ((JMJApplication) getApplication()).setId(et_login_id.getText().toString());
                                ((JMJApplication) getApplication()).setJwt(jwt);

                                Log.d("result : ", "일반사용자 로그인성공!");
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("로그인되었습니다").setPositiveButton("확인", (dialog, which) -> {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }).create();
                                builder.setCancelable(false);
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                dialog = builder.setMessage("아이디와 비밀번호를 확인해 주세요.").setPositiveButton("확인", null).create();
                                dialog.show();
                            }
                        } else if (response.code() == 400) {
                            Log.d("result @@@: ", "로그인실패");
                            Log.d("resul@#$%#@t : ", response.errorBody().string());
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            dialog = builder.setMessage("아이디와 비밀번호를 확인해 주세요.").setPositiveButton("확인", null).create();
                            dialog.show();
                        } else {
                            Log.d("resul@#$%#@t : ", response.errorBody().string());
                            Log.d("result : ", "연결실패");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        naver_login.setOnClickListener(v -> {
            mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(
                    mContext
                    , getString(com.example.jmjapp.R.string.naver_client_id)
                    , getString(com.example.jmjapp.R.string.naver_client_secret)
                    , getString(com.example.jmjapp.R.string.naver_client_name)
            );

            @SuppressLint("HandlerLeak")
            OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
                @Override
                public void run(boolean success) {
                    if (success) {
                        String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                        String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                        long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                        String tokenType = mOAuthLoginModule.getTokenType(mContext);

                        Log.i("LoginData", "accessToken : " + accessToken);
                        Log.i("LoginData", "refreshToken : " + refreshToken);
                        Log.i("LoginData", "expiresAt : " + expiresAt);
                        Log.i("LoginData", "tokenType : " + tokenType);
                    } else {
                        String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                        String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                        Toast.makeText(mContext, "errorCode:" + errorCode + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                    }
                }

                ;
            };
            mOAuthLoginModule.startOauthLoginActivity(LoginActivity.this, mOAuthLoginHandler);


        });

        btn_signup = (Button) findViewById(com.example.jmjapp.R.id.btn_signup);
        btn_signup.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
            finish();
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (responseBodyCall != null)
            responseBodyCall.cancel();
    }


}