package com.hsdi.cypeers.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.hsdi.cypeers.R;
import com.hsdi.cypeers.interfaces.LoginListener;
import com.hsdi.cypeers.util.DialogUtil;
import com.hsdi.cypeers.util.LoginHelper;
import com.hsdi.cypeers.util.ToastUtil;

/**
 * Created by EvaLee on 2017/5/18.
 */
public class LoginActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener ,LoginListener {

    private static final String TAG = "LoginActivity_Eva";
    private static final int START_ACTIVITY_CODE_LOGINTOREGISTER = 0001;
    private EditText eText_login_email;
    private EditText eText_login_password;
    private CheckBox btn_login_rememberMe;

    private String login_email;
    private String login_password;
    private boolean isRememberMe;

    private LoginHelper mLoginHelper;
    public static boolean isLoginSuccess = false;
    private boolean isRegisterStatus = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Black_NoTitleBar);
        getSupportActionBar().hide();
        setContentView(R.layout.login_layout);

        eText_login_email = (EditText) findViewById(R.id.edit_email);
        eText_login_password = (EditText) findViewById(R.id.edit_password);

        btn_login_rememberMe = (CheckBox) findViewById(R.id.btn_remember);
        btn_login_rememberMe.setOnCheckedChangeListener(this);

        mLoginHelper = new LoginHelper(this);
        DialogUtil.init();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void loginActivityClick(View view) {
        switch (view.getId()) {

            case R.id.textview_forget_password:
                Log.i(TAG, "loginActivityClick-->textview_forget_password");

                break;

            case R.id.btn_register:
                isRegisterStatus = true;
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivityForResult(intent, START_ACTIVITY_CODE_LOGINTOREGISTER);
                Log.i(TAG, "loginActivityClick-->btn_register");

                break;

            case R.id.btn_login:
                isRegisterStatus = false;
                Log.i(TAG, "loginActivityClick-->btn_login");
                if (getUserInfo()) {
                    mLoginHelper.doLogin(login_email,login_password);
                }
                break;

        }
    }

    public boolean getUserInfo() {

        String str_email = eText_login_email.getText().toString();
        String str_password = eText_login_password.getText().toString();

        if (str_email.length() == 0 || str_password.length() == 0) {
            ToastUtil.makeToast(LoginActivity.this, "The Email or Password can't null!!");
            eText_login_email.setText("");
            eText_login_password.setText("");
            return false;
        } else if (str_email.length() != 0 && str_password.length() < 6) {
            ToastUtil.makeToast(LoginActivity.this, "The Password length at least 6 character!");
            eText_login_email.setText("");
            eText_login_password.setText("");
            return false;
        } else {
            login_email = str_email;
            login_password = str_password;
            Log.i(TAG, "getLoginInfo-->login_email = " + login_email + " login_password = " + login_password);
            return true;
        }
    }

    @Override
    public void onLoginFinish(boolean isSuccess, String message) {
        if(isSuccess){
            isLoginSuccess = isSuccess;
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("user_name",login_email);
            intent.putExtra("user_pwd",login_password);
            LoginActivity.this.startActivity(intent);
        }else {
            DialogUtil.showMsgDialog(LoginActivity.this,message,"");
        }
    }

    @Override
    public void onLoginFinish(boolean isSuccess, int message_id) {
        onLoginFinish(isSuccess, getResources().getString(message_id));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            ToastUtil.makeToast(LoginActivity.this, "This checkbox is: checked");
        } else {
            ToastUtil.makeToast(LoginActivity.this, "This checkbox is: unchecked");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        eText_login_email.setText(data.getExtras().get("User_Name").toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!isRegisterStatus){//login activity
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
