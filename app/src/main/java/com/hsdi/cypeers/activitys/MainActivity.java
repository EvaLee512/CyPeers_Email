package com.hsdi.cypeers.activitys;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.hsdi.cypeers.R;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.SharedPreferences;
import com.hsdi.cypeers.interfaces.LoginOutListener;
import com.hsdi.cypeers.util.DialogUtil;
import com.hsdi.cypeers.util.LoginHelper;
import com.hsdi.cypeers.util.LoginOutHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.content.Intent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.hsdi.cypeers.util.AccountType;


/**
 * Created by EvaLee on 2017/5/18.
 */
public class MainActivity extends AppCompatActivity implements LoginOutListener{
    private LoginOutHelper mLoginOutHelper;

private static final int START_ACTIVITY_CODE_LOGINTOREGISTER = 0001;
    private static final String TAG = "MainActivity_Eva";
    private static final int ACCOUNT_NUMBER = 6;
    ListView mAccountType_listview;
    //声明一个list,动态存储要显示的信息
    private List<AccountType> mlistAccountType = new ArrayList<AccountType>();
    private int[] account_type_images = new int[]{
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,R.mipmap.ic_launcher
    };
    String[] account_type_name;

    private static final int GMAIL_ACCOUNT = 1000;
    private static final int OUTLOOK_ACCOUNT = 1001;
    private static final int WANGYI_ACCOUNT = 1002;
    private static final int EXCHANGE_ACCOUNT = 1003;
    private static final int YAHOO_ACCOUNT = 1004;
    private static final int OTHER_ACCOUNT = 1005;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoginOutHelper = new LoginOutHelper(this);
        DialogUtil.init();

        account_type_name = new String[]{
                getString(R.string.google_account).toString(),getString(R.string.outlook_account).toString(),
                getString(R.string.wangyi_account).toString(),getString(R.string.exchange_account).toString(),
                getString(R.string.yahoo_account).toString(),getString(R.string.others_account).toString()
        };

        mAccountType_listview = (ListView)findViewById(R.id.accountType_listview);
        setAccountType();
        mAccountType_listview.setAdapter(new ListviewAdapter(mlistAccountType));

        //单击处理函数
        mAccountType_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountType getObject = mlistAccountType.get(position);   //通过position获取所点击的对象
                int infoId = getObject.getId(); //获取信息id
                String infoTitle = getObject.getAccountType();    //获取信息标题
                String account_name = getObject.getAccountName();    //获取信息标题
                //Toast显示测试
                Log.i(TAG,"setOnItemClickListener--->infoId = "+infoId+" infoTitle = "+infoTitle+" account_name = "+account_name);
                switch (infoId){
                    case GMAIL_ACCOUNT:

                    case OUTLOOK_ACCOUNT:

                    case WANGYI_ACCOUNT:

                    case EXCHANGE_ACCOUNT:

                    case YAHOO_ACCOUNT:

                    case OTHER_ACCOUNT:
                        Toast.makeText(MainActivity.this, "You have beeb choose:"+infoTitle,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,AccountLoginActivity.class);
                        intent.putExtra("account_type",infoTitle);
                        startActivityForResult(intent,1004);
                        break;

                }
            }
        });
    }

    public class ListviewAdapter extends BaseAdapter {
        View[] itemViews;

        public ListviewAdapter(List<AccountType> mAccountType){
            itemViews = new View[mAccountType.size()];
            for(int i=0;i<mAccountType.size();i++){
                AccountType getInfo=(AccountType)mAccountType.get(i);    //获取第i个对象
                //调用makeItemView，实例化一个Item
                itemViews[i]=makeItemView(
                        getInfo.getAccountType(), getInfo.getAccountName(),getInfo.getImage()
                );
            }
        }
        @Override
        public int getCount() {
            return itemViews.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                return itemViews[position];
            }
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return itemViews[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    public void setAccountType(){
        mlistAccountType.clear();
        for (int i=0;i<ACCOUNT_NUMBER;i++){
            AccountType accountType = new AccountType();
            accountType.setId(1000+i);
            accountType.setAccountType(account_type_name[i]);
            accountType.setAccountName("");
            accountType.setImage(account_type_images[i]);
            mlistAccountType.add(accountType);
        }

    }
    //绘制Item的函数
    private View makeItemView(String strTitle,String account_name,int resId) {
        // 使用View的对象itemView与R.layout.item关联
        LayoutInflater inflater = (LayoutInflater) MainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.add_account_listview, null);
        TextView  title = (TextView) itemView.findViewById(R.id.account_type_name);
        TextView  tv_account_name = (TextView) itemView.findViewById(R.id.account_name);
        ImageView  image = (ImageView) itemView.findViewById(R.id.email_account_img);
        title.setText(strTitle);    //填入相应的值
        tv_account_name.setText(account_name);    //填入相应的值
        image.setImageResource(resId);
        mViewMapping.put(strTitle,itemView);
        return itemView;
    }

    private Map<String,View> mViewMapping=new HashMap<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView  tv_account_name;
        if(data != null){
            Log.i(TAG,"data.getExtras().get(\"account_type\") = "+data.getExtras().get("account_type"));
            tv_account_name = (TextView)   mViewMapping.get(data.getExtras().get("account_type").toString()).findViewById(R.id.account_name);
            if(tv_account_name != null){
                tv_account_name.setText(data.getExtras().get("email").toString());
                Log.i("MainActivity_Eva","email = "+data.getExtras().get("email").toString());

                AccountType accountType = new AccountType();
                tv_account_name.setText(data.getExtras().get("email").toString());    //填入相应的值
                Log.i(TAG,"tv_account_name = "+tv_account_name.getText().toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loginout,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_quit:
                SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(),Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("token","");
                String access_token = "1001";
                if(!token.equals("")){
                    mLoginOutHelper.doLoginOut(access_token,token);
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoginOutFinish(boolean isSuccess, int message_id) {
        //登出成功将TOKEN删掉
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token","");
        editor.commit();
        finish();
    }
}
