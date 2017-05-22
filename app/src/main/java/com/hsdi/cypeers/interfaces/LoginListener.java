package com.hsdi.cypeers.interfaces;

/**
 * Created by EvaLee on 2017/5/22.
 */
public interface LoginListener {
    void onLoginFinish(boolean isSuccess,String message);
    void onLoginFinish(boolean isSuccess,int message_id);
}
