package com.sskarga.shoptoolsexample;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }

        listViewTools = findViewById(R.id.listViewTools);
        listViewTools.setOnItemClickListener((adapterView, view, position, l) -> {
            switch(position) {
                case 0:
                    Intent intent = new Intent(getApplicationContext(), RechargeableToolsActivity.class);
                    startActivity(intent);
                    break;
            }
        });
    }
}