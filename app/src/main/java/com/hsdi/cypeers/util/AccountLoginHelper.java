package com.hsdi.cypeers.util;

import android.util.Log;

import com.hsdi.cypeers.R;
import com.hsdi.cypeers.interfaces.AccountLoginListener;
import com.hsdi.cypeers.interfaces.LoginListener;
import com.hsdi.cypeers.interfaces.PostFinishListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EvaLee on 2017/5/24.
 */
public class AccountLoginHelper  implements PostFinishListener {

    private AccountLoginListener mListener;
    private static final String TAG = "LoginHelper_Eva";

    public AccountLoginHelper(AccountLoginListener listener){
        mListener = listener;
    }

    public void doAccountLogin(String access_token,String token,String address,String pwd,String mail_type){
        String[] keys=new String[]{"access_token","token","address","pwd","mail_type"};
        Log.i(TAG,"token = "+token+" address = "+address+" pwd = "+pwd+" address = "+address);
        String[] values=new String[]{"1001",token,address,pwd,mail_type};
        Map<String,String> k_v_s=new HashMap<>();
        for(int i=0;i<keys.length;i++){
            k_v_s.put(keys[i],values[i]);
        }
        HttpUtil.doPost(HttpUtil.STR_ADD_ACCOUNT_URL,keys,k_v_s,this,HttpUtil.ADD_ACCOUNT_REQUEST_CODE);
    }

    @Override
    public void onFinish(boolean isSuccessful, JSONObject result, int request_code) {
        if(!isSuccessful){
            mListener.onAccountLoginFinish(false, R.string.login_fail_label);
            return;
        }
        int _result_code=-1;
        try{
            _result_code = Integer.parseInt(result.getString("result_code"));
        }
        catch(Exception e){
            mListener.onAccountLoginFinish(false,R.string.login_fail_label);
            return;
        }
        Log.i(TAG,"AccountLoginHelper onFinish ->_result_code = "+_result_code);
        switch (_result_code){
            case HttpUtil.SUCCESS:
                mListener.onAccountLoginFinish(true,R.string.login_success_label);
                break;

            case HttpUtil.UNAUTHORIZED_CLIENT://1002
                mListener.onAccountLoginFinish(false, R.string.login_unauthorized_client_label);
                break;

            case HttpUtil.EMAIL_DUPLICATE://1003
                mListener.onAccountLoginFinish(false, R.string.login_email_duplicate_label);
                break;

            case HttpUtil.REGISTER_FAIL://1004
                mListener.onAccountLoginFinish(false, R.string.login_fail_label);
                break;

            case HttpUtil.PARAMS_ERROR://1005
                mListener.onAccountLoginFinish(false, R.string.login_params_error_label);
                break;

            case HttpUtil.USER_NOT_FOUND://1006
                mListener.onAccountLoginFinish(false, R.string.login_user_not_found_label);
                break;

            case HttpUtil.ACTIVATE_CODE_NOT_FOUND://1007
                mListener.onAccountLoginFinish(false, R.string.login_activate_code_not_found_label);
                break;

            case HttpUtil.NOT_ACTIVATED://1009
                mListener.onAccountLoginFinish(false, R.string.login_not_activated_label);
                break;

            case HttpUtil.OFFLINE://1010
                mListener.onAccountLoginFinish(false, R.string.login_offline_label);
                break;

            default:
                mListener.onAccountLoginFinish(false,R.string.login_fail_label);
                break;
        }
    }
}
