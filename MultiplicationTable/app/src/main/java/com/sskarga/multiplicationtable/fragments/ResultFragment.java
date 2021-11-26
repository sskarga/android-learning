package com.sskarga.multiplicationtable.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.sskarga.multiplicationtable.BaseFragment;
import com.sskarga.multiplicationtable.R;
import com.sskarga.multiplicationtable.databinding.FragmentResultBinding;
import com.sskarga.multiplicationtable.dto.OptionResult;
import com.sskarga.multiplicationtable.interfaces.HasCustomTitle;


public class ResultFragment extends BaseFragment implements HasCustomTitle {

    private static final String ARG_OPTIONS = "RESULT_OPTIONS";

    FragmentResultBinding binding;

    private Integer countQuestion = 0;
    private Integer countRightAns = 0;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(OptionResult options) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_OPTIONS, options);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getArguments() != null) && getArguments().containsKey(ARG_OPTIONS)) {
            OptionResult optionResult = getArguments().getParcelable(ARG_OPTIONS);
            countQuestion = optionResult.getCountQuestion();
            countRightAns = optionResult.getCountRightAns();
        } else {
            throw new IllegalArgumentException("Can't launch ResultFragment without options");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultBinding.inflate(getLayoutInflater(), container, false);
        binding.btnToMain.setOnClickListener(view -> getNavigator().goToMenu());
        binding.textViewCountAns.setText(
                String.format(getString(R.string.resultReportCountAns), countRightAns, countQuestion)
        );
        binding.ratingBar.setRating((int) (0.33 * countRightAns) );
        return binding.getRoot();
    }

    @Override
    public int getTitleRes() {
        return R.string.titleResult;
    }
}