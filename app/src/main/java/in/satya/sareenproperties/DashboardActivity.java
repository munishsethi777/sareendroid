package in.satya.sareenproperties;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import in.satya.sareenproperties.Managers.AdminMgr;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class DashboardActivity extends AppCompatActivity
        implements IServiceHandler, NavigationView.OnNavigationItemSelectedListener {
    private AdminMgr mAdminMgr;
    private LinearLayout layout_inventory_List;
    private ServiceHandler mAuthTask = null;
    private LayoutHelper layoutHelper;
    private String getInventoryUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminMgr = AdminMgr.getInstance(this);
        boolean isAdminLoggedIn = mAdminMgr.isAdminLoggedIn();
        if(!isAdminLoggedIn){
            goToLoginActivity();
            return;
        }
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        layoutHelper = new LayoutHelper(this);
        Intent intent = getIntent();
        getInventoryUrl = intent.getStringExtra("filterData");
        if(getInventoryUrl == null){
            getInventoryUrl = StringConstants.GET_INVENTORIES;
        }
        executeGetInventoryListCal();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    private void executeGetInventoryListCal(){
        layout_inventory_List = (LinearLayout)findViewById(R.id.layout_inventory_list) ;
        layout_inventory_List.removeAllViews();
        mAuthTask = new ServiceHandler(getInventoryUrl,this,this);
        mAuthTask.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inventorylistmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
                // User chose the "Settings" item, show the app settings UI...
                Intent intent = new Intent(this, InventoryFilterActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                executeGetInventoryListCal();
                return true;
            case R.id.action_add:
                intent = new Intent(this, CreateInventory.class);
                startActivity(intent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            Intent intent = new Intent(this,CreateInventory.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this,DashboardActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this,CreateEnquiryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this,EnquiryList.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            logoutConfirm();
        }else if (id == R.id.nav_change_password) {
            Intent intent = new Intent(this,ChangePassword.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changePassword(){

    }

    public void logoutConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Do you really want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void logout(){
        mAdminMgr.resetPreferences();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void populateInventoryList(JSONObject response)throws Exception{
        JSONArray inventories = response.getJSONArray("Rows");
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i=0;i<inventories.length();i++){
            JSONObject inventoryJson = inventories.getJSONObject(i);
            final int seq = inventoryJson.getInt("seq");
            String propertyType = inventoryJson.getString("propertytype");
            String area = inventoryJson.getString("propertyarea");
            String unit = inventoryJson.getString("propertyunit");
            String address1 = inventoryJson.getString("address1");
            String contactPerson = inventoryJson.getString("contactperson");
            String contactMobile = inventoryJson.getString("contactmobile");
            String imagepath = inventoryJson.getString("imagepath");

            LinearLayout fragmentLayout= (LinearLayout) inflater.inflate(R.layout.inventory_list_fragment, null, false);
            ImageView imageView = fragmentLayout.findViewById(R.id.property_image);
            ImageButton imageButton = fragmentLayout.findViewById(R.id.imageview_show_detail);
            TextView textView_property_type = fragmentLayout.findViewById(R.id.textview_property_type);
            textView_property_type.setText(propertyType + " - " + area + " " + unit);
            TextView textView_address = fragmentLayout.findViewById(R.id.textview_address);
            textView_address.setText(address1);
            TextView textView_contact_detail = fragmentLayout.findViewById(R.id.textview_contact_detail);
            textView_contact_detail.setText(contactPerson + "-" + contactMobile);
            layoutHelper.loadImageRequest(imageView,imagepath);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDetail(seq);
                }
            });
            layout_inventory_List.addView(fragmentLayout);
        }
        getInventoryUrl = StringConstants.GET_INVENTORIES;
    }

    private void showDetail(int seq){
        Intent detailIntent = new Intent(this,InventoryDetails.class);
        detailIntent.putExtra(StringConstants.SEQ,seq);
        startActivity(detailIntent);
    }

    @Override
    public void processServiceResponse(JSONObject response) {
        mAuthTask = null;
        String message = null;
        try{
            populateInventoryList(response);
        }catch (Exception e){
            message = "Error :- " + e.getMessage();
        }
        if(message != null && !message.equals("")){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setCallName(String call) {

    }

}
