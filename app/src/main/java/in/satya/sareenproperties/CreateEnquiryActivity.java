package in.satya.sareenproperties;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.satya.sareenproperties.Enums.FacingType;
import in.satya.sareenproperties.Enums.MediumType;
import in.satya.sareenproperties.Enums.PropertyType;
import in.satya.sareenproperties.Enums.PropertyUnit;
import in.satya.sareenproperties.Enums.PurposeType;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.DateUtil;
import in.satya.sareenproperties.utils.StringConstants;

public class CreateEnquiryActivity extends AppCompatActivity implements IServiceHandler {
    public static final String GET_ENQUIRY_DETAIL = "GET_ENQUIRY_DETAIL";
    private Spinner spinner_property_type;
    private Spinner spinner_purpose;
    private EditText editText_enquiry;
    private EditText editText_landmark;
    private EditText editText_area;
    private Spinner spinner_unit;
    private EditText editText_length;
    private EditText editText_breadth;
    private Spinner spinner_facing;
    private EditText editText_referred;
    private EditText editText_name;
    private EditText editText_mobile;
    private EditText editText_address;
    public EditText editText_total_amount;
    private CheckBox checkBox_rental;
    private CheckBox checkBox_fullfill;
    private EditText editText_specifications;
    private ArrayAdapter propertyAdapter;
    private ArrayAdapter propertyUnitAdapter;
    private ArrayAdapter purposeTypeAdapter;
    private ArrayAdapter facingTypeAdapter;

