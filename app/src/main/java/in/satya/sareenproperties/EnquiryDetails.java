package in.satya.sareenproperties;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.Date;

import in.satya.sareenproperties.Enums.FacingType;
import in.satya.sareenproperties.Enums.PropertyOfferType;
import in.satya.sareenproperties.Enums.PropertyType;
import in.satya.sareenproperties.Enums.PropertyUnit;
import in.satya.sareenproperties.Enums.PurposeType;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.DateUtil;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class EnquiryDetails extends AppCompatActivity  implements IServiceHandler{
    public static final String DELETE_ENQUIRY = "deleteEnquiry";
    private TextView textView_property_type;
    private TextView textView_address;
    private TextView textView_createdOn;
    private TextView textView_modifiedOn;
    private TextView textView_purpose;
    private TextView textView_landmark;
    private TextView textView_area;
    private TextView textView_facing;
    private TextView textView_contact_name;
    private TextView textView_referred_by;
    private TextView textView_mobile;
    private TextView textView_contact_address;
    private TextView textView_amount;
    private TextView textView_spec;
    private TextView textView_offer;
    private LayoutHelper layoutHelper;
    private ServiceHandler mAuthTask;
    private int mEnquirySeq;
    private String mCallName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        textView_address = (TextView)findViewById(R.id.details_propertyaddress);
        textView_createdOn = (TextView)findViewById(R.id.details_createdOn);
        textView_modifiedOn = (TextView)findViewById(R.id.details_modifiedOn);
        textView_property_type = (TextView)findViewById(R.id.details_type) ;
        textView_purpose = (TextView)findViewById(R.id.details_purpose);
        textView_landmark = (TextView)findViewById(R.id.details_landmark);
        textView_area = (TextView)findViewById(R.id.details_area);
        textView_facing = (TextView)findViewById(R.id.details_facing);
        textView_contact_name = (TextView)findViewById(R.id.details_name);
        textView_referred_by = (TextView)findViewById(R.id.details_referred);
        textView_mobile = (TextView)findViewById(R.id.details_mobile);
        textView_contact_address  = (TextView)findViewById(R.id.details_address);
        textView_amount = (TextView)findViewById(R.id.details_amount);
        textView_spec = (TextView)findViewById(R.id.details_specifications);
        textView_offer = (TextView)findViewById(R.id.details_offer);
        layoutHelper = new LayoutHelper(this);
        Intent intent = getIntent();
        mEnquirySeq = intent.getIntExtra(StringConstants.SEQ,0);
        executeGetInventoryDetailCall();
    }

    private void executeGetInventoryDetailCall(){
        Object args[] = {mEnquirySeq};
        String getInventoryDetailUrl = MessageFormat.format(StringConstants.GET_ENQUIRY_DETAIL,args);
        mAuthTask = new ServiceHandler(getInventoryDetailUrl,this, "getEnqueryDetail",this);
        mAuthTask.execute();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,EnquiryList.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        Intent intent = new Intent(this,EnquiryList.class);
        startActivity(intent);
        finish();
        return true;
    }

    @Override
    public void processServiceResponse(JSONObject response) {
        mAuthTask = null;
        String message = null;
        try{
            if(mCallName.equals(DELETE_ENQUIRY)){
                Intent intent = new Intent(this,EnquiryList.class);
                startActivity(intent);
                finish();
            }else{
                populateEnquiryList(response);
            }

        }catch (Exception e){
            message = "Error :- " + e.getMessage();
        }
        if(message != null && !message.equals("")){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void populateEnquiryList(JSONObject response)throws Exception{
        JSONObject inventoryJson = response.getJSONObject("enquiry");
        String propertyType = inventoryJson.getString("propertytype");
        if (!propertyType.isEmpty() && !propertyType.equals("null")) {
            propertyType = PropertyType.valueOf(propertyType).toString();
        }
        String area = inventoryJson.getString("propertyarea");
        String unit = inventoryJson.getString("propertyunit");
        if (!unit.isEmpty() && !unit.equals("null")) {
            unit = PropertyUnit.valueOf(unit).toString();
        }
        String purposeType = inventoryJson.getString("purpose");
        if (!purposeType.isEmpty() && !purposeType.equals("null")) {
            purposeType = PurposeType.valueOf(purposeType).toString();
        }
        String address1 = inventoryJson.getString("address");
        String contactPerson = inventoryJson.getString("contactperson");
        String contactMobile = inventoryJson.getString("contactmobile");
        String contactAddress = inventoryJson.getString("contactaddress");
        String createdOn = inventoryJson.getString("createdon");
        Date createdDate = DateUtil.stringToDate(createdOn);
        createdOn = DateUtil.dateToFormat(createdDate, DateUtil.format);
        String modifiedOn = inventoryJson.getString("lastmodifiedon");
        Date lastModifiedOn = DateUtil.stringToDate(modifiedOn);
        modifiedOn = DateUtil.dateToFormat(lastModifiedOn, DateUtil.format);
        String landMark = inventoryJson.getString("landmark");
        String facing = inventoryJson.getString("facing");
        if (!facing.isEmpty() && !facing.equals("null")) {
            facing = FacingType.valueOf(facing).toString();
        }
        String referredBy = inventoryJson.getString("referredby");
        String expectedAmount = inventoryJson.getString("expectedamount");
        String offerType = inventoryJson.getString("propertyoffer");
        if (!offerType.isEmpty() && !offerType.equals("null")) {
            offerType = PropertyOfferType.valueOf(offerType).toString();
        }
        String specifications = inventoryJson.getString("specifications");

        textView_address.setText(address1);

        textView_createdOn.setText(createdOn);
        textView_modifiedOn.setText(modifiedOn);
        textView_property_type.setText(propertyType);

        textView_purpose.setText(purposeType);

        textView_landmark.setText(landMark);
        textView_area.setText(area + " " + unit);
        textView_facing.setText(facing);
        textView_contact_name.setText(contactPerson);
        textView_referred_by.setText(referredBy);
        textView_mobile.setText(contactMobile);
        textView_contact_address.setText(contactAddress);
        textView_amount.setText(expectedAmount + "/-");
        textView_offer.setText(offerType);
        textView_spec.setText(specifications);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, CreateEnquiryActivity.class);
                intent.putExtra("enquirySeq",mEnquirySeq);
                startActivity(intent);
                return true;
            case R.id.action_delete:
                deleteEnquiry();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void setCallName(String call) {
        mCallName = call;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inventory_detail_menu, menu);
        return true;
    }

    public void deleteEnquiry() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Inventory");
        builder.setMessage("Do you really want to delete this Enquiry?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                executeDeleteEnquiry();
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

    public void executeDeleteEnquiry(){
        Object args[] = {mEnquirySeq};
        String deleteEnquiryUrl = MessageFormat.format(StringConstants.DELETE_ENQUIRY,args);
        mAuthTask = new ServiceHandler(deleteEnquiryUrl,this, DELETE_ENQUIRY,this);
        mAuthTask.execute();
    }

}
