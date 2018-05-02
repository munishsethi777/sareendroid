package in.satya.sareenproperties;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import in.satya.sareenproperties.Enums.FacingType;
import in.satya.sareenproperties.Enums.MediumType;
import in.satya.sareenproperties.Enums.PropertyType;
import in.satya.sareenproperties.Enums.PurposeType;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.utils.LayoutHelper;
import in.satya.sareenproperties.utils.StringConstants;

public class InventoryFilterActivity extends AppCompatActivity implements View.OnClickListener,IServiceHandler {
    private Button button_apply_filter;
    private Spinner spinner_type;
    private Spinner spinner_facing;
    private Spinner spinner_medium;
    private Spinner spinner_purpose;

    private EditText editText_address;
    private EditText editText_amount_from;
    private EditText editText_amount_to;
    private Map<String,Object>filterDataMap;
    private LayoutHelper layoutHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        button_apply_filter = (Button)findViewById(R.id.button_apply_filter);
        editText_address = (EditText)findViewById(R.id.address);
        editText_amount_from = (EditText)findViewById(R.id.amountFrom);
        editText_amount_to = (EditText)findViewById(R.id.amountTo);
        filterDataMap = new HashMap<String,Object>();
        buildSpinners();
        final Activity activity = this;
        layoutHelper = new LayoutHelper(this);
        findViewById(R.id.content_layout).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layoutHelper.hideSoftKeyboard(activity);
                return false;
            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button_apply_filter){
            submitFilter();
        }
    }

    private void buildSpinners() {
        spinner_type = (Spinner) findViewById(R.id.propertytype);
        spinner_purpose = (Spinner) findViewById(R.id.purpose);
        spinner_medium = (Spinner) findViewById(R.id.medium);
        spinner_facing = (Spinner) findViewById(R.id.facing);
        spinner_type.setAdapter(
                new ArrayAdapter<PropertyType>(this,
                        R.layout.spinner_row,
                        PropertyType.values()));


        spinner_purpose.setAdapter(
                new ArrayAdapter<PurposeType>(this,
                        R.layout.spinner_row,
                        PurposeType.values()));

        spinner_medium.setAdapter(
                new ArrayAdapter<MediumType>(this,
                        R.layout.spinner_row,
                        MediumType.values()));

        spinner_facing.setAdapter(
                new ArrayAdapter<FacingType>(this,
                        R.layout.spinner_row,
                        FacingType.values()));
    }


    private void submitFilter(){
        String filterInventoryUrl = getFilterRequestUrl();
        Intent listIntent = new Intent(this,DashboardActivity.class);
        listIntent.putExtra("filterData",filterInventoryUrl);
        startActivity(listIntent);
    }

    private String getFilterRequestUrl(){
        String type = PropertyType.getNameByValue(spinner_type.getSelectedItem().toString());
        String purpose = PurposeType.getNameByValue(spinner_purpose.getSelectedItem().toString());
        String medium = MediumType.getNameByValue(spinner_medium.getSelectedItem().toString());
        String facing = FacingType.getNameByValue(spinner_facing.getSelectedItem().toString());
        String address = editText_address.getText().toString();
        String amountFrom = editText_amount_from.getText().toString();
        String amountTo = editText_amount_to.getText().toString();
        int i = 0;
        String condition = "CONTAINS";
        int filterOperator = 0;
        if(!type.isEmpty()) {
            fillFilterRequestData("propertytype",type,condition,filterOperator,i++);
        }
        if(!purpose.isEmpty()) {
            fillFilterRequestData("purpose",purpose,condition,filterOperator,i++);
        }
        if(!medium.isEmpty()) {
            fillFilterRequestData("medium",medium,condition,filterOperator,i++);
        }
        if(!facing.isEmpty()) {
            fillFilterRequestData("facing",facing,condition,filterOperator,i++);
        }
        if(!address.isEmpty()) {
            fillFilterRequestData("address1",address,condition,filterOperator,i++);
        }
        if(!amountFrom.isEmpty()) {
            condition = "GREATER_THAN_OR_EQUAL";
            filterOperator = 0;
            fillFilterRequestData("expectedamount",amountFrom,condition,filterOperator,i++);
        }
        if(!amountTo.isEmpty()) {
            filterOperator = 0;
            condition = "LESS_THAN_OR_EQUAL";
            fillFilterRequestData("expectedamount",amountTo,condition,filterOperator,i++);
        }
        String filterInventoryUrl = StringConstants.GET_INVENTORIES;
        Iterator<String> itr = filterDataMap.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = filterDataMap.get(key);
            filterInventoryUrl += "&"+key+"="+value;
        }
        filterInventoryUrl += "&filterscount="+i;
        return filterInventoryUrl;
    }

    private void fillFilterRequestData(String fieldName,String fieldValue,String condition,int filterOperator,int count){
        filterDataMap.put(fieldName+"operator", "and");
        filterDataMap.put("filtervalue"+count, fieldValue);
        filterDataMap.put("filtercondition"+count, condition);
        filterDataMap.put("filteroperator"+count, filterOperator);
        filterDataMap.put("filterdatafield"+count, fieldName);
    }


    @Override
    public void processServiceResponse(JSONObject response) {

    }

    @Override
    public void setCallName(String call) {

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
