package com.hsdi.cypeers.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hsdi.cypeers.R;
import com.hsdi.cypeers.util.HttpUtil;
import com.hsdi.cypeers.util.RegisterHelper;
import com.hsdi.cypeers.util.ToastUtil;


/**
 * Created by EvaLee on 2017/5/18.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterHelper.RegisterListener {

    private static final String TAG = "LoginActivity_Eva";
    private EditText eText_register_nickname;
    private EditText eText_register_username;
    private EditText eText_register_email;
    private EditText eText_register_password;

    private String register_nickname;
    private String register_username;
    private String register_email;
    private String register_password;

    private Button btn_signup;
    HttpUtil mHttpUtil;

    private RegisterHelper mHelper;
    private Handler mHandler;

    private boolean isRegisterSuccess = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new RegisterHelper(this);
        mHandler = new Handler();
        setTheme(android.R.style.Theme_NoTitleBar);
        getSupportActionBar().hide();
        setContentView(R.layout.register_layout);

        eText_register_nickname = (EditText) findViewById(R.id.edit_register_nickname);
        eText_register_username = (EditText) findViewById(R.id.edit_register_username);
        eText_register_email = (EditText) findViewById(R.id.edit_register_email);
        eText_register_password = (EditText) findViewById(R.id.edit_register_password);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(this);

        mHttpUtil = new HttpUtil();
    }

    @Override
    public void onClick(View v) {
        //ÐÅÏ¢ok
        if (getRegisterInfo()) {
            Log.i(TAG, "sendRegisterPost-->");
            mHelper.doRegister(register_nickname, register_username, register_email, register_password);
        }
    }

    private boolean getRegisterInfo() {
        String str_nickname = eText_register_nickname.getText().toString();
        String str_username = eText_register_username.getText().toString();
        String str_email = eText_register_email.getText().toString();
        String str_password = eText_register_password.getText().toString();

        if (str_username.length() == 0 || str_password.length() == 0) {
            ToastUtil.makeToast(RegisterActivity.this, "The Email or Password can't null!!");
            eText_register_email.setText("");
            eText_register_password.setText("");
            return false;
        } else if (str_username.length() != 0 && str_password.length() < 6) {
            ToastUtil.makeToast(RegisterActivity.this, "The Password length at least 6 character!");
            eText_register_email.setText("");
            eText_register_password.setText("");
            return false;
        } else {
            register_nickname = str_nickname;
            register_username = str_username;
            register_email = str_email;
            register_password = str_password;
            Log.i(TAG, "getRegisterInfo-->register_nickname = " + register_nickname + " register_username = " + register_username +
                    " register_email = " + register_email + " register_password = " + register_password);
            return true;
        }
    }

    @Override
    public void onRegisterFinish(boolean isSuccess, String msg) {
        if (isSuccess) {
            showMsgDialog(msg);
        } else {
            showMsgDialog(msg);
        }
    }

    @Override
    public void onRegisterFinish(boolean isSuccess, int error_id) {
        isRegisterSuccess = isSuccess;
        onRegisterFinish(isSuccess, getResources().getString(error_id));
    }

    public void showMsgDialog(final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(msg);
                builder.setTitle("Prompt Dialog");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isRegisterSuccess) {
                            Intent result = new Intent();
                            result.putExtra("User_Name", register_email);
                            RegisterActivity.this.setResult(1001, result);
                            RegisterActivity.this.finish();
                        }
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }
}
