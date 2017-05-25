package com.hsdi.cypeers.util;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hsdi.cypeers.interfaces.PostFinishListener;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import org.json.JSONObject;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by EvaLee on 2017/5/18.
 */
public class HttpUtil extends AppCompatActivity{
    private final static String TAG = "HttpUtil_Eva";
    public final static int SUCCESS = 1001;
    public final static int UNAUTHORIZED_CLIENT = 1002;
    public final static int EMAIL_DUPLICATE = 1003;
    public final static int REGISTER_FAIL = 1004;
    public final static int PARAMS_ERROR = 1005;
    public final static int USER_NOT_FOUND = 1006;
    public final static int ACTIVATE_CODE_NOT_FOUND = 1007;
    public final static int ACTIVATED = 1008;

    public final static int NOT_ACTIVATED = 1009;
    public final static int OFFLINE = 1010;

    public final static String STR_REGISTER_URL = "http://192.168.0.130:3333/restful_api/register/register_by_mail";
    public static final int REGISTER_REQUEST_CODE = 101010;
    public final static String STR_LOGIN_URL = "http://192.168.0.130:3333/restful_api/login/do_login";
    public static final int LOGIN_REQUEST_CODE = 121212;
    public final static String STR_LOGIN_OUT_URL = "http://192.168.0.130:3333/restful_api/login/do_logout";
    public static final int LOGIN_OUT_REQUEST_CODE = 131313;




    private static final ExecutorService threadPool= Executors.newFixedThreadPool(5);

    //OkHttp 请求接口
    public static void doPost(String url, String[] keys, Map<String,String> keyValues, final PostFinishListener listener,final int request_code){
        final OkHttpClient client=new OkHttpClient();
        FormEncodingBuilder formDatas=new FormEncodingBuilder();
        for(String key:keys){
            formDatas.add(key,keyValues.get(key));
        }
        RequestBody requestBody=formDatas.build();
        final Request request=new Request.Builder().url(url).post(requestBody).build();
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.i(TAG,"doPost submit-------- finish");
                    Response response = client.newCall(request).execute();
                    listener.onFinish(response.isSuccessful(),new JSONObject( response.body().string()),request_code);
                }catch(Exception e){
                    e.printStackTrace();
                    listener.onFinish(false,null,request_code);
                }
            }
        });
    }
}
