package com.sskarga.cafeorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editLogin;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
    }

    public void onClick_btnGoToOrder(View view) {
        String login = editLogin.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (login.isEmpty()) {
            Toast.makeText(this, R.string.err_empty_name, Toast.LENGTH_LONG).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, R.string.err_empty_password, Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(this, CreateOrderActivity.class);
        intent.putExtra("login", login);
        intent.putExtra("password", password);
        startActivity(intent);
    }
}