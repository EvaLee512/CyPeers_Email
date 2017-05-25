package com.hsdi.cypeers.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.hsdi.cypeers.R;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.SharedPreferences;
import com.hsdi.cypeers.interfaces.LoginOutListener;
import com.hsdi.cypeers.util.DialogUtil;
import com.hsdi.cypeers.util.LoginHelper;
import com.hsdi.cypeers.util.LoginOutHelper;

/**
 * Created by EvaLee on 2017/5/18.
 */
public class MainActivity extends AppCompatActivity implements LoginOutListener{
    private LoginOutHelper mLoginOutHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginOutHelper = new LoginOutHelper(this);
        DialogUtil.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loginout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_quit:
                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(),Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");
                String access_token = "1001";
                if(!token.equals("")){
                    mLoginOutHelper.doLoginOut(access_token,token);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoginOutFinish(boolean isSuccess, int message_id) {
        //µÇ³ö³É¹¦½«TOKENÉ¾µô
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token","");
        editor.commit();
        finish();
    }
}
