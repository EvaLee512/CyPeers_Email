package com.hsdi.cypeers.util;

import android.util.Log;

import com.hsdi.cypeers.R;
import com.hsdi.cypeers.interfaces.LoginListener;
import com.hsdi.cypeers.interfaces.LoginOutListener;
import com.hsdi.cypeers.interfaces.PostFinishListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EvaLee on 2017/5/23.
 */
public class LoginOutHelper implements PostFinishListener{
    private static final String TAG = "LoginOutHelper_Eva";
    public static String TOKEN = "";
    private LoginOutListener mListener;

    public LoginOutHelper (LoginOutListener listener){
        mListener = listener;
    }


    public void doLoginOut(String access_token,String token){
        String[] keys=new String[]{"access_token","token"};
        String[] values=new String[]{"1001",token};
        Map<String,String> k_v_s=new HashMap<>();
        for(int i=0;i<keys.length;i++){
            k_v_s.put(keys[i],values[i]);
        }
        HttpUtil.doPost(HttpUtil.STR_LOGIN_OUT_URL,keys,k_v_s,this,HttpUtil.LOGIN_OUT_REQUEST_CODE);
    }


    @Override
    public void onFinish(boolean isSuccessful, JSONObject result, int request_code) {
        if(!isSuccessful){
            Log.i(TAG,"onFinish isSuccessful = "+isSuccessful);
            mListener.onLoginOutFinish(false, R.string.loginout_fail);
            return;
        }

        int _result_code=-1;

        try{
            _result_code = Integer.parseInt(result.getString("result_code"));
        }
        catch(Exception e){
            mListener.onLoginOutFinish(false,R.string.login_fail_label);
            return;
        }
        Log.i(TAG,"onFinish ->_result_code = "+_result_code);
        switch (_result_code){
            case HttpUtil.SUCCESS:
                mListener.onLoginOutFinish(true,R.string.loginout_success);
                break;

            default:
                mListener.onLoginOutFinish(false,R.string.login_fail_label);
                break;
    }
    }
}
