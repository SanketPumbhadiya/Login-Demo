package com.example.demo_login;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.demo_login.model.ModelLoginCredentials;

public class SharedPreferenceClass {

    public Context context;
    public final String prefName = "My-pref";
    public final String defValue = "";
    public final String Key_Login_UserName = "credentialUsername";
    public final String Key_Login_Password = "credentialPassword";
    public final String Key_Login_IsSaved = "saveCredential";
    SharedPreferences sharedPref;

    public SharedPreferenceClass(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }


    public void setLoginCredentials(String credentialUserName, String credentialPassword) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Key_Login_IsSaved, true);
        editor.putString(Key_Login_UserName, credentialUserName);
        editor.putString(Key_Login_Password, credentialPassword);
        editor.apply();
    }

    public ModelLoginCredentials getLoginCredentials() {
        boolean isRemember = sharedPref.getBoolean(Key_Login_IsSaved, false);
        String username = defValue;
        String password = defValue;
        if (isRemember) {
            username = sharedPref.getString(Key_Login_UserName, defValue);
            password = sharedPref.getString(Key_Login_Password, defValue);
        }
        ModelLoginCredentials loginCredentials = new ModelLoginCredentials();
        loginCredentials.setUserName(username);
        loginCredentials.setPassword(password);
        return loginCredentials;
    }
    public void clearLoginCredentials(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(Key_Login_UserName);
        editor.remove(Key_Login_Password);
        editor.apply();
    }
}