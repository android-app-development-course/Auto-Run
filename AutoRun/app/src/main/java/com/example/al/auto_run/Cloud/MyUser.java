package com.example.al.auto_run.Cloud;

import cn.bmob.v3.BmobUser;

/**
 * Created by yyj on 2017/12/31.
 */

public class MyUser extends BmobUser {
    private String weight;
    private String sex;

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
