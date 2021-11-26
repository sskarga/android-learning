package com.sskarga.multiplicationtable.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.sskarga.multiplicationtable.BaseFragment;
import com.sskarga.multiplicationtable.R;
import com.sskarga.multiplicationtable.databinding.FragmentAboutBinding;
import com.sskarga.multiplicationtable.interfaces.HasCustomTitle;


public class AboutFragment extends BaseFragment implements HasCustomTitle {

    FragmentAboutBinding binding;


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(getLayoutInflater(), container, false);
        binding.btnOk.setOnClickListener(view -> getNavigator().goBack());
        return binding.getRoot();
    }

    @Override
    public int getTitleRes() {
        return R.string.titleAbout;
    }
}