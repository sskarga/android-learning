package com.sskarga.shoptoolsexample;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private TextView textViewInfo;
    private ImageView imageViewTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Hide action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        textViewTitle = findViewById(R.id.textViewDetailsTitle);
        textViewInfo = findViewById(R.id.textViewDetailsInfo);
        imageViewTool = findViewById(R.id.imageViewDetailsTools);

        RechargeableTools rtool;
        Intent intent = getIntent();

        if (intent != null) {
            rtool = (RechargeableTools) intent.getExtras().getSerializable(RechargeableTools.class.getSimpleName());
            textViewTitle.setText(rtool.getTitle());
            textViewInfo.setText(rtool.getInfo());
            imageViewTool.setImageResource(rtool.getImageResId());
        } else {
            Intent backIntent = new Intent(this, RechargeableTools.class);
            startActivity(backIntent);
        }
    }
}