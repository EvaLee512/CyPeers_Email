package com.hsdi.cypeers.interfaces;

/**
 * Created by EvaLee on 2017/5/22.
 */
public interface RegisterListener {
    void onRegisterFinish(boolean isSuccess,String message);
    void onRegisterFinish(boolean isSuccess,int message_id);
}