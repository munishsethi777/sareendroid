package in.satya.sareenproperties;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import in.satya.sareenproperties.Enums.FacingType;
import in.satya.sareenproperties.Enums.MediumType;
import in.satya.sareenproperties.Enums.PropertyType;
import in.satya.sareenproperties.Enums.PurposeType;
import in.satya.sareenproperties.Enums.YesNO;
import in.satya.sareenproperties.utils.StringConstants;

public class EnquiryFilterActivity extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinner_type;
    private Spinner spinner_facing;
    private Spinner spinner_rental;
    private Spinner spinner_fullfill;
    private Spinner spinner_purpose;
    private EditText editText_amount_from;
    private EditText editText_amount_to;
    private Map<String,Object> filterDataMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        filterDataMap = new HashMap<String,Object>();
        editText_amount_from = (EditText) findViewById(R.id.amountFrom);
        editText_amount_to = (EditText) findViewById(R.id.amountTo);
        buildSpinner();
    }

    private void buildSpinner(){
        spinner_purpose = (Spinner) findViewById(R.id.purpose);
        spinner_type = (Spinner) findViewById(R.id.propertytype);
        spinner_facing = (Spinner)findViewById(R.id.facing);
        spinner_rental = (Spinner)findViewById(R.id.isRental);
        spinner_fullfill = (Spinner)findViewById(R.id.isFulfilled);
        spinner_purpose.setAdapter(
                new ArrayAdapter<PurposeType>(this,
                        R.layout.spinner_row,
                        PurposeType.values()));

        spinner_type.setAdapter(
                new ArrayAdapter<PropertyType>(this,
                        R.layout.spinner_row,
                        PropertyType.values()));

        spinner_facing.setAdapter(
                new ArrayAdapter<FacingType>(this,
                        R.layout.spinner_row,
                        FacingType.values()));

        spinner_rental.setAdapter(
                new ArrayAdapter<YesNO>(this,
                        R.layout.spinner_row,
                        YesNO.values()));

        spinner_fullfill.setAdapter(
                new ArrayAdapter<YesNO>(this,
                        R.layout.spinner_row,
                        YesNO.values()));
    }

    private void submitFilter(){
        String filterInventoryUrl = getFilterRequestUrl();
        Intent listIntent = new Intent(this,EnquiryList.class);
        listIntent.putExtra("filterData",filterInventoryUrl);
        startActivity(listIntent);
    }

    private String getFilterRequestUrl(){
        String type = PropertyType.getNameByValue(spinner_type.getSelectedItem().toString());
        String purpose = PurposeType.getNameByValue(spinner_purpose.getSelectedItem().toString());
        String facing = FacingType.getNameByValue(spinner_facing.getSelectedItem().toString());
        String amountFrom = editText_amount_from.getText().toString();
        String amountTo = editText_amount_to.getText().toString();
        String isRental = YesNO.getNameByValue(spinner_rental.getSelectedItem().toString());
        String isFullfill = YesNO.getNameByValue(spinner_fullfill.getSelectedItem().toString());
        int i = 0;
        String condition = "CONTAINS";
        int filterOperator = 0;
        if(!type.isEmpty()) {
            fillFilterRequestData("propertytype",type,condition,filterOperator,i++);
        }
        if(!purpose.isEmpty()) {
            fillFilterRequestData("purpose",purpose,condition,filterOperator,i++);
        }

        if(!facing.isEmpty()) {
            fillFilterRequestData("facing",facing,condition,filterOperator,i++);
        }

        if(!isRental.isEmpty()) {
            fillFilterRequestData("isrental",isRental,condition,filterOperator,i++);
        }

        if(!isFullfill.isEmpty()) {
            fillFilterRequestData("isfullfilled",isFullfill,condition,filterOperator,i++);
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
        String filterEnquiryUrl = StringConstants.GET_ENQUIRIES;
        Iterator<String> itr = filterDataMap.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = filterDataMap.get(key);
            filterEnquiryUrl += "&"+key+"="+value;
        }
        filterEnquiryUrl += "&filterscount="+i;
        return filterEnquiryUrl;
    }

    private void fillFilterRequestData(String fieldName,String fieldValue,String condition,int filterOperator,int count){
        filterDataMap.put(fieldName+"operator", "and");
        filterDataMap.put("filtervalue"+count, fieldValue);
        filterDataMap.put("filtercondition"+count, condition);
        filterDataMap.put("filteroperator"+count, filterOperator);
        filterDataMap.put("filterdatafield"+count, fieldName);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.button_apply_filter){
            submitFilter();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
