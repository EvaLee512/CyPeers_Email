package com.hsdi.cypeers.activitys;

/**
 * Created by EvaLee on 2017/5/18.
 */
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hsdi.cypeers.R;

public class WelcomeActivity extends Activity {
    private final static String TAG = "WelcomeActivity_eva";
    private ImageView welcomeImage;
    private Thread waitThread;
    private int pageNumber = 1;
    SharedPreferences sharedPreferences_isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是否是首次登录应用，yes->WelcomeActivity   No->LoginActivity
        sharedPreferences_isFirst = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        //获取value
        boolean isFirst = sharedPreferences_isFirst.getBoolean("isFirst",true);
        if(isFirst){
            //设置全屏无标题
            setTheme(android.R.style.Theme_Black_NoTitleBar_Fullscreen);
            setContentView(R.layout.welcome_layout);
            Log.i(TAG,"onCreate()------->");
            welcomeImage = (ImageView) findViewById(R.id.welcome_iv);
            //延迟发送消息，跳转到登录主界面
            handler.sendEmptyMessageDelayed(0,3000);
        }else {
            turnToHomePage();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            turnToHomePage();
            super.handleMessage(msg);
        }
    };
    /**
     * 跳转到主界面
     */
    private void turnToHomePage() {
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        WelcomeActivity.this.startActivity(intent);
        //获取编辑器
        SharedPreferences.Editor editor = sharedPreferences_isFirst.edit();
        editor.putBoolean("isFirst",false);
        editor.commit();
        WelcomeActivity.this.finish();
    }
}