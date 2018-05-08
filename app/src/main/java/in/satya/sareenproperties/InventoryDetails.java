package in.satya.sareenproperties;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Date;

import in.satya.sareenproperties.Enums.DocumentType;
import in.satya.sareenproperties.Enums.FacingType;
import in.satya.sareenproperties.Enums.MediumType;
import in.satya.sareenproperties.Enums.PropertyOfferType;
import in.satya.sareenproperties.Enums.PropertyType;
import in.satya.sareenproperties.Enums.PropertyUnit;
import in.satya.sareenproperties.Enums.PurposeType;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.DateUtil;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class InventoryDetails extends AppCompatActivity implements IServiceHandler {
    private static final String GET_INVENTORY_DETAIL = "getInventoryDetail";
    private static final String DELETE_INVENTORY = "deleteInventory";
    private String mCallName;
    private int mInventorySeq;
    private ServiceHandler mAuthTask = null;
    private TextView textView_property_detail;
    private TextView textView_contact_detail;
    private TextView textView_address;
    private TextView textView_createdOn;
    private TextView textView_modifiedOn;
    private TextView textView_medium;
    private TextView textView_property_type;
    private TextView textView_plot_number;
    private TextView textView_purpose;
    private TextView textView_address1;
    private TextView textView_address2;
    private TextView textView_city;
    private TextView textView_landmark;
    private TextView textView_area;
    private TextView textView_dimensions;
    private TextView textView_facing;
    private TextView textView_document;
    private TextView textView_contact_name;
    private TextView textView_referred_by;
    private TextView textView_mobile;
    private TextView textView_organisation;
    private TextView textView_contact_address;
    private TextView textView_rate;
    private TextView textView_amount;
    private TextView textView_time;
    private TextView textView_availability;
    private TextView textView_spec;
    private TextView textView_offer_type;
    private ImageView imageView_property;
    private LayoutHelper layoutHelper;
    private ImageButton imageButton_showLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Intent intent = getIntent();
        mInventorySeq = intent.getIntExtra(StringConstants.SEQ,0);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        textView_property_detail = (TextView) findViewById(R.id.textview_property_type);
        textView_contact_detail= (TextView)findViewById(R.id.textview_contact_detail);
        textView_address = (TextView)findViewById(R.id.textview_address);
        textView_createdOn = (TextView)findViewById(R.id.details_createdOn);
        textView_modifiedOn = (TextView)findViewById(R.id.details_modifiedOn);
        textView_property_type = (TextView)findViewById(R.id.details_type) ;
        textView_medium = (TextView)findViewById(R.id.details_medium);
        textView_plot_number = (TextView)findViewById(R.id.details_plotNumber);
        textView_purpose = (TextView)findViewById(R.id.details_purpose);
        textView_address1 = (TextView)findViewById(R.id.details_address1);
        textView_address2 = (TextView)findViewById(R.id.details_address2);
        textView_city = (TextView)findViewById(R.id.details_city);
        textView_landmark = (TextView)findViewById(R.id.details_landmark);

        textView_area = (TextView)findViewById(R.id.details_area);
        textView_dimensions = (TextView)findViewById(R.id.details_dimensions);
        textView_facing = (TextView)findViewById(R.id.details_facing);
        textView_document = (TextView)findViewById(R.id.details_documents);
        textView_contact_name = (TextView)findViewById(R.id.details_name);
        textView_referred_by = (TextView)findViewById(R.id.details_referred);
        textView_mobile = (TextView)findViewById(R.id.details_mobile);
        textView_organisation = (TextView)findViewById(R.id.details_organisation);
        textView_contact_address  = (TextView)findViewById(R.id.details_address);
        textView_rate = (TextView)findViewById(R.id.details_rate);
        textView_amount = (TextView)findViewById(R.id.details_amount);
        textView_time = (TextView)findViewById(R.id.details_time);
        textView_availability = (TextView)findViewById(R.id.details_availability);
        textView_spec = (TextView)findViewById(R.id.details_specifications);
        textView_offer_type = (TextView)findViewById(R.id.details_offer);
        imageView_property = (ImageView)findViewById(R.id.details_imageView);
        imageButton_showLocation = (ImageButton)findViewById(R.id.imageButton_showLocation);
        layoutHelper = new LayoutHelper(this);
        executeGetInventoryDetailCall();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
        finish();
        return true;
    }

    private void populateInventoryDetail(JSONObject response)throws Exception{
        JSONObject inventoryJson = response.getJSONObject("inventory");
        String propertyType = inventoryJson.getString("propertytype");
        if (!propertyType.isEmpty() && !propertyType.equals("null")) {
            propertyType = PropertyType.valueOf(propertyType).toString();
        }
        String area = inventoryJson.getString("propertyarea");
        String unit = inventoryJson.getString("propertyunit");
        if (!unit.isEmpty() && !unit.equals("null")) {
            unit = PropertyUnit.valueOf(unit).toString();
        }
        String address1 = inventoryJson.getString("address1");
        String contactPerson = inventoryJson.getString("contactperson");
        String contactMobile = inventoryJson.getString("contactmobile");
        String contactAddress = inventoryJson.getString("contactaddress");
        String imagepath = inventoryJson.getString("imagepath");
        String createdOn = inventoryJson.getString("createdon");
        Date createdDate = DateUtil.stringToDate(createdOn);
        createdOn = DateUtil.dateToFormat(createdDate, DateUtil.format);
        String modifiedOn = inventoryJson.getString("lastmodifiedon");
        Date lastModifiedOn = DateUtil.stringToDate(modifiedOn);
        modifiedOn = DateUtil.dateToFormat(lastModifiedOn, DateUtil.format);
        String medium = inventoryJson.getString("medium");
        if (!medium.isEmpty() && !medium.equals("null")) {
            medium = MediumType.valueOf(medium).toString();
        }
        String propertyoffer = inventoryJson.getString("propertyoffer");
        if (!propertyoffer.isEmpty() && !propertyoffer.equals("null")) {
            propertyoffer = PropertyOfferType.valueOf(propertyoffer).toString();
        }
        String plotNumber = inventoryJson.getString("plotnumber");
        String purpose = inventoryJson.getString("purpose");
        if (!purpose.isEmpty() && !purpose.equals("null")) {
            purpose = PurposeType.valueOf(purpose).toString();
        }
        String address2 = inventoryJson.getString("address2");
        String city = inventoryJson.getString("city");
        String landMark = inventoryJson.getString("landmark");

        String dimensionLength = inventoryJson.getString("dimensionlength");
        String dimensionBreadth = inventoryJson.getString("dimensionbreadth");
        String facing = inventoryJson.getString("facing");
        if (!facing.isEmpty() && !facing.equals("null")) {
            facing = FacingType.valueOf(facing).toString();
        }
        String documentation = inventoryJson.getString("documentation");
        if (!documentation.isEmpty() && !documentation.equals("null")) {
            documentation = DocumentType.valueOf(documentation).toString();
        }
        String referredBy = inventoryJson.getString("referredby");

        String organisation = inventoryJson.getString("organisation");
        String rate = inventoryJson.getString("rate");
        String expectedAmount = inventoryJson.getString("expectedamount");
        String time = inventoryJson.getString("time");
        int availability = inventoryJson.getInt("isavailable");
        String specifications = inventoryJson.getString("specifications");
        final double latitude = inventoryJson.getDouble("latitude");
        final double longitude = inventoryJson.getDouble("longitude");

        final String propertyDetail = propertyType + " - " + area + " " + unit;
        textView_property_detail.setText(propertyDetail);
        textView_contact_detail.setText(contactPerson + "-" + contactMobile);
        textView_address.setText(address1);

        textView_createdOn.setText(createdOn);
        textView_modifiedOn.setText(modifiedOn);
        textView_property_type.setText(propertyType);
        textView_medium.setText(medium);
        textView_plot_number.setText(plotNumber);
        textView_purpose.setText(purpose);
        textView_address1.setText(address1);
        textView_address2.setText(address2);
        textView_city.setText(city);
        textView_landmark.setText(landMark);
        textView_area.setText(area + " " + unit);
        textView_dimensions.setText(dimensionLength + " x " + dimensionBreadth);
        textView_facing.setText(facing);
        textView_document.setText(documentation);
        textView_contact_name.setText(contactPerson);
        textView_referred_by.setText(referredBy);
        textView_mobile.setText(contactMobile);
        textView_organisation.setText(organisation);
        textView_contact_address.setText(contactAddress);
        textView_rate.setText(rate + "/-");
        textView_amount.setText(expectedAmount + "/-");
        textView_time.setText(time);
        textView_offer_type.setText(propertyoffer);
        if (availability > 0) {
            textView_availability.setText("Available");
        } else {
            textView_availability.setText("Not Available");
        }
        textView_spec.setText(specifications);
        imageButton_showLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri mapUri = Uri.parse("geo:0,0?q=" + latitude + "," + longitude + " (" + propertyDetail + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
        layoutHelper.loadImageRequest(imageView_property, imagepath);

    }
    private void executeGetInventoryDetailCall(){
        Object args[] = {mInventorySeq};
        String getInventoryDetailUrl = MessageFormat.format(StringConstants.GET_INVENTORY_DETAIL,args);
        mAuthTask = new ServiceHandler(getInventoryDetailUrl,this, GET_INVENTORY_DETAIL,this);
        mAuthTask.execute();
    }

    private void deleteInventory(){
        Object args[] = {mInventorySeq};
        String deleteInventoryUrl = MessageFormat.format(StringConstants.DELETE_INVENTORY,args);
        mAuthTask = new ServiceHandler(deleteInventoryUrl,this, DELETE_INVENTORY,this);
        mAuthTask.execute();
    }

    @Override
    public void processServiceResponse(JSONObject response) {
        mAuthTask = null;
        //showProgress(false);
        boolean success = false;
        String message = null;
        try{
            success = response.getInt("success") == 1 ? true : false;
            message = response.getString("message");
            if(success){
                if(mCallName.equals(GET_INVENTORY_DETAIL)) {
                    populateInventoryDetail(response);
                }else if(mCallName.equals(DELETE_INVENTORY)){
                    Intent intent = new Intent(this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }catch (Exception e){
            message = "Error :- " + e.getMessage();
        }
        if(message != null && !message.equals("")){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inventory_detail_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, CreateInventory.class);
                intent.putExtra("inventorySeq",mInventorySeq);
                startActivity(intent);
                return true;
            case R.id.action_delete:
                deleteNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void deleteNote() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Inventory");
        builder.setMessage("Do you really want to delete this Inventory?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteInventory();
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

    @Override
    public void setCallName(String call) {
        mCallName = call;
    }


}
