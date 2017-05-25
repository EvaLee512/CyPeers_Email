package com.hsdi.cypeers.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hsdi.cypeers.R;
import com.hsdi.cypeers.interfaces.AccountLoginListener;
import com.hsdi.cypeers.util.AccountLoginHelper;
import com.hsdi.cypeers.util.DialogUtil;
import com.hsdi.cypeers.util.LoginHelper;
import com.hsdi.cypeers.util.ToastUtil;

/**
 * Created by EvaLee on 2017/5/23.
 */
public class AccountLoginActivity extends AppCompatActivity implements View.OnClickListener,AccountLoginListener{
    private TextView tv_account_type;
    private Button account_login;
    private Button account_login_cancel;
    private String account_type;
    private EditText edit_email;
    private EditText edit_password;
    private String email;
    private String pwd;
    AccountLoginHelper mAccountLoginHelper;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_account_layout);
        tv_account_type = (TextView)findViewById(R.id.account_title);
        account_login = (Button)findViewById(R.id.account_login);
        account_login_cancel = (Button)findViewById(R.id.account_login_cancel);

        account_login.setOnClickListener(this);
        account_login_cancel.setOnClickListener(this);

        edit_email = (EditText)findViewById(R.id.edit_email);
        edit_password = (EditText)findViewById(R.id.edit_password);

        Intent intent = getIntent();
        account_type = intent.getStringExtra("account_type");

        if (account_type != null) {
            tv_account_type.setText(account_type);
        }

        mAccountLoginHelper = new AccountLoginHelper(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.account_login:
                email = edit_email.getText().toString();
                pwd = edit_password.getText().toString();

                if(!email.equals("") && !pwd.equals("")){
                    //根据account_type判断是那种账号
                    sharedPreferences = getSharedPreferences(getPackageName(),MODE_PRIVATE);
                    String token = sharedPreferences.getString("token","");
                    Log.i("AccountLoginActivity","account_type = "+account_type+" email = "+email+" pwd = "+pwd+" token = "+token);
                    mAccountLoginHelper.doAccountLogin("access_token",token,email,pwd,account_type);
                }else {
                    ToastUtil.makeToast(this,"Please check your account!");
                }
                break;

            case R.id.account_login_cancel:
                finish();
                break;
        }
    }

    @Override
    public void onAccountLoginFinish(boolean isSuccess, String message) {
        if (isSuccess){
            Intent result = new Intent(AccountLoginActivity.this,MainActivity.class);
            result.putExtra("account_type", account_type);
            result.putExtra("email", email);
            result.putExtra("pwd", pwd);
            setResult(1004, result);
            finish();
        }else {
            DialogUtil.showMsgDialog(AccountLoginActivity.this,message,"");
        }

    }

    @Override
    public void onAccountLoginFinish(boolean isSuccess, int message_id) {
        onAccountLoginFinish(isSuccess,getResources().getString(message_id));
    }
}
