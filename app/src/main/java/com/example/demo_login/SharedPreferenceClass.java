package com.example.demo_login;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {

    public Context context;

    public SharedPreferenceClass(Context context) {
        this.context = context;
    }

    public final String prefName = "My-pref";
    public final String serializableKey = "serializableData";
    public final String parcelableKey = "parcelableData";
    public final String defValue = "";
    public final String idKey = "Id";
    public final String nameKey = "Name";
    public final String genderKey = "Gender";
    public final String hobbyKey = "Hobby";
    public final String credentialUnmKey = "credentialUsername";
    public final String credentialPwdKey = "credentialPassword";
    public final String saveCredentialKey = "saveCredential";
    SharedPreferences sharedPref;

    public void getSharedPreferencesModeName(){
        sharedPref= context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    public void putId(int idValue) {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(idKey, idValue);
        editor.apply();
    }

    public int getInt(int idValue) {
        getSharedPreferencesModeName();
        return sharedPref.getInt(idKey, idValue);
    }

    public void putString(String nameValue) {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(nameKey, nameValue);
        editor.apply();
    }

    public String getString(String nameValue) {
        getSharedPreferencesModeName();
        return sharedPref.getString(nameKey, nameValue);
    }

    public void putBoolean(boolean genderValue) {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(genderKey, genderValue);
        editor.apply();
    }

    public boolean getBoolean(boolean genderValue) {
        getSharedPreferencesModeName();
        return sharedPref.getBoolean(genderKey, genderValue);
    }

    public void putArraylist(String hobbyValue) {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(hobbyKey, hobbyValue);
        editor.apply();
    }

    public String getArraylist(String hobbyValue) {
        getSharedPreferencesModeName();
        return sharedPref.getString(hobbyKey, hobbyValue);
    }

    public void putSerializableString(String serializableValue) {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(serializableKey, serializableValue);
        editor.apply();
    }

    public String getSerializableString() {
        getSharedPreferencesModeName();
        return sharedPref.getString(serializableKey, defValue);
    }

    public void putParcelableString(String parcelableValue) {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(parcelableKey, parcelableValue);
        editor.apply();
    }

    public String getParcelableString() {
        getSharedPreferencesModeName();
        return sharedPref.getString(parcelableKey, defValue);
    }

    public void putCredentials(String credentialUserName, String credentialPassword) {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(saveCredentialKey, true);
        editor.putString(credentialUnmKey, credentialUserName);
        editor.putString(credentialPwdKey, credentialPassword);
        editor.apply();
    }

    public String[] getCredentials() {
        getSharedPreferencesModeName();
        boolean isRemember = sharedPref.getBoolean(saveCredentialKey, false);
        String username = defValue;
        String password = defValue;
        if (isRemember) {
            username = sharedPref.getString(credentialUnmKey, defValue);
            password = sharedPref.getString(credentialPwdKey, defValue);
        }
        return new String[]{username, password};
    }

    public void clearPreferences() {
        getSharedPreferencesModeName();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }
}