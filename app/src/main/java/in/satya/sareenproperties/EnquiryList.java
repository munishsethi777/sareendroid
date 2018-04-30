package in.satya.sareenproperties;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import in.satya.sareenproperties.R;
import in.satya.sareenproperties.services.Interface.IServiceHandler;
import in.satya.sareenproperties.services.ServiceHandler;
import in.satya.sareenproperties.utils.StringConstants;

public class EnquiryList extends AppCompatActivity implements IServiceHandler {
    private TextView textView_property_detail;
    private TextView textView_address;
    private TextView textView_contact_detail;
    private LinearLayout layout_enquiry_list;
    private ServiceHandler mAuthTask;
    private String getEnquiryUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        getEnquiryUrl = intent.getStringExtra("filterData");
        if(getEnquiryUrl == null){
            getEnquiryUrl = StringConstants.GET_ENQUIRIES;
        }
        executeGetEnquiryListCal();
    }

    private void executeGetEnquiryListCal(){
        layout_enquiry_list = (LinearLayout)findViewById(R.id.layout_enquiry_list) ;
        layout_enquiry_list.removeAllViews();
        mAuthTask = new ServiceHandler(getEnquiryUrl,this,this);
        mAuthTask.execute();
    }

    @Override
    public void processServiceResponse(JSONObject response) {
        mAuthTask = null;
        String message = null;
        try{
            populateEnquiryList(response);
        }catch (Exception e){
            message = "Error :- " + e.getMessage();
        }
        if(message != null && !message.equals("")){
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    private void populateEnquiryList(JSONObject response)throws Exception {
        JSONArray inventories = response.getJSONArray("Rows");
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < inventories.length(); i++) {
            JSONObject inventoryJson = inventories.getJSONObject(i);
            final int seq = inventoryJson.getInt("seq");
            String propertyType = inventoryJson.getString("propertytype");
            String area = inventoryJson.getString("propertyarea");
            String unit = inventoryJson.getString("propertyunit");
            String address1 = inventoryJson.getString("address");
            String contactPerson = inventoryJson.getString("contactperson");
            String contactMobile = inventoryJson.getString("contactmobile");
            LinearLayout fragmentLayout= (LinearLayout) inflater.inflate(R.layout.enquriy_list_fragment, null, false);

            ImageButton imageButton = fragmentLayout.findViewById(R.id.imageview_show_detail);
            TextView textView_property_type = fragmentLayout.findViewById(R.id.textview_property_type);
            textView_property_type.setText(propertyType + " - " + area + " " + unit);
            TextView textView_address = fragmentLayout.findViewById(R.id.textview_address);
            textView_address.setText(address1);
            TextView textView_contact_detail = fragmentLayout.findViewById(R.id.textview_contact_detail);
            textView_contact_detail.setText(contactPerson + "-" + contactMobile);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDetail(seq);
                }
            });
            layout_enquiry_list.addView(fragmentLayout);
        }
        getEnquiryUrl = StringConstants.GET_ENQUIRIES;
    }

    private void showDetail(int seq){
        Intent detailIntent = new Intent(this,EnquiryDetails.class);
        detailIntent.putExtra(StringConstants.SEQ,seq);
        startActivity(detailIntent);
    }

    @Override
    public void setCallName(String call) {}
}
