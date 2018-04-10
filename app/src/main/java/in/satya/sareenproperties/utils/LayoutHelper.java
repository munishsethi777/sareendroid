package in.satya.sareenproperties.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
/**
 * Created by baljeetgaheer on 07/09/17.
 */
public class LayoutHelper {
    private Activity mActivity;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    public LayoutHelper(Activity activity, LayoutInflater inflater, ViewGroup container){
        mActivity = activity;
        mInflater = inflater;
        mContainer = container;

    }
    public static void showToast(Context context, String message){
        if(message != null && !message.equals("")){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }
}