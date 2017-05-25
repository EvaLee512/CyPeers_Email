package com.hsdi.cypeers.interfaces;

/**
 * Created by EvaLee on 2017/5/24.
 */
public interface AccountLoginListener {

    void onAccountLoginFinish(boolean isSuccess,String message);
    void onAccountLoginFinish(boolean isSuccess,int message_id);
}
