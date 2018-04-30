package in.satya.sareenproperties.DataStores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.MessageFormat;


import in.satya.sareenproperties.BusinessObjects.Admin;

/**
 * Created by baljeetgaheer on 04/09/17.
 */

public class AdminDataStore {

    private Context mContext;
    private DBUtil mDBUtil;




    public static final String COLUMN_SEQ = "id";
    public static final String COLUMN_ADMIN_SEQ = "adminseq";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_NAME = "name";

    public static final String TABLE_NAME = "admins";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME
            + "(" + COLUMN_SEQ + " INTEGER PRIMARY KEY, "
            + COLUMN_ADMIN_SEQ + " INTEGER, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_NAME + " TEXT )";
    public static final String FIND_ADMIN_BY_SEQ = "Select * from admins where " + COLUMN_ADMIN_SEQ + "={0}";
    public static final String COUNT_USER_BY_USER_NAME = "Select count(*) from admins where " + COLUMN_NAME + "={0}";

    public AdminDataStore(Context context){
        mContext = context;
        mDBUtil = DBUtil.getInstance(mContext);
    }
    public long save(Admin admin){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADMIN_SEQ,admin.getAdminSeq());
        values.put(COLUMN_USER_NAME,admin.getUserName());
        values.put(COLUMN_NAME,admin.getName());
        int seq = admin.getId();
        return mDBUtil.addOrUpdateUser(this.TABLE_NAME,values, String.valueOf(seq));
    }



    public Admin getAdminByUserSeq(int userSeq){
        Object[] args  = {userSeq};
        String query = MessageFormat.format(FIND_ADMIN_BY_SEQ,args);
        Cursor c = mDBUtil.executeQuery(query);
        if(c.moveToFirst()) {
            Admin admin = populateObject(c);
            return admin;
        }
        return  null;
    }
    public boolean isAdminExistsWithUsername(String userName){
        Object[] args  = {"'"+userName+"'"};
        String query = MessageFormat.format(COUNT_USER_BY_USER_NAME,args);
        int count = mDBUtil.getCount(query);
        return  count > 0;
    }

    private Admin populateObject( Cursor c){
        int id = c.getInt(0);
        int adminSeq = c.getInt(1);
        String userName = c.getString(2);
        String name = c.getString(3);
        Admin admin = new Admin();
        admin.setId(id);
        admin.setAdminSeq(adminSeq);
        admin.setUserName(userName);
        admin.setName(name);
        return admin;
    }

}
