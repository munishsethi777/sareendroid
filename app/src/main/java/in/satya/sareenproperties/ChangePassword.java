package in.satya.sareenproperties;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.MessageFormat;

import in.satya.sareenproperties.Managers.AdminMgr;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class ChangePassword extends AppCompatActivity implements View.OnClickListener,IServiceHandler {
    private TextView mCurrentPasswordView;
    private TextView mNewPasswordView;
    private TextView mConfirmPasswordView;
    private ServiceHandler mAuthTask;
    private AdminMgr adminMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        adminMgr = new AdminMgr();
        mCurrentPasswordView = (TextView)findViewById(R.id.editText_currentPassword);
        mNewPasswordView = (TextView)findViewById(R.id.editText_newPassword);
        mConfirmPasswordView = (TextView)findViewById(R.id.editText_confirmPassword);
    }



    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button_change_password){
            attemptedChangePassword();
        }
    }

    private void attemptedChangePassword(){
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mCurrentPasswordView.setError(null);
        mNewPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String currentPassword = mCurrentPasswordView.getText().toString();
        String newPassword = mNewPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(currentPassword)) {
            mCurrentPasswordView.setError(getString(R.string.error_field_required));
            focusView = mCurrentPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(newPassword)) {
            mNewPasswordView.setError(getString(R.string.error_field_required));
            focusView = mNewPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_field_required));
            focusView = mNewPasswordView;
            cancel = true;
        }

        if (!newPassword.equals(confirmPassword)) {
            mConfirmPasswordView.setError(getString(R.string.error_confirm_password_match));
            focusView = mConfirmPasswordView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            int adminSeq = adminMgr.getLoggedInAdminSeq();
            Object[] args = {newPassword,currentPassword,adminSeq};
            executeChangePasswordCall(args);
        }
    }

    private void executeChangePasswordCall(Object[] args){
        String loginUrl = MessageFormat.format(StringConstants.CHANGE_PASSWORD,args);
        mAuthTask = new ServiceHandler(loginUrl,this,this);
        mAuthTask.execute();
    }


    @Override
    public void processServiceResponse(JSONObject response){
        mAuthTask = null;
        boolean success;
        String message;
        try{
            success = response.getInt("success") == 1 ? true : false;
            message = response.getString("message");
            if(success){
                resetViews();
            }
        }catch (Exception e){
            message = "Error :- " + e.getMessage();
        }
        LayoutHelper.showToast(this,message);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void setCallName(String call) {}

    private void resetViews(){
        mCurrentPasswordView.setText(null);
        mNewPasswordView.setText(null);
        mConfirmPasswordView.setText(null);
    }
}