    public Spinner spinner_crores;
    public Spinner spinner_lakhs;
    public Spinner spinner_thousands;
    private ServiceHandler mAuthTask = null;
    private String SAVE_ENQUIRY = "saveEnquiry";
    private String mCallName;
    private int mEnquirySeq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_enquiry);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveEnquiry();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        editText_total_amount = (EditText)findViewById(R.id.expectedAmount);
        editText_enquiry = (EditText)findViewById(R.id.enquiry);
        editText_landmark = (EditText)findViewById(R.id.landmark);
        editText_area = (EditText)findViewById(R.id.area);
        editText_length = (EditText)findViewById(R.id.length);
        editText_breadth = (EditText)findViewById(R.id.breadth);
        editText_referred = (EditText)findViewById(R.id.referred);
        editText_name = (EditText)findViewById(R.id.name);
        editText_mobile = (EditText)findViewById(R.id.mobile);
        editText_address = (EditText)findViewById(R.id.address);
        checkBox_fullfill = (CheckBox)findViewById(R.id.isFulfilled);
        checkBox_rental = (CheckBox)findViewById(R.id.isRental);
        editText_specifications = (EditText)findViewById(R.id.specifications);
        buildSpinners();
        Intent intent = getIntent();
        mEnquirySeq = intent.getIntExtra("enquirySeq",0);
        executeGetEnqueryDetailCall();
    }

    private void executeGetEnqueryDetailCall(){
        if(mEnquirySeq > 0) {
            Object args[] = {mEnquirySeq};
            String getEnquiryDetailUrl = MessageFormat.format(StringConstants.GET_ENQUIRY_DETAIL, args);
            mAuthTask = new ServiceHandler(getEnquiryDetailUrl, this, GET_ENQUIRY_DETAIL,this);
            mAuthTask.execute();
        }
    }
    private void buildSpinners() {
        spinner_property_type = (Spinner) findViewById(R.id.property_type);
        spinner_purpose = (Spinner) findViewById(R.id.purpose);
        spinner_unit = (Spinner)findViewById(R.id.units);
        spinner_facing = (Spinner)findViewById(R.id.facing);
        spinner_crores = (Spinner)findViewById(R.id.crores);
        spinner_thousands = (Spinner)findViewById(R.id.thousands);
        spinner_lakhs = (Spinner)findViewById(R.id.lakhs);

        propertyAdapter =  new ArrayAdapter<PropertyType>(this,
                android.R.layout.simple_spinner_item,
                PropertyType.values());
        spinner_property_type.setAdapter(propertyAdapter);

        purposeTypeAdapter = new ArrayAdapter<PurposeType>(this,
                android.R.layout.simple_spinner_item,
                PurposeType.values());
        spinner_purpose.setAdapter(purposeTypeAdapter);

        propertyUnitAdapter =  new ArrayAdapter<PropertyUnit>(this,
                android.R.layout.simple_spinner_item,
                PropertyUnit.values());
        spinner_unit.setAdapter(propertyUnitAdapter);

        facingTypeAdapter = new ArrayAdapter<FacingType>(this,
                android.R.layout.simple_spinner_item,
                FacingType.values());
        spinner_facing.setAdapter(facingTypeAdapter);

        List numbers = new ArrayList<Integer>();
        for (int i = 0; i <= 99; i++) {
            numbers.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, numbers);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner_crores.setAdapter(spinnerArrayAdapter);
        spinner_crores.setOnItemSelectedListener(new AmountSpinnerChangeListener(this));

        spinner_thousands.setAdapter(spinnerArrayAdapter);
        spinner_thousands.setOnItemSelectedListener(new AmountSpinnerChangeListener(this));

        spinner_lakhs.setAdapter(spinnerArrayAdapter);
        spinner_lakhs.setOnItemSelectedListener(new AmountSpinnerChangeListener(this));
    }

    private void saveEnquiry()throws Exception{
        String propertyType = PropertyType.getNameByValue(
                spinner_property_type.getSelectedItem().toString());
        String purposeType = PurposeType.getNameByValue(spinner_purpose.getSelectedItem().toString());
        String enquiry = editText_enquiry.getText().toString();
        String landmark = editText_landmark.getText().toString();
        String area = editText_area.getText().toString();
        String units = PropertyUnit.getNameByValue(spinner_unit.getSelectedItem().toString());
        String length = editText_length.getText().toString();
        String breadth = editText_breadth.getText().toString();
        String facingType = FacingType.getNameByValue(spinner_facing.getSelectedItem().toString());
        String referred = editText_referred.getText().toString();
        String name = editText_name.getText().toString();
        String mobile =  editText_mobile.getText().toString();
        String expectedAmount = editText_total_amount.getText().toString();
        String address = editText_address.getText().toString();
        String isRental = "0";
        if(checkBox_rental.isChecked()){
            isRental = "1";
        }
        String isFullfill = "0";
        if(checkBox_fullfill.isChecked()){
            isFullfill = "1";
        }
        String spec = editText_specifications.getText().toString();
        Object[] args = {URLEncoder.encode(propertyType, "UTF-8"),
                URLEncoder.encode(purposeType, "UTF-8"),
                URLEncoder.encode(enquiry, "UTF-8"),
                URLEncoder.encode(landmark, "UTF-8"),
                URLEncoder.encode(area, "UTF-8"),
                URLEncoder.encode(units, "UTF-8"),
                URLEncoder.encode(length, "UTF-8"),
                URLEncoder.encode(breadth, "UTF-8"),
                URLEncoder.encode(facingType, "UTF-8"),
                URLEncoder.encode(referred, "UTF-8"),
                URLEncoder.encode(name, "UTF-8"),
                URLEncoder.encode(mobile, "UTF-8"),
                URLEncoder.encode(address, "UTF-8"),
                URLEncoder.encode(isRental, "UTF-8"),
                URLEncoder.encode(isFullfill, "UTF-8"),
                URLEncoder.encode(expectedAmount, "UTF-8"),
                URLEncoder.encode(spec, "UTF-8"),
                1,mEnquirySeq};
        String saveEnquiryUrl = MessageFormat.format(StringConstants.SAVE_ENQUIRY,args);
        mAuthTask = new ServiceHandler(saveEnquiryUrl,this, SAVE_ENQUIRY,this);
        mAuthTask.execute();
    }

    public void populateEnquiryDetail(JSONObject response)throws Exception{
        JSONObject enquiryJson = response.getJSONObject("enquiry");
        String propertyType = enquiryJson.getString("propertytype");
        String area = enquiryJson.getString("propertyarea");
        String unit = enquiryJson.getString("propertyunit");
        String purposeType = enquiryJson.getString("purpose");
        String address = enquiryJson.getString("address");
        String contactPerson = enquiryJson.getString("contactperson");
        String contactMobile = enquiryJson.getString("contactmobile");
        String contactAddress = enquiryJson.getString("contactaddress");
        String landMark = enquiryJson.getString("landmark");
        String facing = enquiryJson.getString("facing");
        String referredBy = enquiryJson.getString("referredby");
        String expectedAmount = enquiryJson.getString("expectedamount");
        int isrental = enquiryJson.getInt("isrental");
        int isFullfill = enquiryJson.getInt("isfullfilled");
        String specifications = enquiryJson.getString("specifications");
        String length = enquiryJson.getString("dimensionlength");
        String breadth = enquiryJson.getString("dimensionbreadth");

        editText_enquiry.setText(address);
        if(!propertyType.isEmpty()) {
            spinner_property_type.setSelection(propertyAdapter.getPosition(PropertyType.valueOf(propertyType)));
        }

        if(!purposeType.isEmpty() && !purposeType.equals("null")) {
            spinner_purpose.setSelection(purposeTypeAdapter.getPosition(PurposeType.valueOf(purposeType)));
        }
        if(!facing.isEmpty() && !facing.equals("null")) {
            this.spinner_facing.setSelection(facingTypeAdapter.getPosition(FacingType.valueOf(facing)));
        }

        editText_landmark.setText(landMark);
        editText_area.setText(area + " " + unit);

        editText_name.setText(contactPerson);
        editText_referred.setText(referredBy);
        editText_mobile.setText(contactMobile);
        editText_address.setText(contactAddress);
        editText_total_amount.setText(expectedAmount);
        checkBox_rental.setChecked(isrental > 0);
        checkBox_fullfill.setChecked(isFullfill > 0);
        editText_specifications.setText(specifications);
        editText_length.setText(length);
        editText_breadth.setText(breadth);
    }
    @Override
    public void processServiceResponse(JSONObject response) {
        mAuthTask = null;
        boolean success = false;
        String message = null;
        try{
            success = response.getInt("success") == 1 ? true : false;
            message = response.getString("message");
            if(success){
                if(mCallName.equals(GET_ENQUIRY_DETAIL)){
                    populateEnquiryDetail(response);
                }else if(mCallName.equals(SAVE_ENQUIRY)){
                    if(mEnquirySeq > 0){
                        Intent detailIntent = new Intent(this,EnquiryDetails.class);
                        detailIntent.putExtra(StringConstants.SEQ,mEnquirySeq);
                        startActivity(detailIntent);
                    }else{
                        finish();
                        Intent listIntent = new Intent(this,EnquiryList.class);
                        startActivity(listIntent);
                    }
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
    public void setCallName(String call) {
        mCallName = call;
    }
}
class AmountSpinnerChangeListener implements AdapterView.OnItemSelectedListener{
    CreateEnquiryActivity activity;
    public AmountSpinnerChangeListener(CreateEnquiryActivity activity){
        this.activity = activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int cr = Integer.parseInt(activity.spinner_crores.getSelectedItem().toString()) * 10000000;
        int lk = Integer.parseInt(activity.spinner_lakhs.getSelectedItem().toString()) * 100000;
        int th = Integer.parseInt(activity.spinner_thousands.getSelectedItem().toString()) * 1000;
        Integer total = cr + lk + th;
        if(cr > 0 || lk > 0 || th > 0 ) {
            activity.editText_total_amount.setText(total.toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
