package com.sskarga.cafeorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CreateOrderActivity extends AppCompatActivity {

    private TextView textTitleOrder;
    private TextView textTextAddition;
    private RadioButton radioButtonTea;
    private RadioButton radioButtonCoffee;
    private CheckBox checkBoxMilk;
    private CheckBox checkBoxSugar;
    private CheckBox checkBoxLemon;
    private Spinner spinnerTea;
    private Spinner spinnerCoffee;

    private String login = "";
    private String password = "";
    private String drink = "";
    private StringBuilder builderAdditions;
    private String typeDrink = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        textTitleOrder = findViewById(R.id.title_order);
        textTextAddition = findViewById(R.id.text_addition);
        radioButtonTea = findViewById(R.id.radio_gp_drink_tea);
        radioButtonCoffee = findViewById(R.id.radio_gp_drink_coffe);
        checkBoxMilk = findViewById(R.id.checkboxMilk);
        checkBoxSugar = findViewById(R.id.checkboxSugar);
        checkBoxLemon = findViewById(R.id.checkboxLemon);
        spinnerTea = findViewById(R.id.spinner_option_of_tea);
        spinnerCoffee = findViewById(R.id.spinner_option_of_coffee);

        Intent intent = getIntent();
        if (intent != null) {
            login = intent.hasExtra("login") ? intent.getStringExtra("login") : getString(R.string.default_login);
            password = intent.hasExtra("password") ? intent.getStringExtra("password") : getString(R.string.default_password);
        }

        textTitleOrder.setText(String.format(getString(R.string.title_order), login));

        this.changedDrink();

        builderAdditions = new StringBuilder();
    }

    private void changedDrink() {
        if (radioButtonTea.isChecked()) {
            drink = radioButtonTea.getText().toString();
            textTextAddition.setText(String.format(getString(R.string.text_addition), drink));
            spinnerTea.setVisibility(View.VISIBLE);
            spinnerCoffee.setVisibility(View.INVISIBLE);
            checkBoxLemon.setEnabled(true);
        }

        if (radioButtonCoffee.isChecked()) {
            drink = radioButtonCoffee.getText().toString();
            textTextAddition.setText(String.format(getString(R.string.text_addition), drink));
            spinnerTea.setVisibility(View.INVISIBLE);
            spinnerCoffee.setVisibility(View.VISIBLE);
            checkBoxLemon.setChecked(false);
            checkBoxLemon.setEnabled(false);
        }
    }

    public void onClick_btnSendOrder(View view) {
        builderAdditions.setLength(0);

        if (checkBoxMilk.isChecked()) {
            builderAdditions.append(getString(R.string.checkbox_milk) + " \n");
        }

        if (checkBoxSugar.isChecked()) {
            builderAdditions.append(getString(R.string.checkbox_sugar) + " \n");
        }

        if (checkBoxLemon.isChecked()) {
            builderAdditions.append(getString(R.string.checkbox_lemon) + " \n");
        }

        if (spinnerTea.getVisibility() == View.VISIBLE) {
            typeDrink = spinnerTea.getSelectedItem().toString();
        }

        if (spinnerCoffee.getVisibility() == View.VISIBLE) {
            typeDrink = spinnerCoffee.getSelectedItem().toString();
        }

        String order = String.format(getString(R.string.order), login, password, drink, typeDrink);
        String fullOrder;

        if (builderAdditions.length() > 0) {
            fullOrder = order + getString(R.string.additions) + builderAdditions.toString();
        } else {
            fullOrder = order;
        }

        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("order", fullOrder);
        startActivity(intent);
    }

    public void onclick_ChagedDrink(View view) {
        this.changedDrink();
    }
}
