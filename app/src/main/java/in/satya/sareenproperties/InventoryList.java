package in.satya.sareenproperties;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;


import org.json.JSONArray;
import org.json.JSONObject;

import java.text.MessageFormat;

import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class InventoryList extends AppCompatActivity implements IServiceHandler {
    private LinearLayout layout_inventory_List;
    private ServiceHandler mAuthTask = null;
    private LayoutHelper layoutHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        layout_inventory_List = (LinearLayout)findViewById(R.id.layout_inventory_list) ;
        layoutHelper = new LayoutHelper(this);
        executeGetInventoryListCal();
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
    }

    private void showDetail(int seq){
        Intent detailIntent = new Intent(this,InventoryDetails.class);
        detailIntent.putExtra(StringConstants.SEQ,seq);
        startActivity(detailIntent);
    }
    private void executeGetInventoryListCal(){
        String getInventoryListUrl =StringConstants.GET_INVENTORIES;
        mAuthTask = new ServiceHandler(getInventoryListUrl,this,this);
        mAuthTask.execute();
    }


}
