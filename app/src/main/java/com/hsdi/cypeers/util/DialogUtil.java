package com.hsdi.cypeers.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import com.hsdi.cypeers.activitys.RegisterActivity;

/**
 * Created by EvaLee on 2017/5/22.
 */
public class DialogUtil {
    private static Handler mHandler;
    private static final int START_ACTIVITY_CODE_LOGINTOREGISTER = 0001;

    public static void init(){
       mHandler = new Handler();
    }

    public static void showMsgDialog(final Activity activity,final String msg, final String register_email) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(msg);
                builder.setTitle("Prompt Dialog");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (RegisterActivity.isRegisterSuccess) {
                            Intent result = new Intent();
                            result.putExtra("User_Name", register_email);
                            activity.setResult(START_ACTIVITY_CODE_LOGINTOREGISTER, result);
                            activity.finish();
                        }
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });
    }
}
