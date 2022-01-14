package com.ak453.machinetestapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {
    SharedPreferences pref;
    public SPUtil(Context context) {
        pref = context.getSharedPreferences("mtest_app",Context.MODE_PRIVATE);
    }

    public void setSubName(String name){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name_subscriber",name);
        editor.apply();
    }

    public void setSubEmail(String name){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("email_subscriber",name);
        editor.apply();
    }

    public String getSubName() {
        return pref.getString("name_subscriber","Unable to Fetch");
    }

    public String getSubEmail() {
        return pref.getString("email_subscriber","");
    }

    public void removeSubEmail(){
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("email_subscriber");
        editor.commit();
    }

}
