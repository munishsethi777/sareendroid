package in.satya.sareenproperties.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by baljeetgaheer on 2/18/2016.
 */
public class PreferencesUtil {
    private Context mContext = null;
    private static PreferencesUtil mPreferencesUtil = null;
    private static Activity mActivity;
    public PreferencesUtil(Context context){
        mContext = context;
    }

    public static PreferencesUtil getInstance(Context context){
        if(mPreferencesUtil == null) {
            mPreferencesUtil = new PreferencesUtil(context);
        }
        return mPreferencesUtil;
    }
    public void setPreferences(String name , String value) {
        SharedPreferences.Editor editor  = mContext.getSharedPreferences(StringConstants.PREFS_NAME, mContext.MODE_PRIVATE).edit();
        editor.putString(name,value);
        editor.commit();
    }
    public void removePreferences(String name) {
        SharedPreferences.Editor editor  = mContext.getSharedPreferences(StringConstants.PREFS_NAME, mContext.MODE_PRIVATE).edit();
        editor.remove(name);
        editor.commit();
    }

    public String getPreferences(String name){
        SharedPreferences prefs = mContext.getSharedPreferences(StringConstants.PREFS_NAME, mContext.MODE_PRIVATE);
        String value = prefs.getString(name, null);//"No name defined" is the default value.
        return value;
    }
    public boolean getPreferencesBool(String name){
        SharedPreferences prefs = mContext.getSharedPreferences(StringConstants.PREFS_NAME, mContext.MODE_PRIVATE);
        boolean value = prefs.getBoolean(name, false);//"No name defined" is the default value.
        return value;
    }


    public void setPreferencesBool(String name , boolean value) {
        SharedPreferences.Editor editor  = mContext.getSharedPreferences(StringConstants.PREFS_NAME, mContext.MODE_PRIVATE).edit();
        editor.putBoolean(name,value);
        editor.commit();
    }

    public void resetPreferences(){
        SharedPreferences.Editor editor  = mContext.getSharedPreferences(StringConstants.PREFS_NAME, mContext.MODE_PRIVATE).edit();
        editor.clear().commit();
    }


    public void setLoggedInAdminSeq(long userSeq){
        setPreferences(StringConstants.LOGGED_IN_ADMIN_SEQ, String.valueOf(userSeq));
    }


    public int getLoggedInAdminSeq(){
        String value = getPreferences(StringConstants.LOGGED_IN_ADMIN_SEQ);
        int userSeq = 0;
        if(value != null){
            userSeq = Integer.parseInt(value);
        }
        return userSeq;
    }

  }
