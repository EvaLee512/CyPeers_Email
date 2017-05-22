package com.hsdi.cypeers.util;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by EvaLee on 2017/5/18.
 */
public class ToastUtil {

    public static void makeToast(Activity activity ,String msg){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show();
    }
}
