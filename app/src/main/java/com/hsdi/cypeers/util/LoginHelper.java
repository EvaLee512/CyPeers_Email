package com.hsdi.cypeers.util;

import android.content.SharedPreferences;
import android.util.Log;
import com.hsdi.cypeers.R;
import com.hsdi.cypeers.interfaces.LoginListener;
import com.hsdi.cypeers.interfaces.PostFinishListener;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by EvaLee on 2017/5/22.
 */
public class LoginHelper implements PostFinishListener{
    private LoginListener mListener;
    private static final String TAG = "LoginHelper_Eva";
    public String login_token = "";

    public LoginHelper(LoginListener listener){
        mListener = listener;
    }

    public void doLogin(String email,String password){
        String[] keys=new String[]{"access_token","login_type","user_name","user_pwd"};
        String[] values=new String[]{"1001","email",email,password};
        Map<String,String> k_v_s=new HashMap<>();
        for(int i=0;i<keys.length;i++){
            k_v_s.put(keys[i],values[i]);
        }
        HttpUtil.doPost(HttpUtil.STR_LOGIN_URL,keys,k_v_s,this,HttpUtil.LOGIN_REQUEST_CODE);
    }

    @Override
    public void onFinish(boolean isSuccessful, JSONObject result, int request_code) {
        if(!isSuccessful){
            Log.i(TAG,"onFinish isSuccessful = "+isSuccessful);
            mListener.onLoginFinish(false, R.string.login_fail_label);
            return;
        }

        int _result_code=-1;


        try{
            _result_code = Integer.parseInt(result.getString("result_code"));
            login_token = result.getString("token");
            Log.i(TAG,"onFinish->login_token = "+login_token);
        }
        catch(Exception e){
            mListener.onLoginFinish(false,R.string.login_fail_label);
            return;
        }
Log.i(TAG,"onFinish ->_result_code = "+_result_code);
        switch (_result_code){
            case HttpUtil.SUCCESS:
                mListener.onLoginFinish(true,login_token);
                break;

            case HttpUtil.UNAUTHORIZED_CLIENT://1002
                mListener.onLoginFinish(false, R.string.login_unauthorized_client_label);
                break;

            case HttpUtil.EMAIL_DUPLICATE://1003
                mListener.onLoginFinish(false, R.string.login_email_duplicate_label);
                break;

            case HttpUtil.REGISTER_FAIL://1004
                mListener.onLoginFinish(false, R.string.login_fail_label);
                break;

            case HttpUtil.PARAMS_ERROR://1005
                mListener.onLoginFinish(false, R.string.login_params_error_label);
                break;

            case HttpUtil.USER_NOT_FOUND://1006
                mListener.onLoginFinish(false, R.string.login_user_not_found_label);
                break;

            case HttpUtil.ACTIVATE_CODE_NOT_FOUND://1007
                mListener.onLoginFinish(false, R.string.login_activate_code_not_found_label);
                break;

            case HttpUtil.NOT_ACTIVATED://1009
                mListener.onLoginFinish(false, R.string.login_not_activated_label);
                break;

            case HttpUtil.OFFLINE://1010
                mListener.onLoginFinish(false, R.string.login_offline_label);
                break;

            default:
                mListener.onLoginFinish(false,R.string.login_fail_label);
                break;

        }
    }
}
