package in.satya.sareenproperties.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;

/**
 * Created by baljeetgaheer on 07/09/17.
 */
public class LayoutHelper {
    private Activity mActivity;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    public LayoutHelper(Activity activity){
        mActivity = activity;
    }
    public static void showToast(Context context, String message){
        if(message != null && !message.equals("")){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadImageFromDrawable(ImageView imageView, String imageName){
        Glide.with(mActivity).load(getImage(imageName)).into(imageView);
    }

    public int getImage(String imageName) {
        int drawableResourceId = mActivity.getResources().getIdentifier(imageName, "drawable",
                mActivity.getPackageName());
        return drawableResourceId;
    }

    public void loadImageRequest(ImageView bg, String url) {
        if(url.equals(StringConstants.NULL)){
            url = "dummy";
            this.loadImageFromDrawable(bg,url);
        }else{
            url = StringConstants.WEB_URL + url;
            Glide.with(mActivity)
                    .load(url)
                    .asBitmap()
                    .thumbnail(0.01f)
                    .signature(new StringSignature(String.valueOf(System.currentTimeMillis())))
                    .into(bg);
        }

    }

}