package in.satya.sareenproperties;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
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
import in.satya.sareenproperties.utils.DateUtil;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class CreateInventory extends AppCompatActivity implements IServiceHandler, OnMapReadyCallback, LocationListener,View.OnClickListener {
    public static final String GET_INVENTORY_DETAIL = "getInventoryDetail";
    public static final String SAVE_INVENTORY = "SAVE_INVENTORY";
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
    public static final int GET_FROM_GALLERY = 3;
    private Bitmap propertyImageBitMap;
    private ImageView imageView_property_image;
    private int mInventorySeq;
    private String mCallName;
    private ArrayAdapter propertyAdapter;
    private ArrayAdapter propertUnitAdapter;
    private ArrayAdapter purposeTypeAdapter;
    private ArrayAdapter mediumAdapter;
    private ArrayAdapter facingTypeAdapter;
    private ArrayAdapter documentTypeAdapter;
    private ArrayAdapter approvalTypeAdapter;
    private ArrayAdapter propertySideTypeAdapter;
    private ArrayAdapter rateFactorTypeAdapter;
    private ArrayAdapter propertyOfferAdapter;
    private LayoutHelper layoutHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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

        imageView_property_image = (ImageView)findViewById(R.id.property_image);
        Intent intent = getIntent();
        mInventorySeq = intent.getIntExtra("inventorySeq",0);
        layoutHelper = new LayoutHelper(this);
        buildSpinners();
        executeGetInventoryDetailCall();
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



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

        propertyAdapter =  new ArrayAdapter<PropertyType>(this,
                R.layout.spinner_row,
                PropertyType.values());
        spinner_property_type.setAdapter(propertyAdapter);

        purposeTypeAdapter = new ArrayAdapter<PurposeType>(this,
                R.layout.spinner_row,
                PurposeType.values());
        spinner_purpose.setAdapter(purposeTypeAdapter);

        mediumAdapter = new ArrayAdapter<MediumType>(this,
                R.layout.spinner_row,
                MediumType.values());
        spinner_medium.setAdapter(mediumAdapter);
        spinner_medium.setOnItemSelectedListener(new mediumSpinnerChangeListener(this));

        propertyOfferAdapter = new ArrayAdapter<PropertyOfferType>(this,
                R.layout.spinner_row,
                PropertyOfferType.values());
        spinner_offer.setAdapter(propertyOfferAdapter);

        propertUnitAdapter =  new ArrayAdapter<PropertyUnit>(this,
                R.layout.spinner_row,
                PropertyUnit.values());
        spinner_property_Unit.setAdapter(propertUnitAdapter);

        facingTypeAdapter = new ArrayAdapter<FacingType>(this,
                R.layout.spinner_row,
                        FacingType.values());
        spinner_facing.setAdapter(facingTypeAdapter);

        documentTypeAdapter = new ArrayAdapter<DocumentType>(this,
                R.layout.spinner_row,
                DocumentType.values());
        spinner_document_type.setAdapter(documentTypeAdapter);

        approvalTypeAdapter = new ArrayAdapter<ApprovalType>(this,
                R.layout.spinner_row,
                ApprovalType.values());
        spinner_approval_type.setAdapter(approvalTypeAdapter);

        propertySideTypeAdapter = new ArrayAdapter<PropertySideType>(this,
                R.layout.spinner_row,
                PropertySideType.values());
        spinner_property_side.setAdapter(propertySideTypeAdapter);

        rateFactorTypeAdapter =  new ArrayAdapter<RateFactorType>(this,
                R.layout.spinner_row,
                RateFactorType.values());
        spinner_rate_factor.setAdapter(rateFactorTypeAdapter);

        List numbers = new ArrayList<Integer>();
        for (int i = 0; i <= 99; i++) {
            numbers.add(Integer.toString(i));
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(
                this,  android.R.layout.simple_spinner_dropdown_item, numbers);
        spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spinner_crores.setAdapter(spinnerArrayAdapter);
        spinner_crores.setOnItemSelectedListener(new spinnerChangeListener(this));

        spinner_thousands.setAdapter(spinnerArrayAdapter);
        spinner_thousands.setOnItemSelectedListener(new spinnerChangeListener(this));

        spinner_lakhs.setAdapter(spinnerArrayAdapter);
        spinner_lakhs.setOnItemSelectedListener(new spinnerChangeListener(this));
    }

    private void executeGetInventoryDetailCall(){
        if(mInventorySeq > 0) {
            Object args[] = {mInventorySeq};
            String getInventoryDetailUrl = MessageFormat.format(StringConstants.GET_INVENTORY_DETAIL, args);
            mAuthTask = new ServiceHandler(getInventoryDetailUrl, this, GET_INVENTORY_DETAIL,this);
            mAuthTask.execute();
        }
    }

    private void saveInventory()throws Exception{
        contact_name.setError(null);
        contact_mobile.setError(null);
        address1.setError(null);
        // Store values at the time of the login attempt.
        String username = contact_name.getText().toString();
        String mobile = contact_mobile.getText().toString();
        String address = address1.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(username)) {
            contact_name.setError(getString(R.string.error_field_required));
            focusView = contact_name;
            cancel = true;
        }
        if (TextUtils.isEmpty(mobile)) {
            contact_mobile.setError(getString(R.string.error_field_required));
            focusView = contact_mobile;
            cancel = true;
        }
        if (TextUtils.isEmpty(address)) {
            address1.setError(getString(R.string.error_field_required));
            focusView = address1;
            cancel = true;
        }
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
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
                    URLEncoder.encode(specification.getText().toString(), "UTF-8"),
                    URLEncoder.encode("1111", "UTF-8"),
                    URLEncoder.encode("2222", "UTF-8"),
                    URLEncoder.encode(purposeType, "UTF-8"),
                    1,
                    URLEncoder.encode(editText_medium_name.getText().toString(), "UTF-8"),
                    URLEncoder.encode(editText_medium_address.getText().toString(), "UTF-8"),
                    URLEncoder.encode(editText_medium_phone.getText().toString(), "UTF-8"),
                    mInventorySeq};
            String dashboardCountUrl = MessageFormat.format(StringConstants.SAVE_INVENTORY,args);
            mAuthTask = new ServiceHandler(dashboardCountUrl,this, SAVE_INVENTORY,this);
            mAuthTask.setFileUploadRequest(true);
            mAuthTask.setBitmap(propertyImageBitMap);
            mAuthTask.execute();
        }
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
        }else if(id == R.id.upload_imageButton){
            clickpic();
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
                if(mCallName.equals(GET_INVENTORY_DETAIL)){
                    populateInventoryDetail(response);
                }else if(mCallName.equals(SAVE_INVENTORY)){
                    if(mInventorySeq > 0){
                        Intent detailIntent = new Intent(this,InventoryDetails.class);
                        detailIntent.putExtra(StringConstants.SEQ,mInventorySeq);
                        startActivity(detailIntent);
                    }else{
                        finish();
                        Intent listIntent = new Intent(this,DashboardActivity.class);
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

    public void populateInventoryDetail(JSONObject response)throws Exception{
            JSONObject inventoryJson = response.getJSONObject("inventory");
            String propertyType = inventoryJson.getString("propertytype");
            String area = inventoryJson.getString("propertyarea");
            String unit = inventoryJson.getString("propertyunit");
            String address1 = inventoryJson.getString("address1");
            String contactPerson = inventoryJson.getString("contactperson");
            String contactMobile = inventoryJson.getString("contactmobile");
            String contactAddress = inventoryJson.getString("contactaddress");
            String imagepath = inventoryJson.getString("imagepath");
            String medium = inventoryJson.getString("medium");
            String offer = inventoryJson.getString("propertyoffer");
            String plotNumber = inventoryJson.getString("plotnumber");
            String purpose = inventoryJson.getString("purpose");

            String address2 = inventoryJson.getString("address2");
            String city = inventoryJson.getString("city");
            String landMark = inventoryJson.getString("landmark");

            String dimensionLength = inventoryJson.getString("dimensionlength");
            String dimensionBreadth = inventoryJson.getString("dimensionbreadth");
            String facing = inventoryJson.getString("facing");
            String documentation = inventoryJson.getString("documentation");
            String referredBy = inventoryJson.getString("referredby");

            String organisation = inventoryJson.getString("organisation");
            String rate = inventoryJson.getString("rate");
            String expectedAmount = inventoryJson.getString("expectedamount");
            String time = inventoryJson.getString("time");
            String specifications = inventoryJson.getString("specifications");
            String mediumName = inventoryJson.getString("mediumname");
            String mediumAddress = inventoryJson.getString("mediumaddress");
            String mediumPhone = inventoryJson.getString("mediumphone");


            if(!propertyType.isEmpty()) {
                spinner_property_type.setSelection(propertyAdapter.getPosition(PropertyType.valueOf(propertyType)));
            }
            propertyArea.setText(area);
            if(!unit.isEmpty()) {
                spinner_property_Unit.setSelection(propertUnitAdapter.getPosition(PropertyUnit.valueOf(unit)));
            }
            this.address1.setText(address1);
            contact_name.setText(contactPerson);
            contact_mobile.setText(contactMobile);
            contact_address.setText(contactAddress);
            layoutHelper.loadImageRequest(imageView_property_image,imagepath);
            if(!medium.isEmpty() && !medium.equals("null")) {
                spinner_medium.setSelection(mediumAdapter.getPosition(MediumType.valueOf(medium)));
            }
            if(!offer.isEmpty() && !offer.equals("null")) {
                spinner_offer.setSelection(propertyOfferAdapter.getPosition(PropertyOfferType.valueOf(offer)));
            }
            plotNo.setText(plotNumber);
            if(!purpose.isEmpty() && !purpose.equals("null")) {
                spinner_purpose.setSelection(purposeTypeAdapter.getPosition(PurposeType.valueOf(purpose)));
            }
            this.address2.setText(address2);
            this.city.setText(city);
            this.landmark.setText(landMark);
            this.length.setText(dimensionLength);
            this.breadth.setText(dimensionBreadth);
            if(!facing.isEmpty() && !facing.equals("null")) {
                this.spinner_facing.setSelection(facingTypeAdapter.getPosition(FacingType.valueOf(facing)));
            }
            if(!documentation.isEmpty() && !documentation.equals("null")) {
                this.spinner_document_type.setSelection(documentTypeAdapter.getPosition(DocumentType.valueOf(documentation)));
            }
            this.refferedBy.setText(referredBy);
            this.organisation.setText(organisation);
            this.rate.setText(rate);
            this.textView_expected_amount.setText(expectedAmount);
            this.time.setText(time);
            this.specification.setText(specifications);
            this.editText_medium_name.setText(mediumName);
            this.editText_medium_phone.setText(mediumPhone);
            this.editText_medium_address.setText(mediumAddress);
    }

    @Override
    public void setCallName(String call) {
        mCallName = call;
    }

    private void clickpic() {
        // Check Camera
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),GET_FROM_GALLERY);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                propertyImageBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imageView_property_image.setImageBitmap(propertyImageBitMap);
            }catch (IOException e){
                String message = e.getMessage();
                Toast.makeText(this,message,Toast.LENGTH_LONG);
            }
        }
    }


}
class spinnerChangeListener implements AdapterView.OnItemSelectedListener{
    CreateInventory activity;
    public spinnerChangeListener(CreateInventory activity){
        this.activity = activity;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
           int cr = Integer.parseInt(activity.spinner_crores.getSelectedItem().toString()) * 10000000;
           int lk = Integer.parseInt(activity.spinner_lakhs.getSelectedItem().toString()) * 100000;
           int th = Integer.parseInt(activity.spinner_thousands.getSelectedItem().toString()) * 1000;
           Integer total = cr + lk + th;
           if(cr > 0 || lk > 0 || th > 0 ) {
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
        if(!medium.equals(MediumType.direct.toString()) &&
                !medium.equals(MediumType.selectAny.toString())){
            this.activity.mediumDetailLayout.setVisibility(View.VISIBLE);
        }else{
            this.activity.mediumDetailLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}