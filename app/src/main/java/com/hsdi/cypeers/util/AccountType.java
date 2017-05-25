package com.hsdi.cypeers.util;

/**
 * Created by EvaLee on 2017/5/23.
 */
public class AccountType {


    private int id; //信息ID
    private String account_type;   //信息标题
    private int avatar; //图片ID
    private String account_name; //详细信息



    //信息ID处理函数
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    //标题
    public void setAccountType(String account_type) {
        this.account_type = account_type;
    }
    public String getAccountType() {
        return account_type;
    }

    public void setAccountName(String account_name) {
        this.account_name = account_name;
    }
    public String getAccountName() {
        return account_name;
    }

    //图片
    public void setImage(int avatar) {
        this.avatar = avatar;
    }
    public int getImage() {
        return avatar;
    }
}
