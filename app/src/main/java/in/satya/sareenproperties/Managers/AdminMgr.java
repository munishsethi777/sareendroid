package in.satya.sareenproperties.Managers;

import android.content.Context;

import org.json.JSONObject;


import in.satya.sareenproperties.BusinessObjects.Admin;
import in.satya.sareenproperties.DataStores.AdminDataStore;
import in.satya.sareenproperties.utils.PreferencesUtil;

/**
 * Created by baljeetgaheer on 02/09/17.
 */

public class AdminMgr {
    private static AdminMgr sInstance;
    private static AdminDataStore adminDataStore;
    private static PreferencesUtil mPreferencesUtil;

    public static synchronized AdminMgr getInstance(Context context) {
        if (sInstance == null){
            sInstance = new AdminMgr();
            adminDataStore = new AdminDataStore(context);
            mPreferencesUtil = PreferencesUtil.getInstance(context);
        }
        return sInstance;
    }


    public void saveAdminFromResponse(JSONObject response)throws Exception {
        JSONObject userJson = response.getJSONObject("admin");
        int adminSeq = userJson.getInt("seq");
        String userName = userJson.getString("username");
        String name = userJson.getString("name");
        Admin existingAdmin = getAdminBySeq(adminSeq);
        Admin admin = null;
        if(existingAdmin !=  null){
            admin = existingAdmin;
        }else{
            admin = new Admin();
        }
        admin.setAdminSeq(adminSeq);
        admin.setUserName(userName);
        admin.setName(name);
        adminDataStore.save(admin);
        mPreferencesUtil.setLoggedInAdminSeq(adminSeq);
    }

    public Admin getAdminBySeq(int userSeq){
        Admin user = adminDataStore.getAdminByUserSeq(userSeq);
        return user;
    }

    public Admin getLoggedInAdmin(){
        int adminSeq  = mPreferencesUtil.getLoggedInAdminSeq();
        Admin admin = adminDataStore.getAdminByUserSeq(adminSeq);
        return admin;
    }

    public String getLoggedInAdminName(){
        Admin user = this.getLoggedInAdmin();
        return user.getUserName();
    }

    public int getLoggedInAdminSeq(){
        int userSeq  = mPreferencesUtil.getLoggedInAdminSeq();
        return userSeq;
    }


    public void resetPreferences(){
        mPreferencesUtil.resetPreferences();
    }

    public boolean isAdminLoggedIn(){
        return this.getLoggedInAdminSeq() > 0;
    }

    public boolean isAdminExistsWithUsername(String userName){
        return adminDataStore.isAdminExistsWithUsername(userName);
    }


}
