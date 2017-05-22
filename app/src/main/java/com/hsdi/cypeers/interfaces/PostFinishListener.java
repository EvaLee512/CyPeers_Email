package com.hsdi.cypeers.interfaces;

import org.json.JSONObject;

/**
 * Created by EvaLee on 2017/5/22.
 */
public interface PostFinishListener{
    void onFinish(boolean isSuccessful, JSONObject result, int request_code);
}