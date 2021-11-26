package com.sskarga.listviewbaseadapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sskarga.listviewbaseadapter.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //create adapter object
        ItemBaseAdapter adapter = new ItemBaseAdapter(generateItemsList(), new ItemAdapterListenerImp());

        binding.listView.setAdapter(adapter);
    }

    private ArrayList<ItemCount> generateItemsList() {

        ArrayList<ItemCount> list = new ArrayList<>();

        for (int i = 0; i <= 21; i++) {
            list.add(new ItemCount("Title " + i));
        }

        return list;
    }
}