package in.satya.sareenproperties;


import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import org.json.JSONObject;
import java.text.MessageFormat;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,IServiceHandler {
    View bg;
    //Views
    private EditText mUsernameView;
    private EditText mPasswordView;
    private ServiceHandler mAuthTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Sign In");
        }
        mPasswordView = (EditText) findViewById(R.id.password_view);
        mUsernameView = (EditText) findViewById(R.id.username_view);
 }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignIn:
                attemptLogin();
                break;
            default:
                break;
        }
    }

    @Override
    public void processServiceResponse(JSONObject response) {
        mAuthTask = null;
        boolean success = false;
        String message = "Logged in successfully";
        try{
            success = response.getInt("success") == 1 ? true : false;
            message = response.getString("message");
            if(success){
//                mUserMgr.saveUserFromResponse(response);
                goToDashboardActivity();
            }
        }catch (Exception e){
            message = "Error :- " + e.getMessage();
        }
        if(!message.equals("null")) {
            LayoutHelper.showToast(this, message);
        }
    }

    @Override
    public void setCallName(String call) {}


    private void goToDashboardActivity() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
              mPasswordView.setError(getString(R.string.error_field_required));
              focusView = mPasswordView;
              cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Object[] args = {username,password};
            String loginUrl = MessageFormat.format(StringConstants.LOGIN_URL,args);
            mAuthTask = new ServiceHandler(loginUrl,this,this);
            mAuthTask.execute();
        }
    }

}

