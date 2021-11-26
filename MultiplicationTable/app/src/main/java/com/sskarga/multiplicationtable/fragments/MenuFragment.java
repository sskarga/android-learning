package com.sskarga.multiplicationtable.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.sskarga.multiplicationtable.BaseFragment;
import com.sskarga.multiplicationtable.databinding.FragmentMenuBinding;


public class MenuFragment extends BaseFragment {

    private FragmentMenuBinding binding;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(getLayoutInflater(),container, false);
        binding.btnStart.setOnClickListener(view -> getNavigator().showTestScreen());
        binding.btnAbout.setOnClickListener(view -> getNavigator().showAboutScreen());
        binding.btnExit.setOnClickListener(view -> getNavigator().close());
        return binding.getRoot();
    }
}