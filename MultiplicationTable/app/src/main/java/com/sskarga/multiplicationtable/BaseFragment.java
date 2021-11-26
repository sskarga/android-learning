package com.sskarga.multiplicationtable;

import androidx.fragment.app.Fragment;

import com.sskarga.multiplicationtable.interfaces.Navigator;

public class BaseFragment extends Fragment {

    public Navigator getNavigator() {
        return (Navigator) getActivity();
    }
}
