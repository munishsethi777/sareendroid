package in.satya.sareenproperties;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class InventoryList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton filterBtn = (ImageButton)findViewById(R.id.inventoryFilterButton);
        filterBtn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                openFilter();       //put your intent code here
            }
        });
    }

    private void openFilter(){
        Intent intent = new Intent(getApplicationContext(), InventoryFilterActivity.class);
        startActivity(intent);
    }
}
