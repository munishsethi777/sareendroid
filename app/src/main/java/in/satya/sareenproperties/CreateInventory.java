package in.satya.sareenproperties;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.sql.Connection;

import in.satya.sareenproperties.Enums.ApprovalType;
import in.satya.sareenproperties.Enums.DocumentType;
import in.satya.sareenproperties.Enums.FacingType;
import in.satya.sareenproperties.Enums.MediumType;
import in.satya.sareenproperties.Enums.PropertyOfferType;
import in.satya.sareenproperties.Enums.PropertySideType;
import in.satya.sareenproperties.Enums.PropertyType;
import in.satya.sareenproperties.Enums.PropertyUnit;
import in.satya.sareenproperties.Enums.PurposeType;
import in.satya.sareenproperties.services.Interface.IServiceHandler;

public class CreateInventory extends AppCompatActivity implements IServiceHandler, OnMapReadyCallback, LocationListener,View.OnClickListener {
    private GoogleMap mMap;
    private FloatingActionButton fab;
    private Spinner spinner_property_type;
    private Spinner spinner_purpose;
    private Spinner spinner_medium;
    private Spinner spinner_offer;
    private Spinner spinner_property_Unit;
    private Spinner spinner_facing;
    private Spinner spinner_document_type;
    private Spinner spinner_approval_type;
    private Spinner spinner_property_side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
//             Location location = locationManager.getLastKnownLocation(bestProvider);
//             if (location != null) {
//                 onLocationChanged(location);
//             }
//             locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
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
    }


    private void saveInventory(){

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.fab){
            saveInventory();
        }
    }

    @Override
    public void processServiceResponse(JSONObject response) {

    }

    @Override
    public void setCallName(String call) {

    }
}
