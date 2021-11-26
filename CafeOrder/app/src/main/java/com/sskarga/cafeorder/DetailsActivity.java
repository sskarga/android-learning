package com.sskarga.cafeorder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private TextView textViewOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textViewOrder = findViewById(R.id.textViewOrder);
        Intent intent = getIntent();
        textViewOrder.setText(intent.getStringExtra("order"));
    }
}