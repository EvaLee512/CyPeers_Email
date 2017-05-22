package com.hsdi.cypeers.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Handler;

import com.hsdi.cypeers.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EvaLee on 2017/5/19.
 */
public class RegisterHelper implements HttpUtil.PostFinishListener {

    private RegisterListener mListener;

    public RegisterHelper(RegisterListener listener)
    {
        mListener=listener;
    }

    public interface RegisterListener {
        void onRegisterFinish(boolean isSuccess,String message);
        void onRegisterFinish(boolean isSuccess,int message_id);
    }

    public void doRegister(String nick_name,String user_name,String email,String password){
        String[] keys=new String[]{"access_token","user_nick_name","user_name","user_pwd","user_email"};
        String[] values=new String[]{"1001",nick_name,user_name,password,email};
        Map<String,String> k_v_s=new HashMap<>();
        for(int i=0;i<keys.length;i++){
            k_v_s.put(keys[i],values[i]);
        }
        HttpUtil.doPost(HttpUtil.STR_REGISTER_URL,keys,k_v_s,this,HttpUtil.REGISTER_REQUEST_CODE);
    }

    @Override
    public void onFinish(boolean isSuccessful, JSONObject result, int request_code) {
        if(!isSuccessful){
            mListener.onRegisterFinish(false,R.string.register_Server_fail_label);
            return;
        }

        int _result_code=-1;

        try{
            _result_code = Integer.parseInt(result.getString("result_code"));
        }
        catch(Exception e){
            mListener.onRegisterFinish(false,R.string.register_Server_fail_label);
            return;
        }

        switch (_result_code){
            case HttpUtil.SUCCESS:
                mListener.onRegisterFinish(true,R.string.register_success_label);
                break;

            case HttpUtil.UNAUTHORIZED_CLIENT:
                mListener.onRegisterFinish(false, R.string.register_unauthorized_client_label);
                break;

            case HttpUtil.EMAIL_DUPLICATE:
                mListener.onRegisterFinish(false, R.string.register_email_duplicate_label);
                break;

            case HttpUtil.REGISTER_FAIL:
                mListener.onRegisterFinish(false, R.string.register_register_fail_label);
                break;

            case HttpUtil.PARAMS_ERROR:
                mListener.onRegisterFinish(false, R.string.register_params_error_label);
                break;

            default:
                mListener.onRegisterFinish(false,R.string.register_register_fail_label);
                break;

        }

    }

}
