package com.sskarga.shoptoolsexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RechargeableToolsActivity extends AppCompatActivity {

    private ListView listViewRecharTools;
    private ArrayList<RechargeableTools> rechargeableToolsArrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechargeable_tools);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        listViewRecharTools = findViewById(R.id.listViewRecharTools);

        rechargeableToolsArrays = new ArrayList<RechargeableTools>() {{
            add(new RechargeableTools(
                    getString(R.string.rechar_tool_bolgarki_title),
                    getString(R.string.rechar_tool_bolgarki_info),
                    R.drawable.bolg
            ));
            add(new RechargeableTools(
                    getString(R.string.rechar_tool_diskovye_pily_title),
                    getString(R.string.rechar_tool_diskovye_pily_info),
                    R.drawable.cirk
            ));
            add(new RechargeableTools(
                    getString(R.string.rechar_tool_multituly_title),
                    getString(R.string.rechar_tool_multituly_info),
                    R.drawable.gayk
            ));
        }};

        ArrayAdapter<RechargeableTools> adapter = new ArrayAdapter<>(
                getApplicationContext(),
                android.R.layout.simple_list_item_1,
                rechargeableToolsArrays);

        listViewRecharTools.setAdapter(adapter);

        listViewRecharTools.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
            intent.putExtra(
                    RechargeableTools.class.getSimpleName(),
                    rechargeableToolsArrays.get(i));
            startActivity(intent);
        });
    }
}