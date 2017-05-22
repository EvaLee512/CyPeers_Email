package com.hsdi.cypeers.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hsdi.cypeers.R;

/**
 * Created by EvaLee on 2017/5/18.
 */
public class LoginActivity extends AppCompatActivity{

    private static final String TAG = "LoginActivity_Eva";
    private EditText eText_username;
    private EditText eText_password;
    private TextView tView_forgot;
    private Button btn_remmeberMe;
    private Button btn_register;
    private Button btn_login;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Black_NoTitleBar);
        getSupportActionBar().hide();
        setContentView(R.layout.login_layout);

        eText_username = (EditText)findViewById(R.id.edit_username);
        eText_password = (EditText)findViewById(R.id.edit_password);

        tView_forgot = (TextView)findViewById(R.id.textview_forget_password);

        btn_remmeberMe = (Button)findViewById(R.id.btn_remember);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_login = (Button)findViewById(R.id.btn_login);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loginActivityClick(View view){
        switch (view.getId()){
            case R.id.btn_remember:
                Log.i(TAG,"loginActivityClick-->btn_remember");

                break;

            case R.id.textview_forget_password:
                Log.i(TAG,"loginActivityClick-->textview_forget_password");

                break;

            case R.id.btn_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivityForResult(intent, 1001);
                Log.i(TAG, "loginActivityClick-->btn_register");

                break;

            case R.id.btn_login:
                Log.i(TAG,"loginActivityClick-->btn_login");

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
