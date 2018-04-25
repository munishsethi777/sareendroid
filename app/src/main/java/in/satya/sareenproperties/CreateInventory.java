package in.satya.sareenproperties;

import android.app.Activity;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import in.satya.sareenproperties.Enums.ApprovalType;
import in.satya.sareenproperties.Enums.DocumentType;
import in.satya.sareenproperties.Enums.FacingType;
import in.satya.sareenproperties.Enums.MediumType;
import in.satya.sareenproperties.Enums.PropertyOfferType;
import in.satya.sareenproperties.Enums.PropertySideType;
import in.satya.sareenproperties.Enums.PropertyType;
import in.satya.sareenproperties.Enums.PropertyUnit;
import in.satya.sareenproperties.Enums.PurposeType;
import in.satya.sareenproperties.Enums.RateFactorType;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class CreateInventory extends AppCompatActivity implements IServiceHandler, OnMapReadyCallback, LocationListener,View.OnClickListener {
    private GoogleMap mMap;
    private FloatingActionButton fab;
    //USER INFO
    private EditText contact_name;
    private EditText contact_mobile;
    private EditText refferedBy;
    private EditText organisation;
    private EditText contact_address;

    //Location
    private EditText plotNo;
    private EditText address1;
    private EditText address2;
    private EditText city;
    private EditText landmark;


    //Property Area
    private EditText propertyArea;
    private Spinner spinner_property_Unit;
    private EditText length;
    private EditText breadth;

    //Property Features
    private Spinner spinner_facing;
    private Spinner spinner_document_type;
    private Spinner spinner_approval_type;
    private Spinner spinner_property_side;
    private EditText time;

    //Pricing
    private EditText totalAmount;
    private EditText rate;
    private EditText specification;

    private Spinner spinner_property_type;
    private Spinner spinner_purpose;
    public Spinner spinner_medium;
    private Spinner spinner_offer;
    private ServiceHandler mAuthTask = null;

    public Spinner spinner_crores;
    public Spinner spinner_lakhs;
    public Spinner spinner_thousands;
    public Spinner spinner_rate_factor;
    public EditText textView_expected_amount;

    //medium
    public LinearLayout mediumDetailLayout;
    private EditText editText_medium_name;
    private EditText editText_medium_phone;
    private EditText editText_medium_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveInventory();
                }catch (Exception e){
                    LayoutHelper.showToast(getApplicationContext(),e.getMessage());
                }
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        contact_name = (EditText)findViewById(R.id.contact_person);
        contact_mobile = (EditText)findViewById(R.id.contact_mobile);
        refferedBy = (EditText)findViewById(R.id.referred);
        textView_expected_amount = (EditText) findViewById(R.id.expectedAmount);
        organisation = (EditText)findViewById(R.id.organisation);
        contact_address = (EditText)findViewById(R.id.contact_address);

        //Location
        plotNo = (EditText)findViewById(R.id.plotnumber);
        address1 = (EditText)findViewById(R.id.address1);
        address2 = (EditText)findViewById(R.id.address2);
        city = (EditText)findViewById(R.id.city);
        landmark = (EditText)findViewById(R.id.landmark);

        //property Feature
        time = (EditText)findViewById(R.id.time);
        specification = (EditText)findViewById(R.id.specifications);

        //Pricing
        totalAmount = (EditText)findViewById(R.id.expectedAmount);
        rate = (EditText)findViewById(R.id.rate);

        //Property Area
        propertyArea = (EditText)findViewById(R.id.propertyarea);
        length = (EditText)findViewById(R.id.dimensionlength);
        breadth = (EditText)findViewById(R.id.dimensionbreadth);

        //medium Detail
        mediumDetailLayout = (LinearLayout)findViewById(R.id.medium_layout);
        editText_medium_name = (EditText)findViewById(R.id.mediumname);
        editText_medium_address = (EditText)findViewById(R.id.mediumaddress);
        editText_medium_phone = (EditText)findViewById(R.id.mediumphone);
        buildSpinners();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
         mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                MarkerOptions marker = new MarkerOptions().position(latLng).title("New Marker");
                mMap.addMarker(marker);
            }
        });
         try{
             LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
             Criteria criteria = new Criteria();
             String bestProvider = locationManager.getBestProvider(criteria, true);
             Location location = locationManager.getLastKnownLocation(bestProvider);
             if (location != null) {
                 onLocationChanged(location);
             }
             locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
         }catch (SecurityException se){

         }
    }


    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }



    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    private void buildSpinners(){
        spinner_property_type = (Spinner) findViewById(R.id.property_type);
        spinner_purpose = (Spinner) findViewById(R.id.purpose);
        spinner_medium = (Spinner) findViewById(R.id.medium);
        spinner_offer = (Spinner) findViewById(R.id.propertyoffer);
        spinner_property_Unit = (Spinner) findViewById(R.id.propertyunit);
        spinner_facing = (Spinner) findViewById(R.id.facing);
        spinner_document_type = (Spinner) findViewById(R.id.documentation);
        spinner_approval_type = (Spinner) findViewById(R.id.approvaltype);
        spinner_property_side = (Spinner) findViewById(R.id.propertysides);
        spinner_crores = (Spinner) findViewById(R.id.spinner_crores);
        spinner_lakhs = (Spinner) findViewById(R.id.spinner_lakhs);
        spinner_thousands = (Spinner) findViewById(R.id.spinner_thousands);
        spinner_rate_factor = (Spinner)findViewById(R.id.ratefactor);

        spinner_property_type.setAdapter(
                new ArrayAdapter<PropertyType>(this,
                        android.R.layout.simple_spinner_item,
                        PropertyType.values()));


        spinner_purpose.setAdapter(
                new ArrayAdapter<PurposeType>(this,
                        android.R.layout.simple_spinner_item,
                        PurposeType.values()));

        spinner_medium.setAdapter(
                new ArrayAdapter<MediumType>(this,
                        android.R.layout.simple_spinner_item,
                        MediumType.values()));
        spinner_medium.setOnItemSelectedListener(new mediumSpinnerChangeListener(this));
        spinner_offer.setAdapter(
                new ArrayAdapter<PropertyOfferType>(this,
                        android.R.layout.simple_spinner_item,
                        PropertyOfferType.values()));

        spinner_property_Unit.setAdapter(
                new ArrayAdapter<PropertyUnit>(this,
                        android.R.layout.simple_spinner_item,
                        PropertyUnit.values()));

        spinner_facing.setAdapter(
                new ArrayAdapter<FacingType>(this,
                        android.R.layout.simple_spinner_item,
                        FacingType.values()));

        spinner_document_type.setAdapter(
                new ArrayAdapter<DocumentType>(this,
                        android.R.layout.simple_spinner_item,
                        DocumentType.values()));

        spinner_approval_type.setAdapter(
                new ArrayAdapter<ApprovalType>(this,
                        android.R.layout.simple_spinner_item,
                        ApprovalType.values()));

        spinner_property_side.setAdapter(
                new ArrayAdapter<PropertySideType>(this,
                        android.R.layout.simple_spinner_item,
                        PropertySideType.values()));
        spinner_rate_factor.setAdapter(
                new ArrayAdapter<RateFactorType>(this,
                        android.R.layout.simple_spinner_item,
                        RateFactorType.values()));

        List numbers = new ArrayList<Integer>();
        for (int i = 0; i <= 99; i++) {
            numbers.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this, android.R.layout.simple_spinner_item, numbers);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner_crores.setAdapter(spinnerArrayAdapter);
        spinner_crores.setOnItemSelectedListener(new spinnerChangeListener(this));

        spinner_thousands.setAdapter(spinnerArrayAdapter);
        spinner_thousands.setOnItemSelectedListener(new spinnerChangeListener(this));

        spinner_lakhs.setAdapter(spinnerArrayAdapter);
        spinner_lakhs.setOnItemSelectedListener(new spinnerChangeListener(this));
    }


    private void saveInventory()throws Exception{
        String propertyType = PropertyType.getNameByValue(spinner_property_type.getSelectedItem().toString());
        String medium = MediumType.getNameByValue(spinner_medium.getSelectedItem().toString());
        String offer = PropertyOfferType.getNameByValue(spinner_offer.getSelectedItem().toString());
        String propertyUnit = PropertyUnit.getNameByValue(spinner_property_Unit.getSelectedItem().toString());
        String facing  = FacingType.getNameByValue(spinner_facing.getSelectedItem().toString());
        String documentType  = DocumentType.getNameByValue(spinner_document_type.getSelectedItem().toString());
        String propertySide = PropertySideType.getNameByValue(spinner_property_side.getSelectedItem().toString());
        String rateFactor = RateFactorType.getNameByValue(spinner_rate_factor.getSelectedItem().toString());
        String purposeType = PurposeType.getNameByValue(spinner_purpose.getSelectedItem().toString());
        Object[] args = {URLEncoder.encode(contact_name.getText().toString(), "UTF-8"),
                URLEncoder.encode(contact_mobile.getText().toString(), "UTF-8"),
                URLEncoder.encode(refferedBy.getText().toString(), "UTF-8"),
                URLEncoder.encode(contact_address.getText().toString(), "UTF-8"),
                URLEncoder.encode(organisation.getText().toString(), "UTF-8"),
                URLEncoder.encode(propertyType, "UTF-8"),
                URLEncoder.encode(medium, "UTF-8"),
                URLEncoder.encode(offer, "UTF-8"),
                URLEncoder.encode(plotNo.getText().toString(), "UTF-8"),
                URLEncoder.encode(address1.getText().toString(), "UTF-8"),
                URLEncoder.encode(address2.getText().toString(), "UTF-8"),
                URLEncoder.encode(city.getText().toString(), "UTF-8"),
                URLEncoder.encode(landmark.getText().toString(), "UTF-8"),
                URLEncoder.encode(propertyArea.getText().toString(), "UTF-8"),
                URLEncoder.encode(propertyUnit, "UTF-8"),
                URLEncoder.encode(length.getText().toString(), "UTF-8"),
                URLEncoder.encode(breadth.getText().toString(), "UTF-8"),
                URLEncoder.encode(facing, "UTF-8"),
                URLEncoder.encode(documentType, "UTF-8"),
                URLEncoder.encode(time.getText().toString(), "UTF-8"),
                URLEncoder.encode(time.getText().toString(), "UTF-8"),
                URLEncoder.encode(propertySide, "UTF-8"),
                URLEncoder.encode(textView_expected_amount.getText().toString(), "UTF-8"),
                URLEncoder.encode(rate.getText().toString(), "UTF-8"),
                URLEncoder.encode(rateFactor, "UTF-8"),
                URLEncoder.encode("1111", "UTF-8"),
                URLEncoder.encode("2222", "UTF-8"),
                URLEncoder.encode(specification.getText().toString(), "UTF-8"),
                URLEncoder.encode(purposeType, "UTF-8"),
                1,
                URLEncoder.encode(editText_medium_name.getText().toString(), "UTF-8"),
                URLEncoder.encode(editText_medium_address.getText().toString(), "UTF-8"),
                URLEncoder.encode(editText_medium_phone.getText().toString(), "UTF-8")};
        String dashboardCountUrl = MessageFormat.format(StringConstants.SAVE_INVENTORY,args);
        mAuthTask = new ServiceHandler(dashboardCountUrl,this,this);
        mAuthTask.execute();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.fab){
            try {
                saveInventory();
            }catch (Exception e){
                LayoutHelper.showToast(this,e.getMessage());
            }
        }
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

    }



}
class spinnerChangeListener implements AdapterView.OnItemSelectedListener{
    CreateInventory activity;
    public spinnerChangeListener(CreateInventory activity){
        this.activity = activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       String medium = activity.spinner_medium.getSelectedItem().toString();
       if(!medium.equals(MediumType.direct)){
           int cr = Integer.parseInt(activity.spinner_crores.getSelectedItem().toString()) * 10000000;
           int lk = Integer.parseInt(activity.spinner_lakhs.getSelectedItem().toString()) * 100000;
           int th = Integer.parseInt(activity.spinner_thousands.getSelectedItem().toString()) * 1000;
           Integer total = cr + lk + th;
           activity.textView_expected_amount.setText(total.toString());
       }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

class mediumSpinnerChangeListener implements AdapterView.OnItemSelectedListener{
    CreateInventory activity;
    public mediumSpinnerChangeListener(CreateInventory activity){
        this.activity = activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String medium = this.activity.spinner_medium.getSelectedItem().toString();
        if(!medium.equals(MediumType.direct.toString())){
            this.activity.mediumDetailLayout.setVisibility(View.VISIBLE);
        }else{
            this.activity.mediumDetailLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}